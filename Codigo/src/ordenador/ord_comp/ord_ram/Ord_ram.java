package ordenador.ord_comp.ord_ram;

public class Ord_ram {
    private int CodOrd;
    private int CodRam;
    private int Cantidad;

    public int getCodOrd() {
        return CodOrd;
    }
    public void setCodOrd(int codOrd) {
        CodOrd = codOrd;
    }
    public int getCodRam() {
        return CodRam;
    }
    public void setCodRam(int codRam) {
        CodRam = codRam;
    }
    public int getCantidad() {
        return Cantidad;
    }
    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public Ord_ram(int CodOrd, int CodRam, int Cantidad) {
        this.CodOrd=CodOrd;
        this.CodRam=CodRam;
        this.Cantidad=Cantidad;
    }
}
