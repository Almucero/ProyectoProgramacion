package ordenador.montaje;

import java.sql.Date;

public class Montaje {
    private int CodMontaje;
    private Date Fecha;
    private String Detalles;
    private float Precio;
    private int CodOrd;
    private int CodMon;

    public int getCodMontaje() {
        return CodMontaje;
    }
    public void setCodMontaje(int codMontaje) {
        CodMontaje = codMontaje;
    }
    public Date getFecha() {
        return Fecha;
    }
    public void setFecha(Date fecha) {
        Fecha = fecha;
    }
    public String getDetalles() {
        return Detalles;
    }
    public void setDetalles(String detalles) {
        Detalles = detalles;
    }
    public float getPrecio() {
        return Precio;
    }
    public void setPrecio(float precio) {
        Precio = precio;
    }
    public int getCodOrd() {
        return CodOrd;
    }
    public void setCodOrd(int codOrd) {
        CodOrd = codOrd;
    }
    public int getCodMon() {
        return CodMon;
    }
    public void setCodMon(int codMon) {
        CodMon = codMon;
    }

    public Montaje(int CodMontaje, Date Fecha, String Detalles, float Precio, int CodOrd, int CodMon) {
        this.CodMontaje=CodMontaje;
        this.Fecha=Fecha;
        this.Detalles=Detalles;
        this.Precio=Precio;
        this.CodOrd=CodOrd;
        this.CodMon=CodMon;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Fecha: %s, Detalles: %s, Precio: %.2f, CodOrd: %d, CodMon: %d",
            this.CodMontaje, this.Fecha, this.Detalles, this.Precio, this.CodOrd, this.CodMon);
    }
}
