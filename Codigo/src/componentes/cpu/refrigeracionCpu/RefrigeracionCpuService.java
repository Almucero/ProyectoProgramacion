package componentes.cpu.refrigeracionCpu;

import java.sql.*;
import java.util.ArrayList;

public class RefrigeracionCpuService {
    Connection conn;

    public RefrigeracionCpuService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<RefrigeracionCpu> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<RefrigeracionCpu> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodRefCpu, Precio, Modelo, Consumo, Tipo, Stock, CodFab FROM refrigeracionCpu";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodRefCpu = querySet.getInt("CodRefCpu");
            float Precio = querySet.getFloat("Precio");
            String Modelo = querySet.getString("Modelo");
            float Consumo = querySet.getFloat("Consumo");
            String Tipo = querySet.getString("Tipo");
            int Stock = querySet.getInt("Stock");
            int CodFab = querySet.getInt("CodFab");
            result.add(new RefrigeracionCpu(CodRefCpu, Precio, Modelo, Consumo, Tipo, Stock, CodFab));
        }
        statement.close();
        return result;
    }

    public RefrigeracionCpu requestById(long id) throws SQLException {
        Statement statement = null;
        RefrigeracionCpu result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodRefCpu, Precio, Modelo, Consumo, Tipo, Stock, CodFab FROM refrigeracionCpu WHERE CodRefCpu=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodRefCpu = querySet.getInt("CodRefCpu");
            float Precio = querySet.getFloat("Precio");
            String Modelo = querySet.getString("Modelo");
            float Consumo = querySet.getFloat("Consumo");
            String Tipo = querySet.getString("Tipo");
            int Stock = querySet.getInt("Stock");
            int CodFab = querySet.getInt("CodFab");
            result = new RefrigeracionCpu(CodRefCpu, Precio, Modelo, Consumo, Tipo, Stock, CodFab);
        }
        statement.close();
        return result;
    }

    public long create(RefrigeracionCpu refrigeracionCpu) throws SQLException {
        float Precio = refrigeracionCpu.getPrecio();
        String Modelo = refrigeracionCpu.getModelo();
        float Consumo = refrigeracionCpu.getConsumo();
        String Tipo = refrigeracionCpu.getTipo();
        int Stock = refrigeracionCpu.getStock();
        int CodFab = refrigeracionCpu.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO refrigeracionCpu (Precio, Modelo, Consumo, Tipo, Stock, CodFab) VALUES (%.2f, '%s', %.2f, '%s', %d, %s)",
            Precio, Modelo, Consumo, Tipo, Stock, CodFab != 0 ? String.valueOf(CodFab) : "NULL"
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating refrigeracionCpu failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating refrigeracionCpu failed, no ID obtained.");
            }
        }
    }

    public int update(RefrigeracionCpu refrigeracionCpu) throws SQLException {
        int CodRefCpu = refrigeracionCpu.getCodRefCpu();
        float Precio = refrigeracionCpu.getPrecio();
        String Modelo = refrigeracionCpu.getModelo();
        float Consumo = refrigeracionCpu.getConsumo();
        String Tipo = refrigeracionCpu.getTipo();
        int Stock = refrigeracionCpu.getStock();
        int CodFab = refrigeracionCpu.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE refrigeracionCpu SET Precio=%.2f, Modelo='%s', Consumo=%.2f, Tipo='%s', Stock=%d, CodFab=%s WHERE CodRefCpu=%d",
            Precio, Modelo, Consumo, Tipo, Stock, CodFab != 0 ? String.valueOf(CodFab) : "NULL", CodRefCpu
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM refrigeracionCpu WHERE CodRefCpu=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
