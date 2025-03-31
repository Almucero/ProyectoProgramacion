package componentes.gpu.refrigeracionGpu.refrigeracionGpu_liquida;

public class RefrigeracionGpu_liquida {
    private float PotBomb;
    private float Consumo;
    private int CodRefGpu;

    public float getPotBomb() {
        return PotBomb;
    }
    public void setPotBomb(float potBomb) {
        PotBomb = potBomb;
    }
    public float getConsumo() {
        return Consumo;
    }
    public void setConsumo(float consumo) {
        Consumo = consumo;
    }
    public int getCodRefGpu() {
        return CodRefGpu;
    }
    public void setCodRefGpu(int codRefGpu) {
        CodRefGpu = codRefGpu;
    }

    public RefrigeracionGpu_liquida(float PotBomb, float Consumo, int CodRefGpu) {
        this.PotBomb=PotBomb;
        this.Consumo=Consumo;
        this.CodRefGpu=CodRefGpu;
    }

    @Override
    public String toString() {
        return String.format("ID refrigeraciónGPU: %d, Potencia bomba: %.2f, Consumo: %.2f",
            this.CodRefGpu, this.PotBomb, this.Consumo);
    }
}
