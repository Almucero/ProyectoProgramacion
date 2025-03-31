package componentes.almacenamiento;

public class Almacenamiento {
    private int CodAlm;
    private float Precio;
    private String Modelo;
    private float Capacidad;
    private float Consumo;
    private int Stock;
    private int CodFab;

    public int getCodAlm() {
        return CodAlm;
    }
    public void setCodAlm(int codAlm) {
        CodAlm = codAlm;
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
    public float getCapacidad() {
        return Capacidad;
    }
    public void setCapacidad(float capacidad) {
        Capacidad = capacidad;
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

    public Almacenamiento(int CodAlm, float Precio, String Modelo, float Capacidad, float Consumo, int Stock, int CodFab) {
        this.CodAlm=CodAlm;
        this.Precio=Precio;
        this.Modelo=Modelo;
        this.Capacidad=Capacidad;
        this.Consumo=Consumo;
        this.Stock=Stock;
        this.CodFab=CodFab;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Precio: %.2f, Modelo: %s, Capacidad: %.2f, Consumo: %.2f, Stock: %d, CodFab: %d",
            this.CodAlm, this.Precio, this.Modelo, this.Capacidad, this.Consumo, this.Stock, this.CodFab);
    }
}
