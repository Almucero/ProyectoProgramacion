package ordenador.ord_comp.ord_cpu;

public class Ord_cpu {
    private int CodOrd;
    private int CodCpu;
    private int CodRefCpu;
    private int Cantidad;

    public int getCodOrd() {
        return CodOrd;
    }
    public void setCodOrd(int codOrd) {
        CodOrd = codOrd;
    }
    public int getCodCpu() {
        return CodCpu;
    }
    public void setCodCpu(int codCpu) {
        CodCpu = codCpu;
    }
    public int getCodRefCpu() {
        return CodRefCpu;
    }
    public void setCodRefCpu(int codRefCpu) {
        CodRefCpu = codRefCpu;
    }
    public int getCantidad() {
        return Cantidad;
    }
    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public Ord_cpu(int CodOrd, int CodCpu, int CodRefCpu, int Cantidad) {
        this.CodOrd=CodOrd;
        this.CodCpu=CodCpu;
        this.CodRefCpu=CodRefCpu;
        this.Cantidad=Cantidad;
    }
}
