## 1. INTRODUCCIÓN.
## 2. DISEÑANDO LA BASE DE DATOS.
## 2. CONTRUYENDO EL SERVIDOR
## 4. IMPLEMENTANDO LOS ENDPOINTS
## 5. DANDOLE LOGÍCA AL JUEGO.
## 6. PRUEBAS DE CONEXIÓN.
## 7. CONCLUSIÓN.

### 1. INTRODUCCIÓN

En esta memoria de trabajo/explicativa resaltaré los puntos más importantes de la creación de mi aplicación, la cual se basa en un juego de cartas de coches en el que el cliente jugará contra el servidor. Lo primero que hice a la hora de empezar con el proyecto fue comprobar que **Apache Tomcat**, el servidor web con el que voy a trabajar, no diera ningún de problema y por fortuna así fue. Tambien tenía que empezar a decidirme con que base de datos trabajar, habiendo muchas opciones al final me decidí por **MySQL**, que es además lo que se recomendaba para hacer el proyecto. Lo siguiente a pensar fue ¿implemento yo todos los modelo o dejo que Hibernate lo hago por mi?, la primerá opción era Hibernate, además me gustaba la idea de ahorrar tiempo contruyendo clases, pero al conectar la base de datos con este framework encontraba varios errores, investigué un poco pero no me extendí mucho, ya que siempre podía construir las clases a mano y utilizar el driver de MySQL, así que mediante Maven agregué las correspondientes dependencias para poder empezar a trabajar, así que para la creación de la base de datos tuve que utilizar MySQL Workbench.

## 3. CONSTRUYENDO EL SERVIDOR

Al iniciar con el proyecto con Maven tuve que agregar las correspondientes dependecias, y cambiar algunos valores de configuración del archivo web.xml, es algo que hemos visto en clase y no veo necesario resaltar mucho aquí. En un principio no tenía bastante claro por donde empezar, esto de las API era algo nuevo y tenía mis dudas así que el primer día de proyecto lo dediqué a investigar sobre como estaban estructuradas las api, cual era el metodo de trabajo, para que servian exactamente, en esta primera investigación ya me hice un poco a la idea de lo que podía ser el proyecto, ya sabía por donde empezar así que lo primero que hice fue lo más facil y simple que se me ocurrió y se pedía hacer en el trabajo las operaciones CRUD, así no perdía más tiempo mientras pensaba en lo demás, además es algo que llevo haciendo durante todo el curso y se podría decir que lo tenía más que asimilado.   

Con el proyecto creado y configurado empecé a escribir las primeras clases, estas fueron los modelos, los cuales coincidian con las tablas de la base de datos con la que estamos trabajando, ya que de alguna manera nos servirín de paquete para traernos las tablas a nuestro proyecto, estas clases son Carta, Jugador, Partida y Estadistica definidas según las columnas de las tablas, con sus respectivos metodo getters, setters y string, no me llevo mucho tiempo crear estas clases gracias al generador de código de Eclipse eso sí, estas clases han pasado por varias alteraciones y tuve que cambiar algun que otro valor, lo que significaba tambien cambiarlo en la base de datos, ya que tenían que coincidir.

Una vez creadas estas clases tenía que empezar a darles uso, a trabajar con ellas, así que lo siguiente que hice fue implementar la conexión con el driver de MySQL, así que agregue la dependecia al pom.xml y empecé a crear otras nuevas clases las cuales harían las operaciones con la base de datos, esto sería el controlador.  

