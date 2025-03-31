package servicioTesteo;

public class ServicioTesteo {
    private int CodSerTest;
    private String Nombre;
    private float Estrellas;

    public int getCodSerTest() {
        return CodSerTest;
    }
    public void setCodSerTest(int codSerTest) {
        CodSerTest = codSerTest;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public float getEstrellas() {
        return Estrellas;
    }
    public void setEstrellas(float estrellas) {
        Estrellas = estrellas;
    }

    public ServicioTesteo(int CodSerTest, String Nombre, float Estrellas) {
        this.CodSerTest=CodSerTest;
        this.Nombre=Nombre;
        this.Estrellas=Estrellas;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Nombre: %s, Estrellas: %.2f",
            this.CodSerTest, this.Nombre, this.Estrellas);
    }
}
