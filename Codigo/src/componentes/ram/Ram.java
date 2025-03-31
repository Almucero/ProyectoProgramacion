package componentes.ram;

public class Ram {
    private int CodRam;
    private float Precio;
    private String Modelo;
    private float Frecuencia;
    private String Tipo;
    private float Consumo;
    private int Stock;
    private int CodFab;

    public int getCodRam() {
        return CodRam;
    }
    public void setCodRam(int codRam) {
        CodRam = codRam;
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
    public float getFrecuencia() {
        return Frecuencia;
    }
    public void setFrecuencia(float frecuencia) {
        Frecuencia = frecuencia;
    }
    public String getTipo() {
        return Tipo;
    }
    public void setTipo(String tipo) {
        Tipo = tipo;
    }
    public float getConsumo() {
        return Consumo;
    }
    public void setConsumo(float consumo) {
        Consumo = consumo;
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

    public Ram(int CodRam, float Precio, String Modelo, float Frecuencia, String Tipo, float Consumo, int Stock, int CodFab) {
        this.CodRam=CodRam;
        this.Precio=Precio;
        this.Modelo=Modelo;
        this.Frecuencia=Frecuencia;
        this.Tipo=Tipo;
        this.Consumo=Consumo;
        this.Stock=Stock;
        this.CodFab=CodFab;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Precio: %.2f, Modelo: %s, Frecuencia: %.2f, Tipo: %s, Consumo: %.2f, Stock: %d, CodFab: %d",
            this.CodRam, this.Precio, this.Modelo, this.Frecuencia, this.Tipo, this.Consumo, this.Stock, this.CodFab);
    }
}
