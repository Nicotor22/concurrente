
import java.util.concurrent.CyclicBarrier;

public class Main {

    public static void main(String[] args) {

        // 1. Creamos la liga
        Liga liga = new Liga();

        // 2. Creamos los ocho equipos
        liga.agregarEquipo(new Equipo("Atletico del Sur"));
        liga.agregarEquipo(new Equipo("Deportivo Aurora"));
        liga.agregarEquipo(new Equipo("Union del Puerto"));
        liga.agregarEquipo(new Equipo("Real Montana"));
        liga.agregarEquipo(new Equipo("Racing del Valle"));
        liga.agregarEquipo(new Equipo("Sporting Central"));
        liga.agregarEquipo(new Equipo("Ferroviario Norte"));
        liga.agregarEquipo(new Equipo("Estrella Roja"));

        // 3. Generamos el fixture
        liga.generarFixture();

        // 4. Creamos la clasificacion
        Clasificacion clasificacion =
            new Clasificacion(liga.getEquipos());

        // 5. Creamos las tres barreras
        CyclicBarrier barreraResultados = new CyclicBarrier(4);
        CyclicBarrier barreraClasificacion = new CyclicBarrier(4);
        CyclicBarrier barreraImpresion = new CyclicBarrier(4);

        // 6. Creamos los cuatro hilos
        Thread hiloLocales = new Thread(
            new GeneradorGolesLocales(
                liga,
                barreraResultados,
                barreraClasificacion,
                barreraImpresion
            )
        );

        Thread hiloVisitantes = new Thread(
            new GeneradorGolesVisitantes(
                liga,
                barreraResultados,
                barreraClasificacion,
                barreraImpresion
            )
        );

        Thread hiloClasificacion = new Thread(
            new CalculadorClasificacion(
                liga,
                clasificacion,
                barreraResultados,
                barreraClasificacion,
                barreraImpresion
            )
        );

        Thread hiloImpresion = new Thread(
            new ImpresorClasificacion(
                clasificacion,
                barreraResultados,
                barreraClasificacion,
                barreraImpresion
            )
        );

        // 7. Iniciamos los hilos
        hiloLocales.start();
        hiloVisitantes.start();
        hiloClasificacion.start();
        hiloImpresion.start();

        // 8. Esperamos que todos terminen
        try {

            hiloLocales.join();
            hiloVisitantes.join();
            hiloClasificacion.join();
            hiloImpresion.join();

            System.out.println("\nLa liga ha finalizado.");

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            System.out.println("El programa fue interrumpido.");
        }
    }
}
