## Я без понятия почему, но иногда имя может быть в кавычках если расширение у файла kt.
#set ( $NAME = $NAME.replaceAll("`","") )
##
#set( $CamelCaseName = "" )
#set( $part = "" )
#foreach($part in $NAME.split("-"))
  #set( $CamelCaseName = "${CamelCaseName}#if($CamelCaseName.length() == 0)$part.substring(0,1).toLowerCase()#else$part.substring(0,1).toUpperCase()#end$part.substring(1).toLowerCase()" )
#end
##
#set( $PascalCaseName = "" )
#set( $part = "" )
#foreach($part in $NAME.split("-"))
  #set( $PascalCaseName = "${PascalCaseName}$part.substring(0,1).toUpperCase()$part.substring(1).toLowerCase()" )
#end
package ru.vs.control.feature.${CamelCaseName}

import org.kodein.di.DI
import ru.vs.core.di.Modules

fun Modules.feature$PascalCaseName() = DI.Module("feature-$NAME") {
}