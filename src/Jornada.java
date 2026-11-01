
import java.util.ArrayList;
import java.util.List;

public class Jornada {

    private int numero;
    private List<Partido> partidos;

    // Constructor
    public Jornada(int numero) {
        this.numero = numero;
        partidos = new ArrayList<>();
    }

    // Agrega un partido a la jornada
    public void agregarPartido(Partido partido) {
        partidos.add(partido);
    }

    // Devuelve los partidos de la jornada
    public List<Partido> getPartidos() {
        return partidos;
    }

    // Devuelve el número de jornada
    public int getNumero() {
        return numero;
    }
}
