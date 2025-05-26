# 🖥️ Tienda de Ordenadores - Proyecto de Programación

**Autor:** Álvaro Jiménez Muñoz  
**Estudiante de:** 1º DAM  
**Proyecto de programación: Tienda de ordenadores**  

---

## 📽️ Ejemplo de ejecución

[![Ver video de ejemplo](https://img.youtube.com/vi/0hU-WBWde8E/0.jpg)](https://youtu.be/0hU-WBWde8E)

---

## 📋 Descripción general

Este proyecto consiste en el desarrollo de una **tienda online de ordenadores** completamente funcional, tanto a nivel de gestión interna (administradores) como de experiencia de usuario (clientes).  
El sistema está implementado en **Java** y utiliza una base de datos **SQL Server** gestionada mediante **Docker** para facilitar la portabilidad y despliegue.

El código y la base de datos están **altamente comentados** y diseñados para ser robustos, coherentes y fácilmente ampliables.

---

## 🏗️ Estructura y funcionalidades principales

### Usuarios

- **Registro y autenticación:**  
  Los usuarios pueden registrarse proporcionando correo, contraseña, nombre, apellidos, DNI y fecha de nacimiento.  
  Los administradores ya existen de antemano y no pueden ser registrados desde la aplicación.

- **Roles diferenciados:**  
  - **Usuarios normales:** pueden comprar productos, gestionar su carrito y consultar compras anteriores.
  - **Administradores:** pueden realizar todas las operaciones CRUD sobre los componentes, ordenadores, usuarios y carritos.

### Componentes y productos

- **Componentes gestionados:**  
  - Chasis
  - Placa base
  - CPU
  - GPU
  - Refrigeración (aire/líquida para CPU y GPU)
  - Almacenamiento (principal/secundario)
  - RAM
  - Fuente de alimentación
  - Ordenadores preemontados (PC/Oficina, Workstation, Gaming, Servidor, Embebido, Científico)
  - Montadores y servicios de testeo

- **Atributos y relaciones:**  
  Cada componente tiene atributos específicos y está vinculado a un fabricante.  
  El stock de cada modelo se gestiona y restringe automáticamente.

### Gestión de la tienda

- **Menús claros y legibles:**  
  Navegación sencilla tanto para usuarios como para administradores, con menús bien estructurados y mensajes informativos.

- **Restricciones y validaciones:**  
  - No se pueden crear ordenadores incompatibles (por ejemplo, con fuentes insuficientes o piezas incompatibles).
  - El stock se controla en todo momento y no se permite comprar más unidades de las disponibles.
  - El sistema permite reintentar operaciones en caso de error.

- **Carrito de la compra:**  
  - Un usuario solo puede tener un carrito en estado "compraNoRealizada" a la vez.
  - El stock solo se descuenta al confirmar la compra.
  - El carrito almacena tanto componentes sueltos como ordenadores completos.
  - Si se elimina un carrito, sus contenidos se eliminan automáticamente (gracias a restricciones y triggers en la BBDD).
  - Las compras quedan registradas y pueden be ser consultadas posteriormente.

---

## 🗄️ Base de datos SQL Server

- **Script SQL extenso y comentado** (más de 1000 líneas).
- **Restricciones y triggers** para asegurar la integridad:
  - Eliminación en cascada de relaciones (por ejemplo, borrar un carrito elimina sus contenidos).
  - Cálculo automático del precio total de los ordenadores y carritos.
  - Control de stock y coherencia de datos.
- **Inserciones iniciales realistas**:  
  El script incluye una gran cantidad de datos iniciales, asegurando compatibilidad y realismo en los productos y relaciones.
- **Consultas y operaciones eficientes**:  
  El diseño permite consultar compras, montajes y mantenimientos realizados, con registro de fechas y detalles.

---

## 🛠️ Estructura del código Java

- **Organización por paquetes:**  
  Cada tabla de la base de datos tiene su clase y su servicio correspondiente en Java.
- **CRUD completo para cada entidad:**  
  Métodos para crear, leer, actualizar y borrar componentes, ordenadores, usuarios, carritos, etc.
- **Gestión de compatibilidad y stock:**  
  El sistema valida la compatibilidad de piezas y el stock antes de permitir operaciones.
- **Menús y navegación:**  
  Menús claros, robustos y adaptados al rol del usuario.
- **Código comentado y mantenible:**  
  Todo el código está documentado para facilitar su comprensión y mantenimiento.

---

## 🚀 Instrucciones de instalación y ejecución

### 1. **Requisitos previos**

- [Java JDK](https://adoptium.net/) instalado (versión 17 o superior recomendada)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado y funcionando
- (Opcional) [SQL Server Management Studio (SSMS)](https://aka.ms/ssms) para explorar la base de datos visualmente

---

### 2. **Clonar el repositorio**

```bash
git clone <URL_DEL_REPOSITORIO>
```

---

### 3. **Desplegar la base de datos con Docker**

Ejecuta este comando en la consola (como administrador), cambiando `<ruta_absoluta_de_la_carpeta_sql_server_data>` por la ruta absoluta de la carpeta `sql_server_data` que viene en el proyecto descargado.  
Por ejemplo: `D:\ProyectoProgramación\sql_server_data`

```bash
docker run --e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=12@9629ab8e" --user root -p 1433:1433 --name sql1 \
-v <ruta_absoluta_de_la_carpeta_sql_server_data>:/var/opt/mssql/data -d mcr.microsoft.com/mssql/server:2022-latest
```

- Puedes cambiar la contraseña (`MSSQL_SA_PASSWORD`) por otra válida según los requisitos de Microsoft (mínimo 8 caracteres, mayúsculas, minúsculas, números y un caracter especial).
- Esto creará y levantará el contenedor de SQL Server automáticamente.
- Si no se inicia automáticamente, puedes abrir Docker Desktop y arrancarlo manualmente.

---

### 4. **Cargar el script de la base de datos**

- Abre SQL Server Management Studio (como administrador).
- Conéctate usando:
  - **Server name:** la IP de tu equipo (o `localhost` si es local)
  - **Authentication:** selecciona "Windows Authentication" o "SQL Server Authentication"
    - **Login:** `sa`
    - **Password:** contraseña establecida en el comando anterior (por defecto `12@9629ab8e`)
- Ejecuta el script SQL incluido en el repositorio (`script.sql`) para crear y poblar la base de datos.

---

### 5. **Ejecutar el programa Java**

- Abre el proyecto en tu IDE favorito (por ejemplo, Visual Studio Code o IntelliJ).
- Compila y ejecuta la clase principal (`App.java`).
- El programa se conectará automáticamente a la base de datos si el contenedor está en marcha.

---

### 🌟 Consejos y personalización

- **Ruta de datos:**  
  Asegúrate de que la ruta que pones en el comando Docker corresponda a la carpeta `sql_server_data` del proyecto descargado.
- **Contraseña personalizada:**  
  Puedes cambiar la contraseña del usuario `sa` en el comando Docker, pero recuerda usarla la misma al conectarte desde Java o SSMS.
- Conexión desde SSMS:  
  Para conectarte al contenedor desde SQL Server Management Studio, usa la IP de tu equipo o `localhost`, selecciona "SQL Server Authentication", login `sa` y la contraseña que hayas puesto.

---

### 📝 Notas y recomendaciones

- Todo el código y SQL están comentados para facilitar el aprendizaje y la revisión.
- El sistema está pensado para ser robusto y realista, con validaciones y restricciones tanto en Java como en SQL.
- Puedes modificar los datos iniciales o ampliar el sistema fácilmente gracias a su estructura modular.
- Si tienes dudas o encuentras algún problema, revisa los comentarios en el código o contáctame.

---

### ℹ️ Información adicional

- **Gestión de stock y compatibilidad:**  
  El sistema no permite comprar más unidades de las disponibles ni crear ordenadores incompatibles.
- **Carritos y compras:**  
  Un usuario puede consultar todas sus compras anteriores y solo puede tener un carrito activo a la vez.
- **Administradores:**  
  Los administradores pueden gestionar todos los datos de la tienda, pero no pueden ser registrados desde la aplicación.
- **Triggers y procedimientos almacenados:**  
  El SQL incluye triggers para mantener la integridad y procedimientos para el cálculo automático de precios.

---

### 🎯 Resumen

Este proyecto es una tienda de ordenadores completa, con gestión avanzada tanto a nivel de base de datos como lógica de aplicación.  
Incluye desde un ejercicio de programación integral, abarcando desde la gestión de usuarios y productos hasta la administración avanzada y la integración con Docker y SQL Server.

¡Gracias por tu interés y disfruta explorando el proyecto!