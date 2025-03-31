package ordenador;

public class Ordenador {
    private int CodOrd;
    private String Nombre;
    private float Precio;
    private String Proposito;
    private int Stock;
    private String SO;
    private int CodCha;
    private int CodPB;
    private int CodAlmPrincipal;

    public int getCodOrd() {
        return CodOrd;
    }
    public void setCodOrd(int codOrd) {
        CodOrd = codOrd;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public float getPrecio() {
        return Precio;
    }
    public void setPrecio(float precio) {
        Precio = precio;
    }
    public String getProposito() {
        return Proposito;
    }
    public void setProposito(String proposito) {
        Proposito = proposito;
    }
    public int getStock() {
        return Stock;
    }
    public void setStock(int stock) {
        Stock = stock;
    }
    public String getSO() {
        return SO;
    }
    public void setSO(String sO) {
        SO = sO;
    }
    public int getCodCha() {
        return CodCha;
    }
    public void setCodCha(int codCha) {
        CodCha = codCha;
    }
    public int getCodPB() {
        return CodPB;
    }
    public void setCodPB(int codPB) {
        CodPB = codPB;
    }
    public int getCodAlmPrincipal() {
        return CodAlmPrincipal;
    }
    public void setCodAlmPrincipal(int codAlmPrincipal) {
        CodAlmPrincipal = codAlmPrincipal;
    }

    public Ordenador(int CodOrd, String Nombre, float Precio, String Proposito, int Stock, String SO, int CodCha, int CodPB, int CodAlmPrincipal) {
        this.CodOrd=CodOrd;
        this.Nombre=Nombre;
        this.Precio=Precio;
        this.Proposito=Proposito;
        this.Stock=Stock;
        this.SO=SO;
        this.CodCha=CodCha;
        this.CodPB=CodPB;
        this.CodAlmPrincipal=CodAlmPrincipal;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Nombre: %s, Precio: %.2f, Proposito: %s, Stock: %d, SO: %s, CodCha: %d, CodPB: %d, CodAlmPrincipal: %d",
            this.CodOrd, this.Nombre, this.Precio, this.Proposito, this.Stock, this.SO, this.CodCha, this.CodPB, this.CodAlmPrincipal);
    }
}
