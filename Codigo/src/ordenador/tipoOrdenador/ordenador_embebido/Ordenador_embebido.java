package ordenador.tipoOrdenador.ordenador_embebido;

public class Ordenador_embebido {
    private int SisTiemReal;
    private int CodOrd;

    public int getSisTiemReal() {
        return SisTiemReal;
    }
    public void setSisTiemReal(int sisTiemReal) {
        SisTiemReal = sisTiemReal;
    }
    public int getCodOrd() {
        return CodOrd;
    }
    public void setCodOrd(int codOrd) {
        CodOrd = codOrd;
    }

    public Ordenador_embebido(int SisTiemReal, int CodOrd) {
        this.SisTiemReal=SisTiemReal;
        this.CodOrd=CodOrd;
    }
}
