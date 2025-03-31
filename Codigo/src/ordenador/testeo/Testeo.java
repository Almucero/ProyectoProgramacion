package ordenador.testeo;

import java.sql.Date;

public class Testeo {
    private int CodTest;
    private float Precio;
    private Date Fecha;
    private String Reporte;
    private int CodOrd;
    private int CodSerTest;

    public int getCodTest() {
        return CodTest;
    }
    public void setCodTest(int codTest) {
        CodTest = codTest;
    }
    public float getPrecio() {
        return Precio;
    }
    public void setPrecio(float precio) {
        Precio = precio;
    }
    public Date getFecha() {
        return Fecha;
    }
    public void setFecha(Date fecha) {
        Fecha = fecha;
    }
    public String getReporte() {
        return Reporte;
    }
    public void setReporte(String reporte) {
        Reporte = reporte;
    }
    public int getCodOrd() {
        return CodOrd;
    }
    public void setCodOrd(int codOrd) {
        CodOrd = codOrd;
    }
    public int getCodSerTest() {
        return CodSerTest;
    }
    public void setCodSerTest(int codSerTest) {
        CodSerTest = codSerTest;
    }

    public Testeo(int CodTest, float Precio, Date Fecha, String Reporte, int CodOrd, int CodSerTest) {
        this.CodTest=CodTest;
        this.Precio=Precio;
        this.Fecha=Fecha;
        this.Reporte=Reporte;
        this.CodOrd=CodOrd;
        this.CodSerTest=CodSerTest;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Precio: %.2f, Fecha: %s, Reporte: %s, CodOrd: %d, CodSerTest: %d",
            this.CodTest, this.Precio, this.Fecha, this.Reporte, this.CodOrd, this.CodSerTest);
    }
}
