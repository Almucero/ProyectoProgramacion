package montador;

import java.sql.Date;

public class Montador {
    private int CodMon;
    private String Nombre;
    private String Apellidos;
    private char DNI;
    private Date FechaIni;
    private String Titulacion;

    public int getCodMon() {
        return CodMon;
    }
    public void setCodMon(int codMon) {
        CodMon = codMon;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public String getApellidos() {
        return Apellidos;
    }
    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }
    public char getDNI() {
        return DNI;
    }
    public void setDNI(char dNI) {
        DNI = dNI;
    }
    public Date getFechaIni() {
        return FechaIni;
    }
    public void setFechaIni(Date fechaIni) {
        FechaIni = fechaIni;
    }
    public String getTitulacion() {
        return Titulacion;
    }
    public void setTitulacion(String titulacion) {
        Titulacion = titulacion;
    }

    public Montador(int CodMon, String Nombre, String Apellidos, char DNI, Date FechaIni, String Titulacion) {
        this.CodMon=CodMon;
        this.Nombre=Nombre;
        this.Apellidos=Apellidos;
        this.DNI=DNI;
        this.FechaIni=FechaIni;
        this.Titulacion=Titulacion;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Nombre: %s, Apellidos: %s, DNI: %s, Fecha inicio: %s, Titulación: %s",
            this.CodMon, this.Nombre, this.Apellidos, this.DNI, this.FechaIni, this.Titulacion);
    }
}
