package carrito.contenido_carrito;

public class Contenido_carrito {
    private int CodConCar;
    private float Precio;
    private int Cantidad;
    private int CodCar;
    private int CodOrd;
    private int CodCha;
    private int CodFuen;
    private int CodPB;
    private int CodAlm;
    private int CodRam;
    private int CodGpu;
    private int CodCpu;
    private int CodVent;
    private int CodRefCpu;
    private int CodRefGpu;

    public int getCodConCar() {
        return CodConCar;
    }
    public void setCodConCar(int codConCar) {
        CodConCar = codConCar;
    }
    public float getPrecio() {
        return Precio;
    }
    public void setPrecio(float precio) {
        Precio = precio;
    }
    public int getCantidad() {
        return Cantidad;
    }
    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }
    public int getCodCar() {
        return CodCar;
    }
    public void setCodCar(int codCar) {
        CodCar = codCar;
    }
    public int getCodOrd() {
        return CodOrd;
    }
    public void setCodOrd(int codOrd) {
        CodOrd = codOrd;
    }
    public int getCodCha() {
        return CodCha;
    }
    public void setCodCha(int codCha) {
        CodCha = codCha;
    }
    public int getCodFuen() {
        return CodFuen;
    }
    public void setCodFuen(int codFuen) {
        CodFuen = codFuen;
    }
    public int getCodPB() {
        return CodPB;
    }
    public void setCodPB(int codPB) {
        CodPB = codPB;
    }
    public int getCodAlm() {
        return CodAlm;
    }
    public void setCodAlm(int codAlm) {
        CodAlm = codAlm;
    }
    public int getCodRam() {
        return CodRam;
    }
    public void setCodRam(int codRam) {
        CodRam = codRam;
    }
    public int getCodGpu() {
        return CodGpu;
    }
    public void setCodGpu(int codGpu) {
        CodGpu = codGpu;
    }
    public int getCodCpu() {
        return CodCpu;
    }
    public void setCodCpu(int codCpu) {
        CodCpu = codCpu;
    }
    public int getCodVent() {
        return CodVent;
    }
    public void setCodVent(int codVent) {
        CodVent = codVent;
    }
    public int getCodRefCpu() {
        return CodRefCpu;
    }
    public void setCodRefCpu(int codRefCpu) {
        CodRefCpu = codRefCpu;
    }
    public int getCodRefGpu() {
        return CodRefGpu;
    }
    public void setCodRefGpu(int codRefGpu) {
        CodRefGpu = codRefGpu;
    }

    public Contenido_carrito(int CodConCar, float Precio, int Cantidad, int CodCar, int CodOrd, int CodCha, int CodFuen, int CodPB, int CodAlm, int CodRam, int CodGpu, int CodCpu, int CodVent, int CodRefCpu, int CodRefGpu) {
        this.CodConCar=CodConCar;
        this.Precio=Precio;
        this.Cantidad=Cantidad;
        this.CodCar=CodCar;
        this.CodOrd=CodOrd;
        this.CodCha=CodCha;
        this.CodFuen=CodFuen;
        this.CodPB=CodPB;
        this.CodAlm=CodAlm;
        this.CodRam=CodRam;
        this.CodGpu=CodGpu;
        this.CodCpu=CodCpu;
        this.CodVent=CodVent;
        this.CodRefCpu=CodRefCpu;
        this.CodRefGpu=CodRefGpu;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Precio: %.2f, Cantidad: %d, CodCar: %d, CodOrd: %d, CodCha: %d, CodFuen: %d, CodPB: %d, CodAlm: %d, CodRam: %d, CodGpu: %d, CodCpu: %d, CodVent: %d, CodRefCpu: %d, CodRefGpu: %d",
            this.CodConCar, this.Precio, this.Cantidad, this.CodCar, this.CodOrd, this.CodCha, this.CodFuen, this.CodPB, this.CodAlm, this.CodRam, this.CodGpu, this.CodCpu, this.CodVent, this.CodRefCpu, this.CodRefGpu);
    }
}
