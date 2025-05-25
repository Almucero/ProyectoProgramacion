package componentes.almacenamiento;

import java.sql.*;
import java.util.ArrayList;

public class AlmacenamientoService {
    Connection conn;

    public AlmacenamientoService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Almacenamiento> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Almacenamiento> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodAlm, Precio, Modelo, Capacidad, Consumo, Stock, CodFab FROM almacenamiento";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodAlm = querySet.getInt("CodAlm");
            float Precio = querySet.getFloat("Precio");
            String Modelo = querySet.getString("Modelo");
            float Capacidad = querySet.getFloat("Capacidad");
            float Consumo = querySet.getFloat("Consumo");
            int Stock = querySet.getInt("Stock");
            int CodFab = querySet.getInt("CodFab");
            result.add(new Almacenamiento(CodAlm, Precio, Modelo, Capacidad, Consumo, Stock, CodFab));
        }
        statement.close();
        return result;
    }

    public Almacenamiento requestById(long id) throws SQLException {
        Statement statement = null;
        Almacenamiento result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodAlm, Precio, Modelo, Capacidad, Consumo, Stock, CodFab FROM almacenamiento WHERE CodAlm=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodAlm = querySet.getInt("CodAlm");
            float Precio = querySet.getFloat("Precio");
            String Modelo = querySet.getString("Modelo");
            float Capacidad = querySet.getFloat("Capacidad");
            float Consumo = querySet.getFloat("Consumo");
            int Stock = querySet.getInt("Stock");
            int CodFab = querySet.getInt("CodFab");
            result = new Almacenamiento(CodAlm, Precio, Modelo, Capacidad, Consumo, Stock, CodFab);
        }
        statement.close();
        return result;
    }

    public long create(Almacenamiento almacenamiento) throws SQLException {
        String Modelo = almacenamiento.getModelo();
        float Precio = almacenamiento.getPrecio();
        float Capacidad = almacenamiento.getCapacidad();
        float Consumo = almacenamiento.getConsumo();
        int Stock = almacenamiento.getStock();
        int CodFab = almacenamiento.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO almacenamiento (Precio, Modelo, Capacidad, Consumo, Stock, CodFab) VALUES (%.2f, '%s', %.2f, %.2f, %d, %s)",
            Precio, Modelo, Capacidad, Consumo, Stock, CodFab != 0 ? String.valueOf(CodFab) : "NULL"
        );
        System.out.println("SQL generado: " + sql);
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating almacenamiento failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating almacenamiento failed, no ID obtained.");
            }
        }
    }

    public int update(Almacenamiento almacenamiento) throws SQLException {
        int CodAlm = almacenamiento.getCodAlm();
        float Precio = almacenamiento.getPrecio();
        String Modelo = almacenamiento.getModelo();
        float Capacidad = almacenamiento.getCapacidad();
        float Consumo = almacenamiento.getConsumo();
        int Stock = almacenamiento.getStock();
        int CodFab = almacenamiento.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE almacenamiento SET Precio=%.2f, Modelo='%s', Capacidad=%.2f, Consumo=%.2f, Stock=%d, CodFab=%s WHERE CodAlm=%d",
            Precio, Modelo, Capacidad, Consumo, Stock, CodFab != 0 ? String.valueOf(CodFab) : "NULL", CodAlm
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM almacenamiento WHERE CodAlm=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
