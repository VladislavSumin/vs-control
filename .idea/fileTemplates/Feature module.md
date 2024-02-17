TODO написать ридми.
${DIR_PATH}
${FILE_NAME}
${NAME}

#set( $CamelCaseName = "" )#set( $part = "" )#foreach($part in $NAME.split("-"))#set( $CamelCaseName = "${CamelCaseName}$part.substring(0,1).toUpperCase()$part.substring(1).toLowerCase()" )#end class $CamelCaseName { }


#set( $CamelCaseName = "" )
#set( $part = "" )
#foreach($part in $NAME.split("-"))
  #set( $CamelCaseName = "${CamelCaseName}#if($CamelCaseName.length() == 0)$part.substring(0,1).toLowerCase()#else$part.substring(0,1).toUpperCase()#end$part.substring(1).toLowerCase()" )
#end

$CamelCaseName