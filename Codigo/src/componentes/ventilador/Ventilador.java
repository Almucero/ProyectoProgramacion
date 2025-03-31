package componentes.ventilador;

public class Ventilador {
    private int CodVent;
    private float Precio;
    private String Modelo;
    private float Consumo;
    private float Velocidad;
    private int Stock;
    private int CodFab;

    public int getCodVent() {
        return CodVent;
    }
    public void setCodVent(int codVent) {
        CodVent = codVent;
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
    public float getVelocidad() {
        return Velocidad;
    }
    public void setVelocidad(float velocidad) {
        Velocidad = velocidad;
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

    public Ventilador(int CodVent, float Precio, String Modelo, float Consumo, float Velocidad, int Stock, int CodFab) {
        this.CodVent=CodVent;
        this.Precio=Precio;
        this.Modelo=Modelo;
        this.Consumo=Consumo;
        this.Velocidad=Velocidad;
        this.Stock=Stock;
        this.CodFab=CodFab;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Precio: %.2f, Modelo: %s, Consumo: %.2f, Velocidad: %.2f, Stock: %d, CodFab: %d",
            this.CodVent, this.Precio, this.Modelo, this.Consumo, this.Velocidad, this.Stock, this.CodFab);
    }
}
