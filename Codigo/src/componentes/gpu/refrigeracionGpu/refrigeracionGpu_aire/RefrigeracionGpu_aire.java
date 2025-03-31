package componentes.gpu.refrigeracionGpu.refrigeracionGpu_aire;

public class RefrigeracionGpu_aire {
    private float Velocidad;
    private int CodRefGpu;

    public float getVelocidad() {
        return Velocidad;
    }
    public void setVelocidad(float velocidad) {
        Velocidad = velocidad;
    }
    public int getCodRefGpu() {
        return CodRefGpu;
    }
    public void setCodRefGpu(int codRefGpu) {
        CodRefGpu = codRefGpu;
    }

    public RefrigeracionGpu_aire(float Velocidad, int CodRefGpu) {
        this.Velocidad=Velocidad;
        this.CodRefGpu=CodRefGpu;
    }

    @Override
    public String toString() {
        return String.format("ID refrigeraciónGPU: %d, Velocidad: %.2f",
            this.CodRefGpu, this.Velocidad);
    }
}
