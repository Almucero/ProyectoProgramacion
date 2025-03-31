package carrito;

import java.sql.Date;

public class Carrito {
    private int CodCar;
    private Date Fecha;
    private float PrecioTotal;
    private String Estado;
    private int CodUsu;

    public int getCodCar() {
        return CodCar;
    }
    public void setCodCar(int codCar) {
        CodCar = codCar;
    }
    public Date getFecha() {
        return Fecha;
    }
    public void setFecha(Date fecha) {
        Fecha = fecha;
    }
    public float getPrecioTotal() {
        return PrecioTotal;
    }
    public void setPrecioTotal(float precioTotal) {
        PrecioTotal = precioTotal;
    }
    public String getEstado() {
        return Estado;
    }
    public void setEstado(String estado) {
        Estado = estado;
    }
    public int getCodUsu() {
        return CodUsu;
    }
    public void setCodUsu(int codUsu) {
        CodUsu = codUsu;
    }

    public Carrito(int CodCar, Date Fecha, float PrecioTotal, String Estado, int CodUsu) {
        this.CodCar=CodCar;
        this.Fecha=Fecha;
        this.PrecioTotal=PrecioTotal;
        this.Estado=Estado;
        this.CodUsu=CodUsu;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Fecha: %s, Precio total: %.2f, Estado: %s, CodUsu: %d",
            this.CodCar, this.Fecha, this.PrecioTotal, this.Estado, this.CodUsu);
    }
}
