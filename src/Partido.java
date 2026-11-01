
public class Partido {

    private Equipo local;
    private Equipo visitante;

    private int golesLocal;
    private int golesVisitante;

    // Constructor
    public Partido(Equipo local, Equipo visitante) {
        this.local = local;
        this.visitante = visitante;

        // -1 significa que todavía no se jugó
        golesLocal = -1;
        golesVisitante = -1;
    }

    public Equipo getLocal() {
        return local;
    }

    public Equipo getVisitante() {
        return visitante;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesLocal(int goles) {
        golesLocal = goles;
    }

    public void setGolesVisitante(int goles) {
        golesVisitante = goles;
    }
}