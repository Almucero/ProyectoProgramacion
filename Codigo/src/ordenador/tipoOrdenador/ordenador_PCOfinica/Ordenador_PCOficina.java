package ordenador.tipoOrdenador.ordenador_PCOfinica;

public class Ordenador_PCOficina {
    private String MainSoft;
    private int CodOrd;

    public String getMainSoft() {
        return MainSoft;
    }
    public void setMainSoft(String mainSoft) {
        MainSoft = mainSoft;
    }
    public int getCodOrd() {
        return CodOrd;
    }
    public void setCodOrd(int codOrd) {
        CodOrd = codOrd;
    }

    public Ordenador_PCOficina(String MainSoft, int CodOrd) {
        this.MainSoft=MainSoft;
        this.CodOrd=CodOrd;
    }
}
