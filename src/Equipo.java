
public class Equipo {

    private String nombre;

    private int partidosJugados;
    private int ganados;
    private int empatados;
    private int perdidos;

    private int golesFavor;
    private int golesContra;

    private int puntos;

    // Constructor
    public Equipo(String nombre) {
        this.nombre = nombre;

        partidosJugados = 0;
        ganados = 0;
        empatados = 0;
        perdidos = 0;

        golesFavor = 0;
        golesContra = 0;

        puntos = 0;
    }

    // Devuelve el nombre del equipo
    public String getNombre() {
        return nombre;
    }

    // Devuelve la diferencia de goles
    public int getDiferenciaGoles() {
        return golesFavor - golesContra;
    }


    public int getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(int cantidad) {
        partidosJugados = cantidad;
    }

    public int getGanados() {
        return ganados;
    }

    public void setGanados(int cantidad) {
        ganados = cantidad;
    }

    public int getEmpatados() {
        return empatados;
    }

    public void setEmpatados(int cantidad) {
        empatados = cantidad;
    }

    public int getPerdidos() {
        return perdidos;
    }

    public void setPerdidos(int cantidad) {
        perdidos = cantidad;
    }

    public int getGolesFavor() {
        return golesFavor;
    }

    public void setGolesFavor(int cantidad) {
        golesFavor = cantidad;
    }

    public int getGolesContra() {
        return golesContra;
    }

    public void setGolesContra(int cantidad) {
        golesContra = cantidad;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int cantidad) {
        puntos = cantidad;
    }
}