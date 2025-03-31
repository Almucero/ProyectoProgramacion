package ordenador.tipoOrdenador.ordenador_servidor;

public class Ordenador_servidor {
    private String escalabilidad;
    private int CodOrd;

    public String getEscalabilidad() {
        return escalabilidad;
    }
    public void setEscalabilidad(String escalabilidad) {
        this.escalabilidad = escalabilidad;
    }
    public int getCodOrd() {
        return CodOrd;
    }
    public void setCodOrd(int codOrd) {
        CodOrd = codOrd;
    }

    public Ordenador_servidor(String escalabilidad, int CodOrd) {
        this.escalabilidad=escalabilidad;
        this.CodOrd=CodOrd;
    }
}
