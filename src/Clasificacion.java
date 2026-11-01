
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Clasificacion {

    private List<Equipo> tabla;

    // Constructor
    public Clasificacion(List<Equipo> equipos) {
        tabla = new ArrayList<>(equipos);
    }

    // Actualiza las estadísticas de un partido
    public void actualizarPartido(Partido partido) {

        Equipo local = partido.getLocal();
        Equipo visitante = partido.getVisitante();

        int golesLocal = partido.getGolesLocal();
        int golesVisitante = partido.getGolesVisitante();

        // Actualizamos partidos jugados
        local.setPartidosJugados(local.getPartidosJugados() + 1);
        visitante.setPartidosJugados(visitante.getPartidosJugados() + 1);

        // Actualizamos goles
        local.setGolesFavor(local.getGolesFavor() + golesLocal);
        local.setGolesContra(local.getGolesContra() + golesVisitante);

        visitante.setGolesFavor(visitante.getGolesFavor() + golesVisitante);
        visitante.setGolesContra(visitante.getGolesContra() + golesLocal);

        // Determinamos el resultado
        if (golesLocal > golesVisitante) {

            local.setGanados(local.getGanados() + 1);
            visitante.setPerdidos(visitante.getPerdidos() + 1);

            local.setPuntos(local.getPuntos() + 3);

        } else if (golesLocal < golesVisitante) {

            visitante.setGanados(visitante.getGanados() + 1);
            local.setPerdidos(local.getPerdidos() + 1);

            visitante.setPuntos(visitante.getPuntos() + 3);

        } else {

            local.setEmpatados(local.getEmpatados() + 1);
            visitante.setEmpatados(visitante.getEmpatados() + 1);

            local.setPuntos(local.getPuntos() + 1);
            visitante.setPuntos(visitante.getPuntos() + 1);
        }
    }

    // Ordena la tabla de posiciones
    public void ordenarTabla() {

        tabla.sort(
            Comparator.comparingInt(Equipo::getPuntos).reversed()
            .thenComparing(
                Comparator.comparingInt(Equipo::getDiferenciaGoles).reversed()
            )
            .thenComparing(
                Comparator.comparingInt(Equipo::getGolesFavor).reversed()
            )
            .thenComparing(Equipo::getNombre)
        );
    }

    // Devuelve la tabla ordenada
    public List<Equipo> getTabla() {
        return tabla;
    }
}
