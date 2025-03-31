package componentes.gpu;

public class Gpu {
    private int CodGpu;
    private float Precio;
    private String Modelo;
    private int Stock;
    private float VRAM;
    private float Frecuencia;
    private String TipoMem;
    private float Consumo;
    private int CodFab;

    public int getCodGpu() {
        return CodGpu;
    }
    public void setCodGpu(int codGpu) {
        CodGpu = codGpu;
    }
    public float getPrecio() {
        return Precio;
    }
    public void setPrecio(float precio) {
        Precio = precio;
    }
    public String getModelo() {
        return Modelo;
    }
    public void setModelo(String modelo) {
        Modelo = modelo;
    }
    public int getStock() {
        return Stock;
    }
    public void setStock(int stock) {
        Stock = stock;
    }
    public float getVRAM() {
        return VRAM;
    }
    public void setVRAM(float vRAM) {
        VRAM = vRAM;
    }
    public float getFrecuencia() {
        return Frecuencia;
    }
    public void setFrecuencia(float frecuencia) {
        Frecuencia = frecuencia;
    }
    public String getTipoMem() {
        return TipoMem;
    }
    public void setTipoMem(String tipoMem) {
        TipoMem = tipoMem;
    }
    public float getConsumo() {
        return Consumo;
    }
    public void setConsumo(float consumo) {
        Consumo = consumo;
    }
    public int getCodFab() {
        return CodFab;
    }
    public void setCodFab(int codFab) {
        CodFab = codFab;
    }

    public Gpu(int CodGpu, float Precio, String Modelo, int Stock, float VRAM, float Frecuencia, String TipoMem, float Consumo, int CodFab) {
        this.CodGpu=CodGpu;
        this.Precio=Precio;
        this.Modelo=Modelo;
        this.Stock=Stock;
        this.VRAM=VRAM;
        this.Frecuencia=Frecuencia;
        this.TipoMem=TipoMem;
        this.Consumo=Consumo;
        this.CodFab=CodFab;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Precio: %.2f, Modelo: %s, Stock: %d, VRAM: %.2f, Frecuencia: %.2f, Tipo memoria: %s, Consumo: %.2f, CodFab: %d",
            this.CodGpu, this.Precio, this.Modelo, this.Stock, this.VRAM, this.Frecuencia, this.TipoMem, this.Consumo, this.CodFab);
    }
}
