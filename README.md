# ProyectoProgramacion

Para poder arrancar el proyecto, lo primero que se debe hacer es hacer un pull o descargar la carpeta del proyecto entera y tenerla en el equipo utilizable y en un lugar localizable,
después, teniendo docker desktop o similar instalado en el equipo, desde la consola de comandos ejecutada como administrador,
introducir el siguiente comando tal y como viene, cambiándo solo un par de cosas:

docker run -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=12062006aB@" --user root -p 1433:1433 --name sql1 --hostname sql1 -v <ubicación carpeta sql_server_data>:/var/opt/mssql/data -d mcr.microsoft.com/mssql/server:2022-latest

Del comando se deberá cambiar <ubicación carpeta sql_server_data> por la ruta absoluta de la carpeta "sql_server_data" que contiene la carpeta del proyecto descargado, ejemplo: ... sql1 -v D:\ProyectoProgramacion\sql_server_data:/var/...
También se puede cambiar la contraseña y poner cualquier otra que sea válida bajo los entándares de Microsoft (+8 carácteres con mayúsculas, minúsculas, números y carácter especial, principalmente)

Una vez ejecutado el comando, si todo ha ido bien se habrá creado el contenedor y asignado a la carpeta que contiene el script.sql ya ejecutado y utilizable desde java

Posterior a esto ya se podría ejecutar el código en Java, también se podría desde SQL Server Management Studio conectarse al contenedor lanzado para ver o modificar la estructura de la bbdd, para hacer la conecxión deberá
proporcionarse la IP del equipo, bajo el campo "Server name", establecer la autentificación como "SQL Server Authentication", poner en el campo login "sa" y en el campo de contraseña la contraseña establecida en el comando anterior

(Este documento todavia no está terminado ni decorado)