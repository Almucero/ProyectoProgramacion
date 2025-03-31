package componentes.gpu.refrigeracionGpu;

public class RefrigeracionGpu {
    private float Precio;
    private int CodRefGpu;
    private String Modelo;
    private String Tipo;
    private int Stock;
    private int CodFab;

    public float getPrecio() {
        return Precio;
    }
    public void setPrecio(float precio) {
        Precio = precio;
    }
    public int getCodRefGpu() {
        return CodRefGpu;
    }
    public void setCodRefGpu(int codRefGpu) {
        CodRefGpu = codRefGpu;
    }
    public String getModelo() {
        return Modelo;
    }
    public void setModelo(String modelo) {
        Modelo = modelo;
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

    public RefrigeracionGpu(float Precio, int CodRefGpu, String Modelo, String Tipo, int Stock, int CodFab) {
        this.Precio=Precio;
        this.CodRefGpu=CodRefGpu;
        this.Modelo=Modelo;
        this.Tipo=Tipo;
        this.Stock=Stock;
        this.CodFab=CodFab;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Precio: %.2f, Modelo: %s, Tipo: %s, Stock: %d, CodFab:%d",
            this.CodRefGpu, this.Precio, this.Modelo, this.Tipo, this.Stock, this.CodFab);
    }
}
