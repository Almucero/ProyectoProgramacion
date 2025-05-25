package componentes.placaBase;

import java.sql.*;
import java.util.ArrayList;

public class PlacaBaseService {
    Connection conn;

    public PlacaBaseService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<PlacaBase> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<PlacaBase> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodPB, Precio, maxRam, maxCpu, maxGpu, tipoRam, FF, Stock, Chipset, Socket, Modelo, CodFab FROM placaBase";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodPB = querySet.getInt("CodPB");
            float Precio = querySet.getFloat("Precio");
            int maxRam = querySet.getInt("maxRam");
            int maxCpu = querySet.getInt("maxCpu");
            int maxGpu = querySet.getInt("maxGpu");
            String tipoRam = querySet.getString("tipoRam");
            String FF = querySet.getString("FF");
            int Stock = querySet.getInt("Stock");
            String Chipset = querySet.getString("Chipset");
            String Socket = querySet.getString("Socket");
            String Modelo = querySet.getString("Modelo");
            int CodFab = querySet.getInt("CodFab");
            result.add(new PlacaBase(CodPB, Precio, maxRam, maxCpu, maxGpu, tipoRam, FF, Stock, Chipset, Socket, Modelo, CodFab));
        }
        statement.close();
        return result;
    }

    public PlacaBase requestById(long id) throws SQLException {
        Statement statement = null;
        PlacaBase result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodPB, Precio, maxRam, maxCpu, maxGpu, tipoRam, FF, Stock, Chipset, Socket, Modelo, CodFab FROM placaBase WHERE CodPB=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodPB = querySet.getInt("CodPB");
            float Precio = querySet.getFloat("Precio");
            int maxRam = querySet.getInt("maxRam");
            int maxCpu = querySet.getInt("maxCpu");
            int maxGpu = querySet.getInt("maxGpu");
            String tipoRam = querySet.getString("tipoRam");
            String FF = querySet.getString("FF");
            int Stock = querySet.getInt("Stock");
            String Chipset = querySet.getString("Chipset");
            String Socket = querySet.getString("Socket");
            String Modelo = querySet.getString("Modelo");
            int CodFab = querySet.getInt("CodFab");
            result = new PlacaBase(CodPB, Precio, maxRam, maxCpu, maxGpu, tipoRam, FF, Stock, Chipset, Socket, Modelo, CodFab);
        }
        statement.close();
        return result;
    }

    public long create(PlacaBase placaBase) throws SQLException {
        float Precio = placaBase.getPrecio();
        int maxRam = placaBase.getMaxRam();
        int maxCpu = placaBase.getMaxCpu();
        int maxGpu = placaBase.getMaxGpu();
        String tipoRam = placaBase.getTipoRam();
        String FF = placaBase.getFF();
        int Stock = placaBase.getStock();
        String Chipset = placaBase.getChipset();
        String Socket = placaBase.getSocket();
        String Modelo = placaBase.getModelo();
        int CodFab = placaBase.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO placaBase (Precio, maxRam, maxCpu, maxGpu, tipoRam, FF, Stock, Chipset, Socket, Modelo, CodFab) VALUES (%.2f, %d, %d, %d, '%s', '%s', %d, '%s', '%s', '%s', %s)",
            Precio, maxRam, maxCpu, maxGpu, tipoRam, FF, Stock, Chipset, Socket, Modelo, CodFab != 0 ? String.valueOf(CodFab) : "NULL"
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating placaBase failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating placaBase failed, no ID obtained.");
            }
        }
    }

    public int update(PlacaBase placaBase) throws SQLException {
        int CodPB = placaBase.getCodPB();
        float Precio = placaBase.getPrecio();
        int maxRam = placaBase.getMaxRam();
        int maxCpu = placaBase.getMaxCpu();
        int maxGpu = placaBase.getMaxGpu();
        String tipoRam = placaBase.getTipoRam();
        String FF = placaBase.getFF();
        int Stock = placaBase.getStock();
        String Chipset = placaBase.getChipset();
        String Socket = placaBase.getSocket();
        String Modelo = placaBase.getModelo();
        int CodFab = placaBase.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE placaBase SET Precio=%.2f, maxRam=%d, maxCpu=%d, maxGpu=%d, tipoRam='%s', FF='%s', Stock=%d, Chipset='%s', Socket='%s', Modelo='%s', CodFab=%s WHERE CodPB=%d",
            Precio, maxRam, maxCpu, maxGpu, tipoRam, FF, Stock, Chipset, Socket, Modelo, CodFab != 0 ? String.valueOf(CodFab) : "NULL", CodPB
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM placaBase WHERE CodPB=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
