package carrito.contenido_carrito;

import java.sql.*;
import java.util.ArrayList;

public class Contenido_carritoService {
    Connection conn;

    public Contenido_carritoService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Contenido_carrito> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Contenido_carrito> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodConCar, Precio, Cantidad, CodCar, CodOrd, CodCha, CodFuen, CodPB, CodAlm, CodRam, CodGpu, CodCpu, CodVent, CodRefCpu, CodRefGpu FROM contenido_carrito";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodConCar = querySet.getInt("CodConCar");
            float Precio = querySet.getFloat("Precio");
            int Cantidad = querySet.getInt("Cantidad");
            int CodCar = querySet.getInt("CodCar");
            int CodOrd = querySet.getInt("CodOrd");
            int CodCha = querySet.getInt("CodCha");
            int CodFuen = querySet.getInt("CodFuen");
            int CodPB = querySet.getInt("CodPB");
            int CodAlm = querySet.getInt("CodAlm");
            int CodRam = querySet.getInt("CodRam");
            int CodGpu = querySet.getInt("CodGpu");
            int CodCpu = querySet.getInt("CodCpu");
            int CodVent = querySet.getInt("CodVent");
            int CodRefCpu = querySet.getInt("CodRefCpu");
            int CodRefGpu = querySet.getInt("CodRefGpu");
            result.add(new Contenido_carrito(CodConCar, Precio, Cantidad, CodCar, CodOrd, CodCha, CodFuen, CodPB, CodAlm, CodRam, CodGpu, CodCpu, CodVent, CodRefCpu, CodRefGpu));
        }
        statement.close();
        return result;
    }

    public Contenido_carrito requestById(long id) throws SQLException {
        Statement statement = null;
        Contenido_carrito result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodConCar, Precio, Cantidad, CodCar, CodOrd, CodCha, CodFuen, CodPB, CodAlm, CodRam, CodGpu, CodCpu, CodVent, CodRefCpu, CodRefGpu FROM contenido_carrito WHERE CodConCar=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodConCar = querySet.getInt("CodConCar");
            float Precio = querySet.getFloat("Precio");
            int Cantidad = querySet.getInt("Cantidad");
            int CodCar = querySet.getInt("CodCar");
            int CodOrd = querySet.getInt("CodOrd");
            int CodCha = querySet.getInt("CodCha");
            int CodFuen = querySet.getInt("CodFuen");
            int CodPB = querySet.getInt("CodPB");
            int CodAlm = querySet.getInt("CodAlm");
            int CodRam = querySet.getInt("CodRam");
            int CodGpu = querySet.getInt("CodGpu");
            int CodCpu = querySet.getInt("CodCpu");
            int CodVent = querySet.getInt("CodVent");
            int CodRefCpu = querySet.getInt("CodRefCpu");
            int CodRefGpu = querySet.getInt("CodRefGpu");
            result = new Contenido_carrito(CodConCar, Precio, Cantidad, CodCar, CodOrd, CodCha, CodFuen, CodPB, CodAlm, CodRam, CodGpu, CodCpu, CodVent, CodRefCpu, CodRefGpu);
        }
        statement.close();
        return result;
    }

    public long create(Contenido_carrito contenidoCarrito) throws SQLException {
        float Precio = contenidoCarrito.getPrecio();
        int Cantidad = contenidoCarrito.getCantidad();
        int CodCar = contenidoCarrito.getCodCar();
        int CodOrd = contenidoCarrito.getCodOrd();
        int CodCha = contenidoCarrito.getCodCha();
        int CodFuen = contenidoCarrito.getCodFuen();
        int CodPB = contenidoCarrito.getCodPB();
        int CodAlm = contenidoCarrito.getCodAlm();
        int CodRam = contenidoCarrito.getCodRam();
        int CodGpu = contenidoCarrito.getCodGpu();
        int CodCpu = contenidoCarrito.getCodCpu();
        int CodVent = contenidoCarrito.getCodVent();
        int CodRefCpu = contenidoCarrito.getCodRefCpu();
        int CodRefGpu = contenidoCarrito.getCodRefGpu();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO contenido_carrito (Precio, Cantidad, CodCar, CodOrd, CodCha, CodFuen, CodPB, CodAlm, CodRam, CodGpu, CodCpu, CodVent, CodRefCpu, CodRefGpu) VALUES (%.2f, %d, %d, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)",
            Precio, Cantidad, CodCar,
            CodOrd != 0 ? String.valueOf(CodOrd) : "NULL",
            CodCha != 0 ? String.valueOf(CodCha) : "NULL",
            CodFuen != 0 ? String.valueOf(CodFuen) : "NULL",
            CodPB != 0 ? String.valueOf(CodPB) : "NULL",
            CodAlm != 0 ? String.valueOf(CodAlm) : "NULL",
            CodRam != 0 ? String.valueOf(CodRam) : "NULL",
            CodGpu != 0 ? String.valueOf(CodGpu) : "NULL",
            CodCpu != 0 ? String.valueOf(CodCpu) : "NULL",
            CodVent != 0 ? String.valueOf(CodVent) : "NULL",
            CodRefCpu != 0 ? String.valueOf(CodRefCpu) : "NULL",
            CodRefGpu != 0 ? String.valueOf(CodRefGpu) : "NULL"
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating contenido_carrito failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating contenido_carrito failed, no ID obtained.");
            }
        }
    }

    public int update(Contenido_carrito contenidoCarrito) throws SQLException {
        int CodConCar = contenidoCarrito.getCodConCar();
        float Precio = contenidoCarrito.getPrecio();
        int Cantidad = contenidoCarrito.getCantidad();
        int CodCar = contenidoCarrito.getCodCar();
        int CodOrd = contenidoCarrito.getCodOrd();
        int CodCha = contenidoCarrito.getCodCha();
        int CodFuen = contenidoCarrito.getCodFuen();
        int CodPB = contenidoCarrito.getCodPB();
        int CodAlm = contenidoCarrito.getCodAlm();
        int CodRam = contenidoCarrito.getCodRam();
        int CodGpu = contenidoCarrito.getCodGpu();
        int CodCpu = contenidoCarrito.getCodCpu();
        int CodVent = contenidoCarrito.getCodVent();
        int CodRefCpu = contenidoCarrito.getCodRefCpu();
        int CodRefGpu = contenidoCarrito.getCodRefGpu();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE contenido_carrito SET Precio=%.2f, Cantidad=%d, CodCar=%d, CodOrd=%s, CodCha=%s, CodFuen=%s, CodPB=%s, CodAlm=%s, CodRam=%s, CodGpu=%s, CodCpu=%s, CodVent=%s, CodRefCpu=%s, CodRefGpu=%s WHERE CodConCar=%d",
            Precio, Cantidad, CodCar,
            CodOrd != 0 ? String.valueOf(CodOrd) : "NULL",
            CodCha != 0 ? String.valueOf(CodCha) : "NULL",
            CodFuen != 0 ? String.valueOf(CodFuen) : "NULL",
            CodPB != 0 ? String.valueOf(CodPB) : "NULL",
            CodAlm != 0 ? String.valueOf(CodAlm) : "NULL",
            CodRam != 0 ? String.valueOf(CodRam) : "NULL",
            CodGpu != 0 ? String.valueOf(CodGpu) : "NULL",
            CodCpu != 0 ? String.valueOf(CodCpu) : "NULL",
            CodVent != 0 ? String.valueOf(CodVent) : "NULL",
            CodRefCpu != 0 ? String.valueOf(CodRefCpu) : "NULL",
            CodRefGpu != 0 ? String.valueOf(CodRefGpu) : "NULL",
            CodConCar
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM contenido_carrito WHERE CodConCar=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
