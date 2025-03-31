package fabricante;

public class Fabricante {
    private int CodFab;
    private String NomFab;

    public int getCodFab() {
        return CodFab;
    }
    public void setCodFab(int codFab) {
        CodFab = codFab;
    }
    public String getNomFab() {
        return NomFab;
    }
    public void setNomFab(String nomFab) {
        NomFab = nomFab;
    }

    public Fabricante(int CodFab, String NomFab) {
        this.CodFab=CodFab;
        this.NomFab=NomFab;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Nombre: %s", this.CodFab, this.NomFab);
    }
}
