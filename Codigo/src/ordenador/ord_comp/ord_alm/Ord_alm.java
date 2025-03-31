package ordenador.ord_comp.ord_alm;

public class Ord_alm {
    private int CodOrd;
    private int CodAlmSecundario;
    private int Cantidad;

    public int getCodOrd() {
        return CodOrd;
    }
    public void setCodOrd(int codOrd) {
        CodOrd = codOrd;
    }
    public int getCodAlmSecundario() {
        return CodAlmSecundario;
    }
    public void setCodAlmSecundario(int codAlmSecundario) {
        CodAlmSecundario = codAlmSecundario;
    }
    public int getCantidad() {
        return Cantidad;
    }
    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public Ord_alm(int CodOrd, int CodAlmSecundario, int Cantidad) {
        this.CodOrd=CodOrd;
        this.CodAlmSecundario=CodAlmSecundario;
        this.Cantidad=Cantidad;
    }
}
