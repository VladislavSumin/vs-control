package ru.vs.customDetektRules.rules

import io.github.detekt.psi.absolutePath
import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.KtPackageDirective
import kotlin.io.path.name

class ModuleRootPackage(config: Config = Config.empty) : Rule(config) {
    override val issue = Issue(
        id = "moduleRootPackage",
        description = "Проверка пакета модуля",
        severity = Severity.CodeSmell,
        debt = Debt.FIVE_MINS,
    )

    @Suppress("MagicNumber")
    override fun visitPackageDirective(directive: KtPackageDirective) {
        super.visitPackageDirective(directive)

        val filePackage = directive.fqName
        if (filePackage.isRoot) return

        val path = directive.containingKtFile.absolutePath()

        // 4 это сам файл + kotlin + commonMain + src
        val modulePath = path.subpath(0, path.count() - filePackage.pathSegments().size - 4)
        val featurePath = modulePath.parent

        // TODO пока проверяем пакет только для feature модулей
        if (featurePath.parent.name != "feature") return

        val moduleType = ModuleType.fromModuleName(modulePath.name)
        val featurePackage = featurePath.name.dashToLowerCamelCase()

        val expectedPackage = rootPackage
            .child(Name.identifier(featurePackage))
            .let {
                when (moduleType) {
                    ModuleType.Client -> it.child(Name.identifier("client"))
                    ModuleType.Server -> it.child(Name.identifier("server"))
                    ModuleType.Shared -> it
                }
            }
        if (!filePackage.startsWith(expectedPackage)) {
            report(
                CodeSmell(
                    issue = issue,
                    entity = Entity.from(directive, 0),
                    message = "Пакет должен начинаться с ${expectedPackage.asString()}",
                ),
            )
        }
    }

    private enum class ModuleType {
        Client,
        Server,
        Shared,
        ;

        companion object {
            fun fromModuleName(name: String): ModuleType {
                val type = name.split("-", limit = 2).first()
                return when (type) {
                    "client" -> Client
                    "server" -> Server
                    "shared" -> Shared
                    else -> error("Unknown module type $type")
                }
            }
        }
    }

    companion object {
        private val rootPackage = FqName("ru.vs.control.feature")
    }
}

private val dashRegex = "-[a-zA-Z]".toRegex()
fun String.dashToLowerCamelCase(): String {
    return dashRegex.replace(this) {
        it.value.replace("-", "").uppercase()
    }
}
