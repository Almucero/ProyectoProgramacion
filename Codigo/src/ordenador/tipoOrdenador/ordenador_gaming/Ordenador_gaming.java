package ordenador.tipoOrdenador.ordenador_gaming;

public class Ordenador_gaming {
    private int RGB;
    private int OC;
    private int CodOrd;

    public int getRGB() {
        return RGB;
    }
    public void setRGB(int rGB) {
        RGB = rGB;
    }
    public int getOC() {
        return OC;
    }
    public void setOC(int oC) {
        OC = oC;
    }
    public int getCodOrd() {
        return CodOrd;
    }
    public void setCodOrd(int codOrd) {
        CodOrd = codOrd;
    }

    public Ordenador_gaming(int RGB, int OC, int CodOrd) {
        this.RGB=RGB;
        this.OC=OC;
        this.CodOrd=CodOrd;
    }
}
