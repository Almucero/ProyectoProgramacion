package componentes.cpu.refrigeracionCpu.refrigeracionCpu_aire;

public class RefrigeracionCpu_aire {
    private float Velocidad;
    private int CodRefCpu;

    public float getVelocidad() {
        return Velocidad;
    }
    public void setVelocidad(float velocidad) {
        Velocidad = velocidad;
    }
    public int getCodRefCpu() {
        return CodRefCpu;
    }
    public void setCodRefCpu(int codRefCpu) {
        CodRefCpu = codRefCpu;
    }

    public RefrigeracionCpu_aire(float Velocidad, int CodRefCpu) {
        this.Velocidad=Velocidad;
        this.CodRefCpu=CodRefCpu;
    }

    @Override
    public String toString() {
        return String.format("ID refrigeraciónCPU: %d, Velocidad: %.2f",
            this.CodRefCpu, this.Velocidad);
    }
}
