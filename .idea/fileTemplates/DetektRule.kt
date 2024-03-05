#set($name = $NAME.substring(0,1).toLowerCase() + $NAME.substring(1))
package ${PACKAGE_NAME}

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity

class ${NAME}(config: Config = Config.empty) : Rule(config) {
    override val issue = Issue(
        id = "${name}",
        description = TODO(),
        severity = Severity.CodeSmell,
        debt = Debt.FIVE_MINS,
    )
}
