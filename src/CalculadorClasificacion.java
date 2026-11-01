
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

public class CalculadorClasificacion implements Runnable {

    private Liga liga;
    private Clasificacion clasificacion;

    private CyclicBarrier barreraResultados;
    private CyclicBarrier barreraClasificacion;
    private CyclicBarrier barreraImpresion;

    // Constructor
    public CalculadorClasificacion(
            Liga liga,
            Clasificacion clasificacion,
            CyclicBarrier barreraResultados,
            CyclicBarrier barreraClasificacion,
            CyclicBarrier barreraImpresion) {

        this.liga = liga;
        this.clasificacion = clasificacion;
        this.barreraResultados = barreraResultados;
        this.barreraClasificacion = barreraClasificacion;
        this.barreraImpresion = barreraImpresion;
    }

    @Override
    public void run() {

        try {

            for (Jornada jornada : liga.getJornadas()) {

                // Esperamos los resultados de los partidos
                barreraResultados.await();

                // Actualizamos la clasificación
                for (Partido partido : jornada.getPartidos()) {
                    clasificacion.actualizarPartido(partido);
                }

                // Ordenamos la tabla
                clasificacion.ordenarTabla();

                // Avisamos que terminó el cálculo
                barreraClasificacion.await();

                // Esperamos a que termine la impresión
                barreraImpresion.await();
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } catch (BrokenBarrierException e) {

            System.out.println("Se interrumpió la sincronización de clasificación.");
        }
    }
}
