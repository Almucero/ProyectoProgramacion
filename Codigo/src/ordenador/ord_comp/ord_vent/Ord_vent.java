package ordenador.ord_comp.ord_vent;

public class Ord_vent {
    private int CodOrd;
    private int CodVent;
    private int Cantidad;

    public int getCodOrd() {
        return CodOrd;
    }
    public void setCodOrd(int codOrd) {
        CodOrd = codOrd;
    }
    public int getCodVent() {
        return CodVent;
    }
    public void setCodVent(int codVent) {
        CodVent = codVent;
    }
    public int getCantidad() {
        return Cantidad;
    }
    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public Ord_vent(int CodOrd, int CodVent, int Cantidad) {
        this.CodOrd=CodOrd;
        this.CodVent=CodVent;
        this.Cantidad=Cantidad;
    }
}
