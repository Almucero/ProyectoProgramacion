package componentes.chasis;

public class Chasis {
    private int CodCha;
    private float Precio;
    private String Modelo;
    private String Color;
    private int Stock;
    private String Tamanio;
    private int CodFab;

    public int getCodCha() {
        return CodCha;
    }
    public void setCodCha(int codCha) {
        CodCha = codCha;
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
    public String getColor() {
        return Color;
    }
    public void setColor(String color) {
        Color = color;
    }
    public int getStock() {
        return Stock;
    }
    public void setStock(int stock) {
        Stock = stock;
    }
    public String getTamanio() {
        return Tamanio;
    }
    public void setTamanio(String tamanio) {
        Tamanio = tamanio;
    }
    public int getCodFab() {
        return CodFab;
    }
    public void setCodFab(int codFab) {
        CodFab = codFab;
    }

    public Chasis(int CodCha, float Precio, String Modelo, String Color, int Stock, String Tamanio, int CodFab) {
        this.CodCha=CodCha;
        this.Precio=Precio;
        this.Modelo=Modelo;
        this.Color=Color;
        this.Stock=Stock;
        this.Tamanio=Tamanio;
        this.CodFab=CodFab;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Precio: %.2f, Modelo: %s, Color: %s, Stock: %d, Tamaño: %s, CodFab: %d",
            this.CodCha, this.Precio, this.Modelo, this.Color, this.Stock, this.Tamanio, this.CodFab);
    }
}
