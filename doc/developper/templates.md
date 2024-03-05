# Шаблоны

TODO()

## Скрипты для использования в новых шаблонах.

### Dash-case to PascalCase.

```
#set( $PascalCaseName = "" )
#set( $part = "" )
#foreach($part in $NAME.split("-"))
  #set( $PascalCaseName = "${PascalCaseName}$part.substring(0,1).toUpperCase()$part.substring(1).toLowerCase()" )
#end
```

В одну строку для путей файлов:

```
#set( $PascalCaseName = "" )#set( $part = "" )#foreach($part in $NAME.split("-"))#set( $PascalCaseName = "${PascalCaseName}$part.substring(0,1).toUpperCase()$part.substring(1).toLowerCase()" )#end
```

### Dash-case to camelCase.

```
#set( $CamelCaseName = "" )
#set( $part = "" )
#foreach($part in $NAME.split("-"))
  #set( $CamelCaseName = "${CamelCaseName}#if($CamelCaseName.length() == 0)$part.substring(0,1).toLowerCase()#else$part.substring(0,1).toUpperCase()#end$part.substring(1).toLowerCase()" )
#end
```

В одну строку для путей файлов:

```
#set( $CamelCaseName = "" )#set( $part = "" )#foreach($part in $NAME.split("-"))#set( $CamelCaseName = "${CamelCaseName}#if($CamelCaseName.length() == 0)$part.substring(0,1).toLowerCase()#else$part.substring(0,1).toUpperCase()#end$part.substring(1).toLowerCase()" )#end
```

### To lower case
```
#set($name = $NAME.substring(0,1).toLowerCase() + $NAME.substring(1))
```

### Убираем кавычки

```
#set ( $NAME = $NAME.replaceAll("`","") )
```

## Полезные ссылки

* [Документация по шаблонам в IDEA](https://www.jetbrains.com/help/idea/using-file-and-code-templates.html)
* [Документация по языку шаблонов Apache Velocity Project](https://velocity.apache.org/)