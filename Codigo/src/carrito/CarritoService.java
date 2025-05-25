package carrito;

import java.sql.*;
import java.util.ArrayList;

public class CarritoService {
    Connection conn;

    public CarritoService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Carrito> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Carrito> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodCar, Fecha, PrecioTotal, Estado, CodUsu FROM carrito";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodCar = querySet.getInt("CodCar");
            Date Fecha = querySet.getDate("Fecha");
            float PrecioTotal = querySet.getFloat("PrecioTotal");
            String Estado = querySet.getString("Estado");
            int CodUsu = querySet.getInt("CodUsu");
            result.add(new Carrito(CodCar, Fecha, PrecioTotal, Estado, CodUsu));
        }
        statement.close();
        return result;
    }

    public Carrito requestById(long id) throws SQLException {
        Statement statement = null;
        Carrito result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodCar, Fecha, PrecioTotal, Estado, CodUsu FROM carrito WHERE CodCar=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodCar = querySet.getInt("CodCar");
            Date Fecha = querySet.getDate("Fecha");
            float PrecioTotal = querySet.getFloat("PrecioTotal");
            String Estado = querySet.getString("Estado");
            int CodUsu = querySet.getInt("CodUsu");
            result = new Carrito(CodCar, Fecha, PrecioTotal, Estado, CodUsu);
        }
        statement.close();
        return result;
    }

    public long create(Carrito carrito) throws SQLException {
        Date Fecha = carrito.getFecha();
        float PrecioTotal = carrito.getPrecioTotal();
        String Estado = carrito.getEstado();
        int CodUsu = carrito.getCodUsu();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO carrito (Fecha, PrecioTotal, Estado, CodUsu) VALUES ('%s', %.2f, '%s', %d)",
            Fecha != null ? Fecha.toString() : "GETDATE()", PrecioTotal, Estado, CodUsu
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating carrito failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating carrito failed, no ID obtained.");
            }
        }
    }

    public int update(Carrito carrito) throws SQLException {
        int CodCar = carrito.getCodCar();
        Date Fecha = carrito.getFecha();
        float PrecioTotal = carrito.getPrecioTotal();
        String Estado = carrito.getEstado();
        int CodUsu = carrito.getCodUsu();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE carrito SET Fecha='%s', PrecioTotal=%.2f, Estado='%s', CodUsu=%d WHERE CodCar=%d",
            Fecha != null ? Fecha.toString() : "GETDATE()", PrecioTotal, Estado, CodUsu, CodCar
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM carrito WHERE CodCar=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
