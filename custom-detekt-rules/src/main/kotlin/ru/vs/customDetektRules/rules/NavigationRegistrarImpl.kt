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
import ru.vs.customDetektRules.check.checkName

class NavigationRegistrarImpl(config: Config = Config.empty) : Rule(config) {
    override val issue = Issue(
        id = "navigationRegistrarImpl",
        description = "Проверка что все классы наследующиеся от NavigationRegistrar правильно названы и расположены",
        severity = Severity.CodeSmell,
        debt = Debt.FIVE_MINS,
    )

    override fun visitClass(klass: KtClass) {
        if (NAVIGATION_REGISTRAR_NAME !in klass.getSuperNames()) return
        klass.checkName(NAVIGATION_REGISTRAR_IMPL_NAME_REGEX) {
            "Наследники NavigationRegistrar должны называться NavigationRegistrarImpl"
        }

        if (!PACKAGE_REGEX.matches(klass.fqName!!.parent().asString())) {
            report(
                CodeSmell(
                    issue = issue,
                    entity = Entity.from(klass, 0),
                    message = "NavigationRegistrarImpl должен лежать в пакете " +
                        "'ru.vs.control.feature.<featureName>.ui.screen",
                ),
            )
        }
        if (!klass.isInternal()) {
            report(
                CodeSmell(
                    issue = issue,
                    entity = Entity.from(klass, 0),
                    message = "NavigationRegistrarImpl должен быть internal",
                ),
            )
        }
    }

    companion object {
        private const val NAVIGATION_REGISTRAR_NAME = "NavigationRegistrar"
        private val NAVIGATION_REGISTRAR_IMPL_NAME_REGEX = "NavigationRegistrarImpl".toRegex()
        private val PACKAGE_REGEX = "ru\\.vs\\.control\\.feature\\..*\\.ui\\.screen".toRegex()
    }
}
