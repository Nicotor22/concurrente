
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

public class ImpresorClasificacion implements Runnable {

    private Clasificacion clasificacion;
    private CyclicBarrier barreraResultados;
    private CyclicBarrier barreraClasificacion;
    private CyclicBarrier barreraImpresion;

    // Constructor
    public ImpresorClasificacion(
            Clasificacion clasificacion,
            CyclicBarrier barreraResultados,
            CyclicBarrier barreraClasificacion,
            CyclicBarrier barreraImpresion) {

        this.clasificacion = clasificacion;
        this.barreraResultados = barreraResultados;
        this.barreraClasificacion = barreraClasificacion;
        this.barreraImpresion = barreraImpresion;
    }

    @Override
    public void run() {

        try {

            int numeroJornada = 1;

            for (int i = 0; i < 7; i++) {

                // Esperamos a que se generen todos los resultados
                barreraResultados.await();

                // Esperamos a que se actualice la clasificación
                barreraClasificacion.await();

                // Imprimimos la tabla
                System.out.println("\n==================================");
                System.out.println("       JORNADA " + numeroJornada);
                System.out.println("==================================");

                System.out.printf(
                    "%-22s %4s %4s %4s %4s %4s %4s %4s%n",
                    "Equipo", "PJ", "PG", "PE", "PP",
                    "GF", "GC", "PTS"
                );

                for (Equipo equipo : clasificacion.getTabla()) {

                    System.out.printf(
                        "%-22s %4d %4d %4d %4d %4d %4d %4d%n",
                        equipo.getNombre(),
                        equipo.getPartidosJugados(),
                        equipo.getGanados(),
                        equipo.getEmpatados(),
                        equipo.getPerdidos(),
                        equipo.getGolesFavor(),
                        equipo.getGolesContra(),
                        equipo.getPuntos()
                    );
                }

                numeroJornada++;

                // Esperamos a que todos terminen esta jornada
                barreraImpresion.await();
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } catch (BrokenBarrierException e) {

            System.out.println("Se interrumpió la sincronización de impresión.");
        }
    }
}
