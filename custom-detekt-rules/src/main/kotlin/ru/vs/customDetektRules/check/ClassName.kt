package ru.vs.customDetektRules.check

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Rule
import org.jetbrains.kotlin.psi.KtClass

/**
 * Проверяет имя класса на соответствие [regex].
 *
 * @param regex регулярное выражение для проверки [KtClass.getName].
 * @param message сообщение, которое будет выведено при несоответствии имени.
 */
context(Rule)
fun KtClass.checkName(
    regex: Regex,
    message: () -> String,
) {
    val name = name
    if (name == null || !regex.matches(name)) {
        report(
            CodeSmell(
                issue = issue,
                entity = Entity.from(nameIdentifier!!, 0),
                message = message(),
            ),
        )
    }
}
