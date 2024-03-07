package ru.vs.customDetektRules.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.rules.isInternal
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.getSuperNames

class ScreenFactory(config: Config = Config.empty) : Rule(config) {
    override val issue = Issue(
        id = "screenFactory",
        description = "Проверки для наследников ScreenFactory",
        severity = Severity.CodeSmell,
        debt = Debt.FIVE_MINS,
    )

    override fun visitClass(klass: KtClass) {
        if (SCREEN_FACTORY_NAME !in klass.getSuperNames()) return

        if (!SCREEN_FACTORY_IMPL_NAME_REGEX.matches(klass.name!!)) {
            report(
                CodeSmell(
                    issue = issue,
                    entity = Entity.from(klass.nameIdentifier!!, 0),
                    message = "Наследники ScreenFactory должны иметь постфикс ScreenFactory",
                ),
            )
        }

        val packageMatches = PACKAGE_REGEX.matchEntire(klass.fqName!!.parent().asString())
        if (packageMatches == null || "${packageMatches.groups[1]?.value}Factory" != klass.name!!.decapitalize()) {
            report(
                CodeSmell(
                    issue = issue,
                    entity = Entity.from(klass, 0),
                    message = "${klass.name!!} должен лежать в пакете " +
                        "'ru.vs.control.feature.<featureName>.ui.screen.${klass.name!!.decapitalize()}",
                ),
            )
        }
        if (!klass.isInternal()) {
            report(
                CodeSmell(
                    issue = issue,
                    entity = Entity.from(klass, 0),
                    message = "${klass.name} должна быть internal",
                ),
            )
        }
    }

    companion object {
        private const val SCREEN_FACTORY_NAME = "ScreenFactory"
        private val SCREEN_FACTORY_IMPL_NAME_REGEX = ".*ScreenFactory".toRegex()
        private val PACKAGE_REGEX = "ru\\.vs\\.control\\.feature\\..*\\.ui\\.screen\\.(.*)".toRegex()
    }
}
