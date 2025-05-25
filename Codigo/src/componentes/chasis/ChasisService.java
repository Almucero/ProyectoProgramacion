package componentes.chasis;

import java.sql.*;
import java.util.ArrayList;

public class ChasisService {
    Connection conn;

    public ChasisService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Chasis> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Chasis> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodCha, Precio, Modelo, Color, Stock, Tamanio, CodFab FROM chasis";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodCha = querySet.getInt("CodCha");
            float Precio = querySet.getFloat("Precio");
            String Modelo = querySet.getString("Modelo");
            String Color = querySet.getString("Color");
            int Stock = querySet.getInt("Stock");
            String Tamanio = querySet.getString("Tamanio");
            int CodFab = querySet.getInt("CodFab");
            result.add(new Chasis(CodCha, Precio, Modelo, Color, Stock, Tamanio, CodFab));
        }
        statement.close();
        return result;
    }

    public Chasis requestById(long id) throws SQLException {
        Statement statement = null;
        Chasis result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodCha, Precio, Modelo, Color, Stock, Tamanio, CodFab FROM chasis WHERE CodCha=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodCha = querySet.getInt("CodCha");
            float Precio = querySet.getFloat("Precio");
            String Modelo = querySet.getString("Modelo");
            String Color = querySet.getString("Color");
            int Stock = querySet.getInt("Stock");
            String Tamanio = querySet.getString("Tamanio");
            int CodFab = querySet.getInt("CodFab");
            result = new Chasis(CodCha, Precio, Modelo, Color, Stock, Tamanio, CodFab);
        }
        statement.close();
        return result;
    }

    public long create(Chasis chasis) throws SQLException {
        String Modelo = chasis.getModelo();
        float Precio = chasis.getPrecio();
        String Color = chasis.getColor();
        int Stock = chasis.getStock();
        String Tamanio = chasis.getTamanio();
        int CodFab = chasis.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO chasis (Precio, Modelo, Color, Stock, Tamanio, CodFab) VALUES (%.2f, '%s', '%s', %d, '%s', %s)",
            Precio, Modelo, Color, Stock, Tamanio, CodFab != 0 ? String.valueOf(CodFab) : "NULL"
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating chasis failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating chasis failed, no ID obtained.");
            }
        }
    }

    public int update(Chasis chasis) throws SQLException {
        int CodCha = chasis.getCodCha();
        float Precio = chasis.getPrecio();
        String Modelo = chasis.getModelo();
        String Color = chasis.getColor();
        int Stock = chasis.getStock();
        String Tamanio = chasis.getTamanio();
        int CodFab = chasis.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE chasis SET Precio=%.2f, Modelo='%s', Color='%s', Stock=%d, Tamanio='%s', CodFab=%s WHERE CodCha=%d",
            Precio, Modelo, Color, Stock, Tamanio, CodFab != 0 ? String.valueOf(CodFab) : "NULL", CodCha
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM chasis WHERE CodCha=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
