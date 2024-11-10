package ru.vs.core.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.AbstractFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
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
fun <T, K, R> Flow<Iterable<T>>.cachingStateProcessing(
    keySelector: (T) -> K,
    block: suspend FlowCollector<K>.(StateFlow<T>) -> Unit
): Flow<List<R>> = FlowCachingStateProcessing(this, keySelector, block)

@OptIn(ExperimentalCoroutinesApi::class)
private class FlowCachingStateProcessing<T, K, R>(
    private val upstream: Flow<Iterable<T>>,
    private val keySelector: (T) -> K,
    private val block: suspend FlowCollector<K>.(StateFlow<T>) -> Unit
) : AbstractFlow<List<R>>() {
    override suspend fun collectSafely(collector: FlowCollector<List<R>>) = coroutineScope {
        // Создаем отдельный coroutine scope на котором будет запускать задачки для выполнения block параметра.
        // Scope нужен именно этот, иначе, если мы используем scope из transformLatest, то job которая должна переживать
        // обновление состояния при совпадении ключей будет отменяться вместе с transformLatest. Поэтому тут мы должны
        // явно управлять отменой дочерних job.
        val scope: CoroutineScope = this

        // К этому состоянию безопасно обращаться из transformLatest, так как данный блок гарантирует отмену предыдущей
        // обработки перед началом следующей
        var oldState: State<K, T>
        var newState = State<K, T>()

        upstream.transformLatest { newItems ->
            oldState = newState
            newState = State()

            newItems.map { item ->
                val key = keySelector(item)
                val cachedItem: CacheData<T> = oldState.cache.remove(key) ?: let {
                    val state = MutableStateFlow(item)
                    val flow = flow { block(state) }
                    val job = scope.launch {
                        coroutineScope {
                            // TODO вот это написать надо.
                            flow.shareIn(this, SharingStarted.Eagerly, replay = 1)
                        }
                    }
                    CacheData(state, job)
                }
                newState.cache[key] = cachedItem
            }

            oldState.cache.forEach { (_, v) ->
                // TODO cancel removes keys
            }


            emit(TODO())
        }
            .collect(collector)
    }
}

private class State<K, T>(
    val cache: MutableMap<K, CacheData<T>> = mutableMapOf()
)

private class CacheData<T>(
    val flow: MutableStateFlow<T>,
    val job: Job
)