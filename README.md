## 1. INTRODUCCIÓN.
## 2. DISEÑANDO LA BASE DE DATOS.
## 2. CONTRUYENDO EL SERVIDOR
## 4. IMPLEMENTANDO LOS ENDPOINTS
## 5. DANDOLE LOGÍCA AL JUEGO.
## 6. PRUEBAS DE CONEXIÓN.
## 7. CONCLUSIÓN.

### 1. INTRODUCCIÓN

En esta memoria de trabajo/explicativa resaltaré los puntos más importantes de la creación de mi aplicación, la cual se basa en un juego de cartas de coches en el que el cliente jugará contra el servidor. Lo primero que hice a la hora de empezar con el proyecto fue comprobar que **Apache Tomcat**, el servidor web con el que voy a trabajar, no diera ningún de problema y por fortuna así fue. Tambien tenía que empezar a decidirme con que base de datos trabajar, habiendo muchas opciones al final me decidí por **MySQL**, que es además lo que se recomendaba para hacer el proyecto. Lo siguiente a pensar fue ¿implemento yo todos los modelo o dejo que Hibernate lo hago por mi?, la primerá opción era Hibernate, además me gustaba la idea de ahorrar tiempo contruyendo clases, pero al conectar la base de datos con este framework encontraba varios errores, investigué un poco pero no me extendí mucho, ya que siempre podía construir las clases a mano y utilizar el driver de MySQL, así que mediante Maven agregué las correspondientes dependencias para poder empezar a trabajar, así que para la creación de la base de datos tuve que utilizar MySQL Workbench.

### 2. DISEÑANDO LA BASE DE DATOS 

### 3. CONSTRUYENDO EL SERVIDOR

Al iniciar con el proyecto con Maven tuve que agregar las correspondientes dependecias, y cambiar algunos valores de configuración del archivo web.xml, es algo que hemos visto en clase y no veo necesario resaltar mucho aquí. En un principio no tenía bastante claro por donde empezar, esto de las API era algo nuevo y tenía mis dudas así que el primer día de proyecto lo dediqué a investigar sobre como estaban estructuradas las api, cual era el metodo de trabajo, para que servian exactamente, en esta primera investigación ya me hice un poco a la idea de lo que podía ser el proyecto, ya sabía por donde empezar así que lo primero que hice fue lo más facil y simple que se me ocurrió y se pedía hacer en el trabajo las operaciones CRUD, así no perdía más tiempo mientras pensaba en lo demás, además es algo que llevo haciendo durante todo el curso y se podría decir que lo tenía más que asimilado.   

Con el proyecto creado y configurado empecé a escribir las primeras clases, estas fueron los modelos, los cuales coincidian con las tablas de la base de datos con la que estamos trabajando, ya que de alguna manera nos servirín de paquete para traernos las tablas a nuestro proyecto, estas clases son Carta, Jugador, Partida y Estadistica definidas según las columnas de las tablas, con sus respectivos metodo getters, setters y string, no me llevo mucho tiempo crear estas clases gracias al generador de código de Eclipse eso sí, estas clases han pasado por varias alteraciones y tuve que cambiar algun que otro valor, lo que significaba tambien cambiarlo en la base de datos, ya que tenían que coincidir.

Una vez creadas estas clases tenía que empezar a darles uso, a trabajar con ellas, así que lo siguiente que hice fue implementar la conexión con el driver de MySQL, así que agregue la dependecia al pom.xml y empecé a crear otras nuevas clases las cuales harían las operaciones con la base de datos, estas clases serían las clases controladoras ControlarCarta, ControladorJugador, ControladorParta y ControladorEstadistica. Antes de empezar a trabajar con estas clases debía definir el conector de MySQL, el cual si que medio algún problema, pero no me llevo demasiado tiempo solucionarlo, ya que se trabaja de una simple línea de código, con lo que está clase se quedó así.

```java
public class MySQLConnector {
	public static Connection getConnection() {
		Connection connection = null;
		String usuario = "root";
		String contraseña = "interfaces";
		String urlConnection = "jdbc:mysql://localhost:3306/autocartas?serverTimezone=UTC"; // Puerto por defecto: 3306											
		try {
			Class.forName("com.mysql.jdbc.Driver"); //Linea de código necesaria para una correcta conexión
			connection = DriverManager.getConnection(urlConnection, usuario, contraseña);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
}

```
Una vez creada esta clase ya me puse con los controladores los cuales realizaban las operaciones de creación, lectura, actualización y borrado sobre la base de datos, para esto utilicé las clases Statement y ResultSet que era lo que conocía. Estas clases eran las 4 nuevamente muy similares, con lo que con una ya tenía practicamente todas hechas, solo que tenía que cambiar alguna que otra cosa.

Con los metódos ya implementados hice unas pequeñas pruebas para verificar que estaban funcionando y así fue, por lo que ya tenía el CRUD de la aplicación hecho, por lo que me tocaba pasar al siguiente paso, crear los endpoints del servidor web, era algo que aún no tenía muy claro del todo así que simplemente me dedique a montar el esqueleto de los endpoints titulando los metodos, viendo cuales serían los metodos GET SET PUT DELETE, algo que no entendía del todo pero una vez estudiado no me costó tanto. Una vez tuve el esqueleto de los endpoints, completamente vacios, me quede un poco estancado porque no tenía claro el siguiente paso. Así que me puse a investigar nuevamente, que era exactamente un endpoint, su estructura y que debía contener, lo que nos lleva al siguiente apartado.

### 4. IMPLEMENTADO LOS ENDPOINTS

Una vez hecha mi investigación ya tenía mucho más claro como abarcar los endpoints, en este punto del proyecto empezaba a verlo todo más claro, y apartir de aquí mis días de proyecto empezaron a hacer un poco más productivos. 

En el anterior punto ya tenía el esqueleto de mis endpoints definidos, con las rutas que tendrían, y el tipo que eran, y si consumirian recursos o los producirian, en principio todo esto que había hecho estaba bien, pero claro todos mis metodos no devolvían nada estaban a void, por lo que tuve que ayudarme un poco del ejercicio de las apirest que estuvimos haciendo en acceso a datos, en este vi que soliamos devolver un strings, pero recordé que habiamos visto las Response, que eran respuestas http por lo que elegí la opción de devolver Response, lo cual se me confirmo despues que era lo correcto. Bien, pues mis endpoints simplemente se dedican a usar las clases controladoras para hacer las operaciones correspondiente y dependiendo de lo que devuelvan es transformado a json y se le envian al cliente, gracias a las clases controladoras los endpoitns del clud eran cosa de coser y cantar, aquí un ejemplo:

```java
	/**
	 * [ENDPOINT] Permite obtener una carta de la base de datos mediante su
	 * identificador haciendo una conversión a JSON mediante Gson.
	 * 
	 * @param identificador
	 * @return una carta en formato JSON.
	 */
	@Path("/getcard")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCarta(@QueryParam("identificador") String identificador) {
		Carta carta;
		String json;
		carta = ControladorCarta.getCarta(identificador);
		json = new Gson().toJson(carta);
		return Response.status(Response.Status.OK).entity(json).build();
	}
```



Una vez aprendí y a hacer las conversiones a JSON con GSON y devolverlos en el response todo iba más fluido. Por lo que empecé tambien a hacer los endpoints correspondientes al juego, por lo menos el esqueleto, y para esto me ayude de la grafica que aparece en el PDF en el que se comunica el cliente con el servidor.

