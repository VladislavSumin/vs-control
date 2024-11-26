package ru.vs.customDetektRules.rules

import io.github.detekt.psi.absolutePath
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import kotlin.io.path.name
import org.jetbrains.kotlin.psi.KtPackageDirective

// TODO пока не используется, дописать.
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

        // TODO пока проверяем пакет только для feature модулей
        if (modulePath.parent.name != "feature") return

        println("package =$filePackage")
        println("path =$path")
        println("modulePath =$modulePath")
    }
}
