## 1. INTRODUCCIÓN.
## 2. DISEÑANDO LA BASE DE DATOS.
## 2. CONSTRUYENDO EL SERVIDOR
## 4. IMPLEMENTANDO LOS ENDPOINTS
## 5. PRUEBAS DE CONEXIÓN.
## 6. CREANDO EL JUEGO
## 7. DISEÑANDO EL UI
## 8. MONTANDO EL JUEGO
## 9. PREFERENCIAS
## 10. ADMINISTRACIÓN
## 11. CONCLUSIÓN

### 1. INTRODUCCIÓN

En esta memoria de trabajo/explicativa resaltaré los puntos más importantes de la creación de mi aplicación, la cual se basa en un juego de cartas de coches en el que el cliente jugará contra el servidor. Lo primero que hice a la hora de empezar con el proyecto fue comprobar que **Apache Tomcat**, el servidor web con el que voy a trabajar, no diera ningún de problema y por fortuna así fue. Tambien tenía que empezar a decidirme con que base de datos trabajar, habiendo muchas opciones al final me decidí por **MySQL**, que es además lo que se recomendaba para hacer el proyecto. Lo siguiente a pensar fue ¿implemento yo todos los modelo o dejo que Hibernate lo hago por mi?, la primerá opción era Hibernate, además me gustaba la idea de ahorrar tiempo contruyendo clases, pero al conectar la base de datos con este framework encontraba varios errores, investigué un poco pero no me extendí mucho, ya que siempre podía construir las clases a mano y utilizar el driver de MySQL, así que mediante Maven agregué las correspondientes dependencias para poder empezar a trabajar, así que para la creación de la base de datos tuve que utilizar MySQL Workbench.

### 2. DISEÑANDO LA BASE DE DATOS 

Lo primero que necesitaba para empezar a trabajar era una base de datos sobre la cual
operar por lo que procedi a instalarme SQLWorkbench para crear mi base de datos
autocartas, que esta formadas por 4 tablas cartas, jugadores, partidas, estadisticas en el
cual la tablas estadisticas y jugadores tienen relacion con las tablas jugadores y
partidas, dejo un mockup.

![Mockup] (Mochup.png)

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
	@Path("/getcard/{identificador}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCarta(@PathParam("identificador") String identificador) {
		Carta carta;
		String json;
		carta = ControladorCarta.getCarta(identificador);
		json = new Gson().toJson(carta);
		return Response.status(Response.Status.OK).entity(json).build();
	}
