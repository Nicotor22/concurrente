
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.BrokenBarrierException;

public class GeneradorGolesLocales implements Runnable {

    private Liga liga;
    private CyclicBarrier barreraResultados;
    private CyclicBarrier barreraClasificacion;
    private CyclicBarrier barreraImpresion;

    // Constructor
    public GeneradorGolesLocales(
            Liga liga,
            CyclicBarrier barreraResultados,
            CyclicBarrier barreraClasificacion,
            CyclicBarrier barreraImpresion) {

        this.liga = liga;
        this.barreraResultados = barreraResultados;
        this.barreraClasificacion = barreraClasificacion;
        this.barreraImpresion = barreraImpresion;
    }

    @Override
    public void run() {

        try {

            for (Jornada jornada : liga.getJornadas()) {

                // Generamos los goles de los equipos locales
                for (Partido partido : jornada.getPartidos()) {

                    int goles = ThreadLocalRandom.current().nextInt(0, 6);

                    partido.setGolesLocal(goles);
                }

                // Esperamos a que todos terminen de generar resultados
                barreraResultados.await();

                // Esperamos a que se calcule la clasificación
                barreraClasificacion.await();

                // Esperamos a que se imprima la tabla
                barreraImpresion.await();
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } catch (BrokenBarrierException e) {

            System.out.println("Se interrumpió la sincronización de resultados.");
        }
    }
}
