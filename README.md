# concurrente
# El programa de concurrente tp final
# TP de Procesos Ligeros en Java
# Integrantes: Nicolás Torandell Ballesteros, Marcos Giordano, Gonzalo Redondo

# Liga de fútbol – Explicación del funcionamiento del programa

# 1. ¿En qué consiste el programa?

El programa simula el desarrollo de una liga de fútbol formada por ocho equipos ficticios, que compiten bajo el formato de todos contra todos a una sola vuelta.

Esto significa que cada equipo debe enfrentarse una vez contra cada uno de los otros siete equipos.

Las características del campeonato son:

- 8 equipos participantes.
- 7 jornadas.
- 4 partidos por jornada.
- 28 partidos en total.
- Resultados generados aleatoriamente.
- 3 puntos por victoria, 1 por empate y 0 por derrota.

Al finalizar cada jornada, el programa actualiza las estadísticas de los equipos y muestra una tabla de posiciones ordenada según los siguientes criterios:

1. Mayor cantidad de puntos.
2. Mayor diferencia de goles.
3. Mayor cantidad de goles a favor.
4. Orden alfabético.

El objetivo del trabajo es utilizar procesos ligeros (hilos) para dividir las tareas del programa y ejecutarlas de manera concurrente, utilizando mecanismos de sincronización para coordinar su funcionamiento.

# 2. ¿Cómo está organizado el programa?

El programa está dividido en diez clases. Cada una tiene una responsabilidad específica.

*Equipo.java*

Representa a cada uno de los equipos participantes.

Almacena la información y las estadísticas de cada equipo:

- Nombre.
- Partidos jugados.
- Partidos ganados.
- Partidos empatados.
- Partidos perdidos.
- Goles a favor.
- Goles en contra.
- Puntos obtenidos.

También dispone de métodos para consultar y modificar estos valores.

*Partido.java*

Representa un encuentro entre dos equipos.

Almacena:
- Equipo local.
- Equipo visitante.
- Goles del equipo local.
- Goles del equipo visitante.

Los goles se inicializan en -1 para indicar que el encuentro todavía no tiene un resultado generado.

*Jornada.java*

Representa una fecha del campeonato.

Contiene un número identificador y una lista de cuatro partidos. También permite agregar encuentros y consultar los partidos correspondientes a esa jornada.

*Liga.java*

Administra el campeonato completo.

Contiene las listas de equipos y jornadas. Permite incorporar equipos y jornadas, y cuenta con el método `generarFixture()`, encargado de crear automáticamente los enfrentamientos.

Para generar el fixture utiliza un algoritmo de rotación: mantiene fijo al primer equipo y rota las posiciones de los demás para formar los emparejamientos de cada jornada.

De esta manera se generan las siete jornadas sin repetir enfrentamientos ni hacer que un equipo juegue dos veces en una misma fecha.

*Clasificacion.java*

Se encarga de administrar la tabla de posiciones.

Recibe los resultados de los partidos y actualiza las estadísticas de los equipos. También ordena la clasificación según los criterios establecidos.

*GeneradorGolesLocales.java*

Es una de las tareas concurrentes del programa.

Se encarga de generar aleatoriamente los goles de todos los equipos que juegan como locales durante cada jornada.

*GeneradorGolesVisitantes.java*

Es la segunda tarea concurrente.

Genera los goles de los equipos visitantes. Trabaja simultáneamente con el generador de goles locales, pero modifica un atributo diferente de cada partido.

*CalculadorClasificacion.java*

Es el hilo responsable de actualizar la tabla de posiciones.

Espera a que los dos generadores terminen de producir los resultados de una jornada. Después recorre todos los partidos, actualiza las estadísticas de los equipos y ordena la clasificación.

*ImpresorClasificacion.java*

Es el hilo encargado de mostrar la tabla de posiciones por pantalla.

Espera a que se termine de calcular la clasificación antes de imprimirla. Una vez mostrada la tabla, permite que comience la siguiente jornada.

*Main.java*

Es la clase principal del programa.

Se encarga de crear la liga, incorporar los ocho equipos, generar el fixture, crear la clasificación, configurar las barreras de sincronización, instanciar los cuatro hilos e iniciarlos.

# 3. ¿Cómo funcionan los procesos ligeros?

La parte más importante del trabajo es la utilización de cuatro hilos independientes.

En Java, un hilo (Thread) representa un flujo de ejecución dentro de un mismo programa.

Esto permite dividir el trabajo en distintas tareas que pueden ejecutarse concurrentemente.

En nuestro programa tenemos cuatro hilos:

|             Hilo           | Responsabilidad |
| Generador de goles locales | Genera los goles de los locales. |
| Generador de goles visitantes | Genera los goles de los visitantes. |
| Calculador de clasificación | Actualiza y ordena la tabla. |
| Impresor de clasificación | Muestra la tabla de posiciones. |

Los cuatro hilos trabajan sobre las mismas estructuras de datos compartidas, principalmente el objeto Liga y sus partidos.

Cada uno tiene una responsabilidad diferente, evitando que todos tengan que realizar las mismas operaciones.

Los dos generadores pueden trabajar al mismo tiempo porque modifican atributos distintos: uno escribe los goles locales y el otro los goles visitantes.