```



Una vez aprendí y a hacer las conversiones a JSON con GSON y devolverlos en el response todo iba más fluido. Por lo que empecé tambien a hacer los endpoints correspondientes al juego, por lo menos el esqueleto, y para esto me ayude de la grafica que aparece en el PDF en el que se comunica el cliente con el servidor. Despues de hacer esto empecé a trabajar en el primer endpoint que contenía la clase del juego, el login. No me compliqué mucho la vida haciendo un login, pude haber usado metodos de para encriptar el código y cosas así, pero decidí por hacerlo basico, un simple equals que comprobara que el usuario introducido coincide con la contraseña introducida, no quería perder mucho tiempo ya que le tenía un miedo especial a los siguientes endpoints. Mientras hacia el login me surgío la duda sobre si podía devolver respuestas negativas en caso de que haya fallado, sabía que sí pero no sabía como, así que tuve que preguntar, se me resolvió rapidamente, lo que me dio un poco de más juego con los endpoints. Una vez hice el login, sabía que tenía que empezar a implementar un poco la logica y funcionamiento del juego, lo cual me daba un poco de miedo ya que no tenía muy claro como iba a conseguir que esto funcionase, por lo que me puse a hacer un endpoint de registro lo cual creía que sería algo facil de implementar y que me daba tiempo a pensar un poco más. 

Con esto hecho ya tenía parte del proyecto hecha, y las cosas más claras, pero no tenía mucho del juego en sí, por lo que la idea era empezar ya, pero volví a aplazar un poquito más esto ya que en clase de programación multimedia se nos explicó lo que era el Retrofit, que era lo que utiliziamos para comunicarnos con nuestro servidor desde Android, era algo indispensable que tenía que controlar antes de continuar, comunicar cliente y servidor, así que empecé un nuevo proyecto en Android.

### 5. PRUEBAS DE CONEXIÓN.

Llegados a este punto empecé con el proyecto de Android, cree las clases necesarias para trabajar con Retrofit, no me costó mucho entender el funcionamiento de este, así que desde un principio me lancé a hacer consultas al servidor con lo más basico, como que me devuelva todas las cartas, jugadores, partidas y estadisticas. 

La primera vez no funcionó debidó a que el Retrofit me acortaba la URL y no cogía la ruta que le había dicho, solo una parta mi ip y el puerto, se ve que no le gustaba, por lo que la solución rapida que le di fue pasarle la url restante a traves de la interfaz de la API, lo que hice que funcionasen los get, por ahora iba todo bien, ya habñia conseguido conectar con el servidor, iba todo perfecto hasta que me puse a diseñar el login y hacer las pertinentes comprobaciones, me tocó hacer la primera POST al servidor y en un principio no funcionaba, no entendía por qué, si estaba todo perfecto, había seguido bien los pasos correctamente, por lo que tuve que ayudarme de ambos profesores, estuvimos mirando cual era el problema y se me dió una pista de lo que podría ser, lo que pasaba era que Retrofit los metodos PUT los envia con URLENCODED y yo en mi proyecto no tenía ningun metodo que consumiera este tipo, así que cuando hacía el PUT iba a parar a ningún lado, al final la solución fue consumir en el endpoint un MediaType.APPLICATION_FORM_URLENCODED lo que soluciono por completo el problema.

```java
/**
	 * Verifica que el usuario/jugador existe en la base de datos y que la
	 * contraseña introducida es correcta
	 * 
	 * @param Un jugador con su usuario y contraseña.
	 * @return Response.OK con la información del jugador.
	 * @return Response LOGIN FAIL si el jugador no existe o la contraseña no es
	 *         correcta
	 */
	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("nickname") String nickname, @FormParam("password") String password) {
		ArrayList<Jugador> jugadores;
		Jugador jugador;
		String json = "";
		jugadores = ControladorJugador.getAll();
		for (int i = 0; i < jugadores.size(); i++) {
			if (nickname.equals(jugadores.get(i).getNickname()) && password.equals(jugadores.get(i).getPassword())) {
				jugador = jugadores.get(i);
				json = new Gson().toJson(jugador);
				return Response.status(Response.Status.OK).entity(json).build();
			}
		}
		return Response.status(401).entity(TAG + ": Login fail.").build();
	}
```
A partir de aquí no tuve ningún problema para conectarme con el servidor, excepto por cosas tribiales y cositas como cambios en la ip. Por la parte de android tendría que implementar los metodos restantes en la interfaz. Pero es algo que dejo para más adelante y está vez si me puse a trabajar en la logíca del juego como tal.

### 6. CREANDO EL JUEGO

Creo que la API del juego es lo que más me ha costado, esta clase la podemos encontrar en el proyecto como IniciarAPI que es la que contiene los endpoints del juego. Aquí tenemos los endpoints que login y registrar que comprueban y registran nuevos jugadores, pero son endpoints de los que ya he hablado. El siguiente endpoint se trataba de crear una nueva partida, siempre y cuando el jugador no tuviese ya una en marcha, por lo que pude entender cuando el jugador tuviese una partida en marcha esta debía finalizarse, esto mediante otro endpoint, y empezar una nueva, en cualquiera de los dos casos devolver el id de la partida. Lo primero que pensé fue en devolver una respuesta negativa, un 400 en caso de que la partida existiese y un 200 en caso de que la partida no existiese. Yo lo veía correcto, pero a la hora de obtener los datos de una respuesta con el Retrofit necesitaba una respuesta positiva, cuando intenté obtener el body de una respuesta negativa venía vacio, por lo que me ocurrió devolver dos respuestas positivas, en caso de que el jugador tenga una partida en marcha devolverá 202 (ACEPTADO), en caso de que no tenga una partida en marcha devuelve 200 (OK), y esto sí que podía manejarlo en el retrofit mediante el metodo code().

Ya podría crear partidas nuevas, esa parte estaba implementada, ahora tocaba de en caso de que el jugador ya tuviera una partida en marcha acabarla, para que pueda empezar otra nueva lo que nos lleva al endpoint reset. Este endpoint se encarga simplemente de acabar las partidas no finalizadas devolviendo la partida terminada, para volver a llamar a newgame. El siguiente metodo me parecía complicado y consistia en repartir 12 cartas, sin que estas se repitan ni en la mano del jugador ni en la mano de la máquina, debía ser todas completamanete diferentes y aleatorios, claro está.

Así que se me ocurrio crear una nueva clase a la que llamo RandomNoRepeat, se encuentra en el paquete librería, y lo que hace es devolver un array de numeros aleatorios enteros sin que estos se repitan, para esto genero un random de manera recursiva comprobando siempre si el array no tiene ya ese nuemero, por lo que en caso de tener el nuemero vuelve a llamarse a el mismo esto hasta tener el arreglo de numeros no repetidos completo.

```java
public class RandomNoRepeat {

