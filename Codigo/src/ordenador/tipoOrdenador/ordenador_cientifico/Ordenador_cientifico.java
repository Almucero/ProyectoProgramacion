package ordenador.tipoOrdenador.ordenador_cientifico;

public class Ordenador_cientifico {
    private int multiCpu;
    private int CodOrd;

    public int getMultiCpu() {
        return multiCpu;
    }
    public void setMultiCpu(int multiCpu) {
        this.multiCpu = multiCpu;
    }
    public int getCodOrd() {
        return CodOrd;
    }
    public void setCodOrd(int codOrd) {
        CodOrd = codOrd;
    }

    public Ordenador_cientifico(int multiCpu, int CodOrd) {
        this.multiCpu=multiCpu;
        this.CodOrd=CodOrd;
    }
}
