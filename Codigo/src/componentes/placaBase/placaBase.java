package componentes.placaBase;

public class PlacaBase {
    private int CodPB;
    private float Precio;
    private int maxRam;
    private int maxCpu;
    private int maxGpu;
    private String tipoRam;
    private String FF;
    private int Stock;
    private String Chipset;
    private String Socket;
    private String Modelo;
    private int CodFab;

    public int getCodPB() {
        return CodPB;
    }
    public void setCodPB(int codPB) {
        CodPB = codPB;
    }
    public float getPrecio() {
        return Precio;
    }
    public void setPrecio(float precio) {
        Precio = precio;
    }
    public int getMaxRam() {
        return maxRam;
    }
    public void setMaxRam(int maxRam) {
        this.maxRam = maxRam;
    }
    public int getMaxCpu() {
        return maxCpu;
    }
    public void setMaxCpu(int maxCpu) {
        this.maxCpu = maxCpu;
    }
    public int getMaxGpu() {
        return maxGpu;
    }
    public void setMaxGpu(int maxGpu) {
        this.maxGpu = maxGpu;
    }
    public String getTipoRam() {
        return tipoRam;
    }
    public void setTipoRam(String tipoRam) {
        this.tipoRam = tipoRam;
    }
    public String getFF() {
        return FF;
    }
    public void setFF(String fF) {
        FF = fF;
    }
    public int getStock() {
        return Stock;
    }
    public void setStock(int stock) {
        Stock = stock;
    }
    public String getChipset() {
        return Chipset;
    }
    public void setChipset(String chipset) {
        Chipset = chipset;
    }
    public String getSocket() {
        return Socket;
    }
    public void setSocket(String socket) {
        Socket = socket;
    }
    public String getModelo() {
        return Modelo;
    }
    public void setModelo(String modelo) {
        Modelo = modelo;
    }
    public int getCodFab() {
        return CodFab;
    }
    public void setCodFab(int codFab) {
        CodFab = codFab;
    }

    public PlacaBase(int CodPB, float Precio, int maxRam, int maxCpu, int maxGpu, String tipoRam, String FF, int Stock, String Chipset, String Socket, String Modelo, int CodFab) {
        this.CodPB=CodPB;
        this.Precio=Precio;
        this.maxRam=maxRam;
        this.maxCpu=maxCpu;
        this.maxGpu=maxGpu;
        this.tipoRam=tipoRam;
        this.FF=FF;
        this.Stock=Stock;
        this.Chipset=Chipset;
        this.Socket=Socket;
        this.Modelo=Modelo;
        this.CodFab=CodFab;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %d, Modelo: %s, Precio: %.2f, maxRam: %d, maxCpu: %d, maxGpu: %d, tipoRam: %s, FF: %s, Stock: %d, Chipset: %s, Socket: %s, CodFab: %d",
            this.CodPB, this.Modelo, this.Precio, this.maxRam, this.maxCpu, this.maxGpu, this.tipoRam, this.FF, this.Stock, this.Chipset, this.Socket, this.CodFab);
    }
}
