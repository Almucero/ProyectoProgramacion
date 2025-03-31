package ordenador.tipoOrdenador.ordenador_workstation;

public class Ordenador_workstation {
    private int render;
    private String certificado;
    private int CodOrd;

    public int getRender() {
        return render;
    }
    public void setRender(int render) {
        this.render = render;
    }
    public String getCertificado() {
        return certificado;
    }
    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }
    public int getCodOrd() {
        return CodOrd;
    }
    public void setCodOrd(int codOrd) {
        CodOrd = codOrd;
    }

    public Ordenador_workstation(int render, String certificado, int CodOrd) {
        this.render=render;
        this.certificado=certificado;
        this.CodOrd=CodOrd;
    } 
}