En cambio, el cálculo de la clasificación debe esperar a que ambos hayan terminado.

# 4. ¿Cómo se sincronizan los hilos?

Para coordinar las tareas utilizamos `CyclicBarrier`, una herramienta de Java que permite que varios hilos esperen en un punto determinado hasta que todos hayan llegado.

En nuestro caso utilizamos tres barreras, cada una con cuatro participantes, porque tenemos cuatro hilos.

Cada jornada se desarrolla en tres etapas:

*Primera etapa: generación de resultados*

Los hilos generadores trabajan simultáneamente:

- El primero genera los goles locales.
- El segundo genera los goles visitantes.

Mientras tanto, los otros dos hilos esperan en la primera barrera.

Cuando los cuatro llegan a esa barrera, significa que todos los resultados están completos y se puede continuar.

*Segunda etapa: cálculo de la clasificación*

El hilo calculador actualiza las estadísticas de los ocho equipos utilizando los resultados de los cuatro partidos.

Después ordena la tabla de posiciones.

Los otros tres hilos esperan en la segunda barrera.

Cuando los cuatro llegan, significa que la clasificación está actualizada y puede mostrarse.

*Tercera etapa: impresión de la clasificación*

El hilo impresor muestra la tabla de posiciones correspondiente a la jornada.

Los otros tres hilos esperan en la tercera barrera.

Cuando todos llegan a ella, se permite que comience la siguiente jornada.

Este mecanismo se repite hasta completar las siete jornadas.

# Esquema del funcionamiento

1. Los dos generadores producen los resultados en paralelo.
2. Primera barrera: todos esperan a que terminen los resultados.
3. El calculador actualiza las estadísticas.
4. Segunda barrera: todos esperan a que termine el cálculo.
5. El impresor muestra la clasificación.
6. Tercera barrera: todos esperan a que termine la impresión.
7. Comienza la siguiente jornada.

La utilización de las tres barreras evita que un hilo avance antes de que se complete la tarea de la etapa anterior.

# 5. ¿Qué conceptos de Java estamos utilizando?

El programa permite aplicar varios conceptos importantes de programación.

*Programación orientada a objetos*

Utilizamos clases para representar los elementos del problema: equipos, partidos, jornadas y liga.

Cada clase tiene sus propios atributos y métodos, lo que permite organizar el código y separar responsabilidades.

*Runnable*

Las clases que representan las tareas concurrentes implementan la interfaz `Runnable`.

Esta interfaz obliga a definir el método `run()`, que contiene las instrucciones que ejecutará el hilo.

*Thread*

La clase `Thread` permite crear e iniciar los hilos.

Por ejemplo:

`Thread hiloLocales = new Thread(new GeneradorGolesLocales(...));`

De esta manera asociamos una tarea con un nuevo hilo de ejecución.

*start()*

El método `start()` inicia el hilo y permite que ejecute su método `run()` de forma independiente.

No es lo mismo que llamar directamente a `run()`, ya que esto último ejecutaría las instrucciones dentro del hilo que realizó la llamada.

*CyclicBarrier*

Permite sincronizar los cuatro hilos en puntos determinados de la ejecución.

Cada barrera espera a que lleguen sus cuatro participantes antes de permitirles continuar.

*join()*

Permite que el hilo principal espere a que otro hilo termine.

En nuestro programa utilizamos `join()` para asegurarnos de que los cuatro hilos hayan finalizado antes de mostrar el mensaje de finalización de la liga.

*ArrayList y List*

Utilizamos listas para almacenar los equipos, las jornadas y los partidos.

Esto permite administrar una cantidad variable de objetos y recorrerlos mediante estructuras repetitivas.

*ThreadLocalRandom*

Se utiliza para generar los goles aleatorios de los encuentros.

La expresión `nextInt(0, 6)` genera valores entre 0 y 5.

---

# 6. ¿Cómo se ejecuta el programa desde el principio hasta el final?

El funcionamiento completo comienza en `Main.java`.

1. Se crea un objeto Liga.
2. Se incorporan los ocho equipos participantes.
3. Se genera automáticamente el fixture de siete jornadas.
4. Se crea el objeto Clasificacion, que administrará las estadísticas.
5. Se crean las tres barreras de sincronización, cada una configurada para cuatro participantes.
6. Se instancian los cuatro hilos.
7. Se inician los cuatro hilos mediante `start()`.
8. Los hilos ejecutan sus respectivas tareas durante las siete jornadas.
9. El hilo principal espera la finalización de todos mediante `join()`.
10. Cuando todos terminan, se muestra el mensaje indicando que la liga finalizó.

El resultado es una simulación completa de un campeonato de fútbol, con una tabla de posiciones que se actualiza después de cada jornada.

# 7. Conclusión

El programa permite simular una liga de fútbol y, al mismo tiempo, aplicar los conceptos de procesos ligeros y sincronización en Java.

Se utiliza programación orientada a objetos para representar los elementos del campeonato y cuatro hilos independientes para dividir las tareas principales.

La utilización de `CyclicBarrier` permite coordinar los procesos y garantizar que los resultados estén completos antes de calcular la clasificación, y que la clasificación esté actualizada antes de imprimirla.

De esta manera, se consigue una solución organizada y concurrente para el problema planteado.
