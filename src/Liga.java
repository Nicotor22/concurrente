
import java.util.ArrayList;
import java.util.List;

public class Liga {

    private List<Equipo> equipos;
    private List<Jornada> jornadas;

    // Constructor
    public Liga() {
        equipos = new ArrayList<>();
        jornadas = new ArrayList<>();
    }

    // Agrega un equipo a la liga
    public void agregarEquipo(Equipo equipo) {
        equipos.add(equipo);
    }

    // Agrega una jornada a la liga
    public void agregarJornada(Jornada jornada) {
        jornadas.add(jornada);
    }

    // Devuelve los equipos
    public List<Equipo> getEquipos() {
        return equipos;
    }

    // Devuelve las jornadas
    public List<Jornada> getJornadas() {
        return jornadas;
    }

    public void generarFixture() {

        if (equipos.size() != 8) {
            throw new IllegalStateException(
                "La liga debe tener exactamente 8 equipos."
            );
        }

        // Copiamos los equipos para poder rotarlos
        // sin modificar la lista original de la liga.
        List<Equipo> rotacion = new ArrayList<>(equipos);

        // Generamos las 7 jornadas
        for (int r = 0; r < 7; r++) {

            Jornada jornada = new Jornada(r + 1);

            // Emparejamos los equipos
            for (int i = 0; i < 4; i++) {

                Equipo equipo1 = rotacion.get(i);
                Equipo equipo2 = rotacion.get(7 - i);

                // Alternamos la localía
                if ((r + i) % 2 == 0) {
                    jornada.agregarPartido(
                        new Partido(equipo1, equipo2)
                    );
                } else {
                    jornada.agregarPartido(
                        new Partido(equipo2, equipo1)
                    );
                }
            }

            jornadas.add(jornada);

            // Rotamos los equipos, dejando fijo el primero
            Equipo ultimo = rotacion.remove(7);
            rotacion.add(1, ultimo);
        }
    }
}