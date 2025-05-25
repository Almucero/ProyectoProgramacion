package componentes.fuente;

import java.sql.*;
import java.util.ArrayList;

public class FuenteService {
    Connection conn;

    public FuenteService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Fuente> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Fuente> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodFuen, Precio, Modelo, Stock, Potencia, Eficiencia, CodFab FROM fuente";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodFuen = querySet.getInt("CodFuen");
            float Precio = querySet.getFloat("Precio");
            String Modelo = querySet.getString("Modelo");
            int Stock = querySet.getInt("Stock");
            float Potencia = querySet.getFloat("Potencia");
            float Eficiencia = querySet.getFloat("Eficiencia");
            int CodFab = querySet.getInt("CodFab");
            result.add(new Fuente(CodFuen, Precio, Modelo, Stock, Potencia, Eficiencia, CodFab));
        }
        statement.close();
        return result;
    }

    public Fuente requestById(long id) throws SQLException {
        Statement statement = null;
        Fuente result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodFuen, Precio, Modelo, Stock, Potencia, Eficiencia, CodFab FROM fuente WHERE CodFuen=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodFuen = querySet.getInt("CodFuen");
            float Precio = querySet.getFloat("Precio");
            String Modelo = querySet.getString("Modelo");
            int Stock = querySet.getInt("Stock");
            float Potencia = querySet.getFloat("Potencia");
            float Eficiencia = querySet.getFloat("Eficiencia");
            int CodFab = querySet.getInt("CodFab");
            result = new Fuente(CodFuen, Precio, Modelo, Stock, Potencia, Eficiencia, CodFab);
        }
        statement.close();
        return result;
    }

    public long create(Fuente fuente) throws SQLException {
        String Modelo = fuente.getModelo();
        float Precio = fuente.getPrecio();
        int Stock = fuente.getStock();
        float Potencia = fuente.getPotencia();
        float Eficiencia = fuente.getEficiencia();
        int CodFab = fuente.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO fuente (Precio, Modelo, Stock, Potencia, Eficiencia, CodFab) VALUES (%.2f, '%s', %d, %.2f, %.2f, %s)",
            Precio, Modelo, Stock, Potencia, Eficiencia, CodFab != 0 ? String.valueOf(CodFab) : "NULL"
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating fuente failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating fuente failed, no ID obtained.");
            }
        }
    }

    public int update(Fuente fuente) throws SQLException {
        int CodFuen = fuente.getCodFuen();
        float Precio = fuente.getPrecio();
        String Modelo = fuente.getModelo();
        int Stock = fuente.getStock();
        float Potencia = fuente.getPotencia();
        float Eficiencia = fuente.getEficiencia();
        int CodFab = fuente.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE fuente SET Precio=%.2f, Modelo='%s', Stock=%d, Potencia=%.2f, Eficiencia=%.2f, CodFab=%s WHERE CodFuen=%d",
            Precio, Modelo, Stock, Potencia, Eficiencia, CodFab != 0 ? String.valueOf(CodFab) : "NULL", CodFuen
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM fuente WHERE CodFuen=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
