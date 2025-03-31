package ordenador.ord_comp.ord_gpu;

public class Ord_gpu {
    private int CodOrd;
    private int CodGpu;
    private int CodRefGpu;
    private int Cantidad;

    public int getCodOrd() {
        return CodOrd;
    }
    public void setCodOrd(int codOrd) {
        CodOrd = codOrd;
    }
    public int getCodGpu() {
        return CodGpu;
    }
    public void setCodGpu(int codGpu) {
        CodGpu = codGpu;
    }
    public int getCodRefGpu() {
        return CodRefGpu;
    }
    public void setCodRefGpu(int codRefGpu) {
        CodRefGpu = codRefGpu;
    }
    public int getCantidad() {
        return Cantidad;
    }
    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public Ord_gpu(int CodOrd, int CodGpu, int CodRefGpu, int Cantidad) {
        this.CodOrd=CodOrd;
        this.CodGpu=CodGpu;
        this.CodRefGpu=CodRefGpu;
        this.Cantidad=Cantidad;
    }
}
