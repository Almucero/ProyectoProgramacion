package componentes.cpu.refrigeracionCpu.refrigeracionCpu_liquida;

public class RefrigeracionGpu_liquida {
    private float PotBomb;
    private int CodRefCpu;

    public float getPotBomb() {
        return PotBomb;
    }
    public void setPotBomb(float potBomb) {
        PotBomb = potBomb;
    }
    public int getCodRefCpu() {
        return CodRefCpu;
    }
    public void setCodRefCpu(int codRefCpu) {
        CodRefCpu = codRefCpu;
    }

    public RefrigeracionGpu_liquida(float PotBomb, int CodRefCpu) {
        this.PotBomb=PotBomb;
        this.CodRefCpu=CodRefCpu;
    }

    @Override
    public String toString() {
        return String.format("ID refrigeraciónCPU: %d, Potencia bomba: %s",
            this.CodRefCpu, this.PotBomb);
    }
}
