package usuario;

import java.sql.Date;

public class Usuario {
    private int CodUsu;
    private String Nombre;
    private String Ape1Usu;
    private String Ape2Usu;
    private String DNI;
    private Date FecNac;
    private String DireccionCompleta;
    private String Correo;
    private String Contrasenia;
    private int EsAdministrador;

    public int getCodUsu() {
        return CodUsu;
    }
    public void setCodUsu(int codUsu) {
        CodUsu = codUsu;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public String getApe1Usu() {
        return Ape1Usu;
    }
    public void setApe1Usu(String ape1Usu) {
        Ape1Usu = ape1Usu;
    }
    public String getApe2Usu() {
        return Ape2Usu;
    }
    public void setApe2Usu(String ape2Usu) {
        Ape2Usu = ape2Usu;
    }
    public String getDNI() {
        return DNI;
    }
    public void setDNI(String dNI) {
        DNI = dNI;
    }
    public Date getFecNac() {
        return FecNac;
    }
    public void setFecNac(Date fecNac) {
        FecNac = fecNac;
    }
    public String getDireccionCompleta() {
        return DireccionCompleta;
    }
    public void setDireccionCompleta(String direccionCompleta) {
        DireccionCompleta = direccionCompleta;
    }
    public String getCorreo() {
        return Correo;
    }
    public void setCorreo(String correo) {
        Correo = correo;
    }
    public String getContrasenia() {
        return Contrasenia;
    }
    public void setContrasenia(String contrasenia) {
        Contrasenia = contrasenia;
    }
    public int getEsAdministrador() {
        return EsAdministrador;
    }
    public void setEsAdministrador(int esAdministrador) {
        EsAdministrador = esAdministrador;
    }

    public Usuario(int CodUsu, String Nombre, String Ape1Usu, String Ape2Usu, String DNI, Date FecNac, String DireccionCompleta, String Correo, String Contrasenia, int EsAdmnistrador) {
        this.CodUsu=CodUsu;
        this.Nombre=Nombre;
        this.Ape1Usu=Ape1Usu;
        this.Ape2Usu=Ape2Usu;
        this.DNI=DNI;
        this.FecNac=FecNac;
        this.DireccionCompleta=DireccionCompleta;
        this.Correo=Correo;
        this.Contrasenia=Contrasenia;
        this.EsAdministrador=EsAdmnistrador;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Nombre: %s, Primer apellido: %s, Segundo Apellido: %s, DNI: %s, Fecha nacimiento: %s, Dirección completa: %s, Correo: %s, Contraseña: %s, EsAdministrador: %d",
            this.CodUsu, this.Nombre, this.Ape1Usu, this.Ape2Usu, this.DNI, this.FecNac, this.DireccionCompleta, this.Correo, this.Contrasenia, this.EsAdministrador);
    }
}
