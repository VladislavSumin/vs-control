package ru.vs.customDetektRules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider
import ru.vs.customDetektRules.rules.ModuleRootPackage
import ru.vs.customDetektRules.rules.NavigationRegistrarImpl
import ru.vs.customDetektRules.rules.Screen
import ru.vs.customDetektRules.rules.ScreenComponent
import ru.vs.customDetektRules.rules.ScreenFactory

/**
 * Предоставляет конфигурацию и список правил для detekt
 */
class CustomDetektRuleSetProvider : RuleSetProvider {
    override val ruleSetId: String
        get() = "control"

    override fun instance(config: Config): RuleSet {
        return RuleSet(
            ruleSetId,
            listOf(
                ModuleRootPackage(config),
                NavigationRegistrarImpl(config),
                Screen(config),
                ScreenFactory(config),
                ScreenComponent(config),
            ),
        )
    }
}
