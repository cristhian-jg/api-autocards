## 1. INTRODUCCIÓN.
## 2. CREANDO EL SERVIDOR.
## 3. DISEÑANDO LA BASE DE DATOS.
## 4. IMPLEMENTANDO LOS ENDPOINTS
## 5. DANDOLE LOGÍCA AL JUEGO.
## 6. PRUEBAS DE CONEXIÓN.
## 7. CONCLUSIÓN.

### 1. INTRODUCCIÓN

En esta memoria de trabajo/explicativa resaltaré los puntos más importantes de la creación de mi aplicación, la cual se basa en un juego de cartas de coches en el que el cliente jugará contra el servidor. Lo primero que hice a la hora de empezar con el proyecto fue comprobar que **Apache Tomcat**, el servidor web con el que voy a trabajar, no diera ningún de problema y por fortuna así fue. Tambien tenía que empezar a decidirme con que base de datos trabajar, habiendo muchas opciones al final me decidí por **MySQL**, que es además lo que se recomendaba para hacer el proyecto. Lo siguiente a pensar fue ¿implemento yo todos los modelo o dejo que Hibernate lo hago por mi?, la primerá opción era Hibernate, además me gustaba la idea de ahorrar tiempo contruyendo clases, pero al conectar la base de datos con este framework encontraba varios errores, investigué un poco pero no me extendí mucho, ya que siempre podía construir las clases a mano y utilizar el driver de MySQL, así que mediante Maven agregué las correspondientes dependencias para poder empezar a trabajar. Para la creación de la base de datos tuve que utilizar MySQL Workbench y ya todo configurado empecé con el proyecto. 


