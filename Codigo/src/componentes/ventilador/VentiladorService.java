package componentes.ventilador;

import java.sql.*;
import java.util.ArrayList;

public class VentiladorService {
    Connection conn;

    public VentiladorService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Ventilador> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Ventilador> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodVent, Precio, Modelo, Consumo, Velocidad, Stock, CodFab FROM ventilador";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodVent = querySet.getInt("CodVent");
            float Precio = querySet.getFloat("Precio");
            String Modelo = querySet.getString("Modelo");
            float Consumo = querySet.getFloat("Consumo");
            float Velocidad = querySet.getFloat("Velocidad");
            int Stock = querySet.getInt("Stock");
            int CodFab = querySet.getInt("CodFab");
            result.add(new Ventilador(CodVent, Precio, Modelo, Consumo, Velocidad, Stock, CodFab));
        }
        statement.close();
        return result;
    }

    public Ventilador requestById(long id) throws SQLException {
        Statement statement = null;
        Ventilador result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodVent, Precio, Modelo, Consumo, Velocidad, Stock, CodFab FROM ventilador WHERE CodVent=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodVent = querySet.getInt("CodVent");
            float Precio = querySet.getFloat("Precio");
            String Modelo = querySet.getString("Modelo");
            float Consumo = querySet.getFloat("Consumo");
            float Velocidad = querySet.getFloat("Velocidad");
            int Stock = querySet.getInt("Stock");
            int CodFab = querySet.getInt("CodFab");
            result = new Ventilador(CodVent, Precio, Modelo, Consumo, Velocidad, Stock, CodFab);
        }
        statement.close();
        return result;
    }

    public long create(Ventilador ventilador) throws SQLException {
        String Modelo = ventilador.getModelo();
        float Precio = ventilador.getPrecio();
        float Consumo = ventilador.getConsumo();
        float Velocidad = ventilador.getVelocidad();
        int Stock = ventilador.getStock();
        int CodFab = ventilador.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO ventilador (Precio, Modelo, Consumo, Velocidad, Stock, CodFab) VALUES (%.2f, '%s', %.2f, %.2f, %d, %s)",
            Precio, Modelo, Consumo, Velocidad, Stock, CodFab != 0 ? String.valueOf(CodFab) : "NULL"
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating ventilador failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating ventilador failed, no ID obtained.");
            }
        }
    }

    public int update(Ventilador ventilador) throws SQLException {
        int CodVent = ventilador.getCodVent();
        float Precio = ventilador.getPrecio();
        String Modelo = ventilador.getModelo();
        float Consumo = ventilador.getConsumo();
        float Velocidad = ventilador.getVelocidad();
        int Stock = ventilador.getStock();
        int CodFab = ventilador.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE ventilador SET Precio=%.2f, Modelo='%s', Consumo=%.2f, Velocidad=%.2f, Stock=%d, CodFab=%s WHERE CodVent=%d",
            Precio, Modelo, Consumo, Velocidad, Stock, CodFab != 0 ? String.valueOf(CodFab) : "NULL", CodVent
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM ventilador WHERE CodVent=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
