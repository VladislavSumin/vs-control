package ru.vs.core.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.AbstractFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch

/**
 * Кеширующая обработка значений
 *
 * @param keySelector селектор **уникальных** ключей. По этим ключам фреймворк понимает что данные не менялись.
 *
 * @param T тип элементов в родительской коллекции.
 * @param K тип уникального ключа используемого для индексации данных
 * @param R тип элементов в дочерней коллекции
 */
inline fun <T, K, reified R> Flow<Iterable<T>>.cachingStateProcessing(
    noinline keySelector: (T) -> K,
    noinline block: suspend FlowCollector<R>.(StateFlow<T>) -> Unit,
): Flow<List<R>> = FlowCachingStateProcessing(
    upstream = this,
    keySelector = keySelector,
    block = block,
    combiner = { flows -> combine(flows) { it.toList() } },
)

@OptIn(ExperimentalCoroutinesApi::class)
@PublishedApi
internal class FlowCachingStateProcessing<T, K, R>(
    private val upstream: Flow<Iterable<T>>,
    private val keySelector: (T) -> K,
    private val block: suspend FlowCollector<R>.(StateFlow<T>) -> Unit,
    private val combiner: (List<Flow<R>>) -> Flow<List<R>>,
) : AbstractFlow<List<R>>() {
    override suspend fun collectSafely(collector: FlowCollector<List<R>>) = coroutineScope {
        // Создаем отдельный coroutine scope на котором будет запускать задачки для выполнения block параметра.
        // Scope нужен именно этот, иначе, если мы используем scope из transformLatest, то job которая должна переживать
        // обновление состояния при совпадении ключей будет отменяться вместе с transformLatest. Поэтому тут мы должны
        // явно управлять отменой дочерних job.
        val scope: CoroutineScope = this

        // К этому состоянию безопасно обращаться из transformLatest, так как данный блок гарантирует отмену предыдущей
        // обработки перед началом следующей
        var oldState: State<K, R>
        var newState = State<K, R>()

        upstream.transformLatest { newItems ->
            oldState = newState
            newState = State()

            val newWithCache: List<Flow<R>> = newItems.map { item ->
                val key = keySelector(item)
                val cachedItem: CacheData<R> = oldState.cache.remove(key) ?: let {
                    val inputState = MutableStateFlow(item)
                    val outputState: MutableStateFlow<R?> = MutableStateFlow(null)
                    val job = scope.launch {
                        flow { block(inputState) }
                            .collect { outputState.value = it }
                    }
                    CacheData(outputState.mapNotNull { it }, job)
                }
                newState.cache[key] = cachedItem

                cachedItem.out
            }

            oldState.cache.forEach { (_, v) ->
                v.job.cancel()
            }

            combiner(newWithCache).collect(this)
        }
            .collect(collector)
    }
}

private class State<K, R>(
    val cache: MutableMap<K, CacheData<R>> = mutableMapOf(),
)

private class CacheData<R>(
    val out: Flow<R>,
    val job: Job,
)
