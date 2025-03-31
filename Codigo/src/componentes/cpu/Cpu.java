package componentes.cpu;

public class Cpu {
    private int CodCpu;
    private float Precio;
    private String Modelo;
    private float Consumo;
    private int Stock;
    private int Nucleos;
    private String Socket;
    private float Frecuencia;
    private int CodFab;

    public int getCodCpu() {
        return CodCpu;
    }
    public void setCodCpu(int codCpu) {
        CodCpu = codCpu;
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
    public int getStock() {
        return Stock;
    }
    public void setStock(int stock) {
        Stock = stock;
    }
    public int getNucleos() {
        return Nucleos;
    }
    public void setNucleos(int nucleos) {
        Nucleos = nucleos;
    }
    public String getSocket() {
        return Socket;
    }
    public void setSocket(String socket) {
        Socket = socket;
    }
    public float getFrecuencia() {
        return Frecuencia;
    }
    public void setFrecuencia(float frecuencia) {
        Frecuencia = frecuencia;
    }
    public int getCodFab() {
        return CodFab;
    }
    public void setCodFab(int codFab) {
        CodFab = codFab;
    }

    public Cpu(int CodCpu, float Precio, String Modelo, float Consumo, int Stock, int Nucleos, String Socket, float Frecuencia, int CodFab) {
        this.CodCpu=CodCpu;
        this.Precio=Precio;
        this.Modelo=Modelo;
        this.Consumo=Consumo;
        this.Stock=Stock;
        this.Nucleos=Nucleos;
        this.Socket=Socket;
        this.Frecuencia=Frecuencia;
        this.CodFab=CodFab;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Precio: %.2f, Modelo: %s, Consumo: %.2f, Stock: %d, Nucleos: %d, Socket: %s, Frecuencia: %.2f, CodFab: %d",
            this.CodCpu, this.Precio, this.Modelo, this.Consumo, this.Stock, this.Nucleos, this.Socket, this.Frecuencia, this.CodFab);
    }
}
