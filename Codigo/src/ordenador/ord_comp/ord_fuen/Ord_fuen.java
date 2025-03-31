package ordenador.ord_comp.ord_fuen;

public class Ord_fuen {
    private int CodOrd;
    private int CodFuen;
    private int Cantidad;

    public int getCodOrd() {
        return CodOrd;
    }
    public void setCodOrd(int codOrd) {
        CodOrd = codOrd;
    }
    public int getCodFuen() {
        return CodFuen;
    }
    public void setCodFuen(int codFuen) {
        CodFuen = codFuen;
    }
    public int getCantidad() {
        return Cantidad;
    }
    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public Ord_fuen(int CodOrd, int CodFuen, int Cantidad) {
        this.CodOrd=CodOrd;
        this.CodFuen=CodFuen;
        this.Cantidad=Cantidad;
    }
}
