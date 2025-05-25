package componentes.ram;

import java.sql.*;
import java.util.ArrayList;

public class RamService {
    Connection conn;

    public RamService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Ram> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Ram> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodRam, Precio, Modelo, Frecuencia, Tipo, Consumo, Stock, CodFab FROM ram";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodRam = querySet.getInt("CodRam");
            float Precio = querySet.getFloat("Precio");
            String Modelo = querySet.getString("Modelo");
            float Frecuencia = querySet.getFloat("Frecuencia");
            String Tipo = querySet.getString("Tipo");
            float Consumo = querySet.getFloat("Consumo");
            int Stock = querySet.getInt("Stock");
            int CodFab = querySet.getInt("CodFab");
            result.add(new Ram(CodRam, Precio, Modelo, Frecuencia, Tipo, Consumo, Stock, CodFab));
        }
        statement.close();
        return result;
    }

    public Ram requestById(long id) throws SQLException {
        Statement statement = null;
        Ram result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodRam, Precio, Modelo, Frecuencia, Tipo, Consumo, Stock, CodFab FROM ram WHERE CodRam=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodRam = querySet.getInt("CodRam");
            float Precio = querySet.getFloat("Precio");
            String Modelo = querySet.getString("Modelo");
            float Frecuencia = querySet.getFloat("Frecuencia");
            String Tipo = querySet.getString("Tipo");
            float Consumo = querySet.getFloat("Consumo");
            int Stock = querySet.getInt("Stock");
            int CodFab = querySet.getInt("CodFab");
            result = new Ram(CodRam, Precio, Modelo, Frecuencia, Tipo, Consumo, Stock, CodFab);
        }
        statement.close();
        return result;
    }

    public long create(Ram ram) throws SQLException {
        String Modelo = ram.getModelo();
        float Precio = ram.getPrecio();
        float Frecuencia = ram.getFrecuencia();
        String Tipo = ram.getTipo();
        float Consumo = ram.getConsumo();
        int Stock = ram.getStock();
        int CodFab = ram.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO ram (Precio, Modelo, Frecuencia, Tipo, Consumo, Stock, CodFab) VALUES (%.2f, '%s', %.2f, '%s', %.2f, %d, %s)",
            Precio, Modelo, Frecuencia, Tipo, Consumo, Stock, CodFab != 0 ? String.valueOf(CodFab) : "NULL"
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating ram failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating ram failed, no ID obtained.");
            }
        }
    }

    public int update(Ram ram) throws SQLException {
        int CodRam = ram.getCodRam();
        float Precio = ram.getPrecio();
        String Modelo = ram.getModelo();
        float Frecuencia = ram.getFrecuencia();
        String Tipo = ram.getTipo();
        float Consumo = ram.getConsumo();
        int Stock = ram.getStock();
        int CodFab = ram.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE ram SET Precio=%.2f, Modelo='%s', Frecuencia=%.2f, Tipo='%s', Consumo=%.2f, Stock=%d, CodFab=%s WHERE CodRam=%d",
            Precio, Modelo, Frecuencia, Tipo, Consumo, Stock, CodFab != 0 ? String.valueOf(CodFab) : "NULL", CodRam
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM ram WHERE CodRam=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
