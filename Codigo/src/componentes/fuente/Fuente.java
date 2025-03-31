package componentes.fuente;

public class Fuente {
    private int CodFuen;
    private float Precio;
    private String Modelo;
    private int Stock;
    private float Potencia;
    private float Eficiencia;
    private int CodFab;

    public int getCodFuen() {
        return CodFuen;
    }
    public void setCodFuen(int codFuen) {
        CodFuen = codFuen;
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
    public float getPotencia() {
        return Potencia;
    }
    public void setPotencia(float potencia) {
        Potencia = potencia;
    }
    public float getEficiencia() {
        return Eficiencia;
    }
    public void setEficiencia(float eficiencia) {
        Eficiencia = eficiencia;
    }
    public int getCodFab() {
        return CodFab;
    }
    public void setCodFab(int codFab) {
        CodFab = codFab;
    }

    public Fuente(int CodFuen, float Precio, String Modelo, int Stock, float Potencia, float Eficiencia, int CodFab) {
        this.CodFuen=CodFuen;
        this.Precio=Precio;
        this.Modelo=Modelo;
        this.Stock=Stock;
        this.Potencia=Potencia;
        this.Eficiencia=Eficiencia;
        this.CodFab=CodFab;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Precio: %.2f, Modelo: %s, Stock: %d, Potencia: %.2f, Eficiencia: %.2f, CodFab: %d",
            this.CodFuen, this.Precio, this.Modelo, this.Stock, this.Potencia, this.Eficiencia, this.CodFab);
    }
}
