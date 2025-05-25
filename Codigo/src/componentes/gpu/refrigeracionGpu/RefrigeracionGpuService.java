package componentes.gpu.refrigeracionGpu;

import java.sql.*;
import java.util.ArrayList;

public class RefrigeracionGpuService {
    Connection conn;

    public RefrigeracionGpuService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<RefrigeracionGpu> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<RefrigeracionGpu> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT Precio, CodRefGpu, Modelo, Tipo, Stock, CodFab FROM refrigeracionGpu";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            float Precio = querySet.getFloat("Precio");
            int CodRefGpu = querySet.getInt("CodRefGpu");
            String Modelo = querySet.getString("Modelo");
            String Tipo = querySet.getString("Tipo");
            int Stock = querySet.getInt("Stock");
            int CodFab = querySet.getInt("CodFab");
            result.add(new RefrigeracionGpu(Precio, CodRefGpu, Modelo, Tipo, Stock, CodFab));
        }
        statement.close();
        return result;
    }

    public RefrigeracionGpu requestById(long id) throws SQLException {
        Statement statement = null;
        RefrigeracionGpu result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT Precio, CodRefGpu, Modelo, Tipo, Stock, CodFab FROM refrigeracionGpu WHERE CodRefGpu=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            float Precio = querySet.getFloat("Precio");
            int CodRefGpu = querySet.getInt("CodRefGpu");
            String Modelo = querySet.getString("Modelo");
            String Tipo = querySet.getString("Tipo");
            int Stock = querySet.getInt("Stock");
            int CodFab = querySet.getInt("CodFab");
            result = new RefrigeracionGpu(Precio, CodRefGpu, Modelo, Tipo, Stock, CodFab);
        }
        statement.close();
        return result;
    }

    public long create(RefrigeracionGpu refrigeracionGpu) throws SQLException {
        float Precio = refrigeracionGpu.getPrecio();
        String Modelo = refrigeracionGpu.getModelo();
        String Tipo = refrigeracionGpu.getTipo();
        int Stock = refrigeracionGpu.getStock();
        int CodFab = refrigeracionGpu.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO refrigeracionGpu (Precio, Modelo, Tipo, Stock, CodFab) VALUES (%.2f, '%s', '%s', %d, %s)",
            Precio, Modelo, Tipo, Stock, CodFab != 0 ? String.valueOf(CodFab) : "NULL"
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating refrigeracionGpu failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating refrigeracionGpu failed, no ID obtained.");
            }
        }
    }

    public int update(RefrigeracionGpu refrigeracionGpu) throws SQLException {
        float Precio = refrigeracionGpu.getPrecio();
        int CodRefGpu = refrigeracionGpu.getCodRefGpu();
        String Modelo = refrigeracionGpu.getModelo();
        String Tipo = refrigeracionGpu.getTipo();
        int Stock = refrigeracionGpu.getStock();
        int CodFab = refrigeracionGpu.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE refrigeracionGpu SET Precio=%.2f, Modelo='%s', Tipo='%s', Stock=%d, CodFab=%s WHERE CodRefGpu=%d",
            Precio, Modelo, Tipo, Stock, CodFab != 0 ? String.valueOf(CodFab) : "NULL", CodRefGpu
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM refrigeracionGpu WHERE CodRefGpu=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
