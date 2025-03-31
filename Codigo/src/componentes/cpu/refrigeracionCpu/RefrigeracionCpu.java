package componentes.cpu.refrigeracionCpu;

public class RefrigeracionCpu {
    private int CodRefCpu;
    private float Precio;
    private String Modelo;
    private float Consumo;
    private String Tipo;
    private int Stock;
    private int CodFab;

    public int getCodRefCpu() {
        return CodRefCpu;
    }
    public void setCodRefCpu(int codRefCpu) {
        CodRefCpu = codRefCpu;
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
    public float getConsumo() {
        return Consumo;
    }
    public void setConsumo(float consumo) {
        Consumo = consumo;
    }
    public String getTipo() {
        return Tipo;
    }
    public void setTipo(String tipo) {
        Tipo = tipo;
    }
    public int getStock() {
        return Stock;
    }
    public void setStock(int stock) {
        Stock = stock;
    }
    public int getCodFab() {
        return CodFab;
    }
    public void setCodFab(int codFab) {
        CodFab = codFab;
    }

    public RefrigeracionCpu(int CodRefCpu, float Precio, String Modelo, float Consumo, String Tipo, int Stock, int CodFab) {
        this.CodRefCpu=CodRefCpu;
        this.Precio=Precio;
        this.Modelo=Modelo;
        this.Consumo=Consumo;
        this.Tipo=Tipo;
        this.Stock=Stock;
        this.CodFab=CodFab;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Precio: %.2f, Modelo: %s, Consumo: %.2f, Tipo: %s, Stock: %d, CodFab: %d",
            this.CodRefCpu, this.Precio, this.Modelo, this.Consumo, this.Tipo, this.Stock, this.CodFab);
    }
}