   public static ArrayList<Integer> randomNoRepeat (int cantidad, int rangg) {
       ArrayList<Integer> numeros = new ArrayList<>();
       for (int i = 0; i < cantidad; i++) {
           numeros.add(generarRandom(numeros, rangg));
       }
       return numeros;
   }

   private static int generarRandom(ArrayList<Integer> numeros, int rango) {
       Random ra = new Random();
       int numero = ra.nextInt(rango);
       if (!numeros.contains(numero)) {
           return numero;
       }else {
           return generarRandom(numeros, rango);
       }
   }	
}
```
Esta clase tenía sus pegas como ¿que pasaría si le paso una cantidad de numeros superior en un rango menor? Es decir, generame 5 numeros aleatorios en un rango de 3, no podría hacer eso, tendrían que repetirse dos numeros al menos una vez, pero por el momento esto no suponía ningun problema, ya que sabia que esto podría pasar.
Volviendo al endpoint de repartir, ahora debía repartir las cartas de todas las cartas que tenía en la base de datos, para ello utilizaba mi clase que genera numeros aleatorios, así que se me ocurrio hacer esta función en otra clase llamada JuegoOperations en el que tiene el metodo repartir(), que lo que hace recorrer el array de numeros random no repetidos asignando las 6 primeras posiciones a el jugador y las 6 ultimas a la máquina. Además este endpoint debía decir quien empezaba eligiendo la caractersitica, esto lo solucioné haciendo un Random en un rango de 0 y 1, si salía 0 empezaba el jugador, por lo que respondía un 200 (OK), en caso de salir 1 empezaba la máquina por lo que respondía 202(ACEPTED), aparte de devolver las cartas del jugador, el id del juego, y las de la máquina, así que tuve que crear otra case modelo donde empaquetar toda esta información para enviarla, la llamé Juego.
Ahora había llegado al endpoint de playcard, donde el usuario indicaba su jugada o su ataque, para esto tuve que crearme otra clase modelo donde empaquetar toda esta información para así poder enviarla y recibirla mejor, esta clase se llama Jugada, donde se indicar el nickname del jugador (idSession) y el id de la partida (idGame), aparte de la carta, la caracteritica y la mano, para la caracteristica me decidí por crear un enumerado que contenga todas las caracteristicas.
Esta clase tenía sus pegas como ¿que pasaría si le paso una cantidad de numeros superior en un rango menor? Es decir, generame 5 numeros aleatorios en un rango de 3, no podría hacer eso, tendrían que repetirse dos numeros al menos una vez, pero por el momento esto no suponía ningun problema, ya que sabia que esto podría pasar. 

Volviendo al endpoint de repartir, ahora debía repartir las cartas de todas las cartas que tenía en la base de datos, para ello utilizaba mi clase que genera numeros aleatorios, así que se me ocurrio hacer esta función en otra clase llamada JuegoOperations en el que tiene el metodo repartir(), que lo que hace recorrer el array de numeros random no repetidos asignando las 6 primeras posiciones a el jugador y las 6 ultimas a la máquina. Además este endpoint debía decir quien empezaba eligiendo la caractersitica, esto lo solucioné haciendo un Random en un rango de 0 y 1, si salía 0 empezaba el jugador, por lo que respondía un 200 (OK), en caso de salir 1 empezaba la máquina por lo que respondía 202(ACEPTED), aparte de devolver las cartas del jugador, el id del juego, y las de la máquina, así que tuve que crear otra case modelo donde empaquetar toda esta información para enviarla, la llamé Juego.

Ahora había llegado al endpoint de playcard, donde el usuario indicaba su jugada o su ataque, para esto tuve que crearme otra clase modelo donde empaquetar toda esta información para así poder enviarla y recibirla mejor, esta clase se llama Jugada, donde se indicar el nickname del jugador (idSession) y el id de la partida (idGame), aparte de la carta, la caracteritica y la mano, para la caracteristica me decidí por crear un enumerado que contenga todas las caracteristicas.

Ahora tocaba implementar el metodo o endpoint ready, algo que me generó mucho confusión, pero creo que lo que he hecho en este end point puede funcionar, aunque puede que tenga que modificarla en su versión final, este ready indica que el jugador está listo y la cpu puede hacer su jugada, hay veces que el jugador no elige la caracteristica y tiene que hacerlo la máquina, esto lo detecto porque en los parametros tambien se pasa la caracteristica seleccionada, en caso de ser null la máquina procderá a elegir una caracteristica además de elegir la carta que va a tirar, esto de manera aleatoria, para ser posteriormente removida del array ya que es carta la ha tirado.

Para el siguiente endpoint tambien decidí crear otra clase la cual se encarga de manejar el resultado de la partida, esta clase se llama HandResult, la cual aumenta los puntos del jugador o de la maquina, esto lo hace dependiendo de las caracteristicas y las especificaciones de cada carta , por lo que este endpoint se encarga de asignar los puntos correspondientes según la caracteristica. Llegados a este punto la parte de la API estaba casi completada, probablente tendría que hacer algunos ajustes al hacer la implementación en el cliente, pero solo quedaba devolver los resultados o estadisticas de la partida que se ha jugado, por lo que debía hacer una consulta de las estadisticas del juego.

Ahora tocaba implementar el metodo o endpoint ready, algo que me generó mucho confusión, pero creo que lo que he hecho en este end point puede funcionar, aunque puede que tenga que modificarla en su versión final, este ready indica que el jugador está listo y la cpu puede hacer su jugada, hay veces que el jugador no elige la caracteristica y tiene que hacerlo la máquina, esto lo detecto porque en los parametros tambien se pasa la caracteristica seleccionada, en caso de ser null la máquina procderá a elegir una caracteristica además de elegir la carta que va a tirar, esto de manera aleatoria, para ser posteriormente removida del array ya que es carta la ha tirado.
Para el siguiente endpoint tambien decidí crear otra clase la cual se encarga de manejar el resultado de la partida, esta clase se llama HandResult, la cual aumenta los puntos del jugador o de la maquina, esto lo hace dependiendo de las caracteristicas y las especificaciones de cada carta , por lo que este endpoint se encarga de asignar los puntos correspondientes según la caracteristica. Llegados a este punto la parte de la API estaba casi completada, probablente tendría que hacer algunos ajustes al hacer la implementación en el cliente, pero solo quedaba devolver los resultados o estadisticas de la partida que se ha jugado, por lo que debía hacer una consulta de las estadisticas del juego.

### 7. DISEÑANDO EL UI

Una vez con  con la API Rest completada me puse a hacer los primeros diseños para la interfaz de usuarios, no quería dedicarle mucho tiempo a la interfaz ya que lo que buscaba al final es que el juego funcionase, si llegaba a conseguirlo considerable problablemente me hubiese currado más la UI. Bueno, empecé construyendo un Login, ya que quería que fuera la primera ventana que vieran los usuarios al encender la aplicación, puse dos EditText y un Button para empezar a hacer las primeras pruebas de conexión con Retrofit con el servidor, esto es algo que he mencionado en la anterior parte, el problema con los PUT y URLENCODED. Empecé diseñando todo mi programa en modo PORTRAIT, es decir vertical, lo cual me gustaba por el momento. El login estaba listo así que tocaba hacer una ventana de registro así que me puse con ello y no tarde mucho en hacerlo ya que dominaba lo suficiente el Retrofit.

Ahora era el turno de hacer la ventana de Inicio del juego, se me ocurrió mostrar las estadisticas de cada jugador que había jugado una partida en una parte de la actividad y por otro lado varios botones, uno para iniciar la partida y otro para elegir las preferencias. Lo primero que tenía que hacer era crear el adaptador para el RecyclerView de estadisticas y que se mostraran los resultados de la tabla estadisticas,  para ver resultados tuve que meter estadisticas de prueba ya que si no aparecía un RecyclerView en blanco. 
La siguiente ventana o actividad sería donde se jugaría la partida, empecé poniendo los botones para que el jugador pudiese elegir la caracteristica, tambien se me ocurrio mostrar las cartas en un RecyclerView, eran 6 en total, pero claro sentía que el espacio no era el adecuado así que decidí hacer mi aplicación LANDSCAPE, en decir en horizontal, lo cual ve llevo cambiar un el diseño, además quería que el juego se viese a pantalla completa, por lo que tuve que ayudarme de internet para hacerlo, así aprovechaba mejor el espeacio y mostraba las cartas con más claridad. Tambien tenía pensaba implementar imagenes pero aún tenía cosas por hacer y no iba a pararme ahí.

![Interfaz] (Interfaz.png)

### 8. MONTANDO EL JUEGO

Despues de implementar el código para las llamadas al login y register de API, de haber implementado los modelos necesarios para realizar esta llamada, recoger la información y haber creado algunos adaptadores era la hora de hacer funcionar el juego en el cliente, cosa que me parecía que no sería del todo complicada, ya que tenía el API completada. Al principio no tenái muy claro como lo iba a hacer ya que tenía varias dudas, al final la partida se juega en el servidor, así que las decisiones las operaciones las hace este en una clase llamada JuegoOperations, especé a escribir codigo con el fin de que me funcionase el juego pero me ha costado más de lo que esperaba, ya que presenta varios errores, como que siempre es el server el que elige la caracteristica o he llegado a ver que en un telefono mi RecyclerView no funciona como debe, no retira la cartas, además de que solo se puede jugar una partida, cuando se juega esta partida y marcador no actua como espero, por lo que habría que reiniciar el servidor, creo que se por donde van los tiros, pero me ha faltado tiempo para solucionarlo, por lo que el funcionamiento del juego no es el correcto.

### 9. PREFERENCIAS

La aplicación debía tener preferencias de usuario, así que me puse con ello, añadi un botón de preferencias al menú ya que no venía sentido poner una toolbar en modo pantalla completa y horizontal. Realmente no tenía suficientes datos para rellenar las preferencias así que poner el momento implementé la interfaz con datos ficticios, aunque no tanto, ya que despues serían funciones que tendría la aplicación. Como por ejemplo los datos de conexión, es decir el usuario y la contraseña. Unas preferencias de notificaciones, pensé que los juegos suelen tener muchas notificaciones, como la notificación de cuando recuperas una vida en el Candy Crush, tambíen puse la opción de habilitar el bot automatico que juega por ti, a paser de no estar implementando en el proyecto final lo puse ya que era una tarea a realizar, tambien puse la opción de quitar o poner el sonido, esto de manera ficticia, ya que mi juego no dispone de sonido, pero es una idea que podría haber ocurrido y por ultimo metí la selección del nivel de dificultad, donde se podría elegir que tal listo fuese el adversario, algo que tampoco está implementado.

### 10. ADMINISTRACIÓN

El cliente tambien debía contar con modo desarrollador, donde este pudiese realizar operaciones CRUD. Lo primero que pensé es que podría haber una manera de implementar esto sin tener que hacer otro activity que lleve a estas opciones, así que busqué algo por el estilo pero no encontré nada relacionado, lo que me llevo a hacer otra actividad con estas opciones CRUD, la cual se habilita al poner en el Login admin admin, lo más tipico.

![Admin] (Admin.png)


Esto nos llevaba a la actividad que se ve más arriba donde se puede gestionar las cartas, los jugadores y las partidas, eran botones que llevaban a otras actividades donde se podían ingresar los datos y hacer la operación pertinente, además de mostrarse los datos que habían en las tablas. 

![Admin] (Admin2.png)

### 10. CONCLUSIÓN

En este proyecto he podido ver todo lo que he aprendido, o almenos lo más importante  visto durante los dos años del ciclo, además de lo que me queda por perfeccionar o mejorar, y a paser de que la practica no esta complementate perfecta o terminada al 100% refleja mi nivel programación y manejo de datos, ha sido una buena practica y es posible que intente mejorarla una vez finalizada.

