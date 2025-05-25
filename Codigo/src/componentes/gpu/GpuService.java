package componentes.gpu;

import java.sql.*;
import java.util.ArrayList;

public class GpuService {
    Connection conn;

    public GpuService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Gpu> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Gpu> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodGpu, Precio, Modelo, Stock, VRAM, Frecuencia, TipoMem, Consumo, CodFab FROM gpu";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodGpu = querySet.getInt("CodGpu");
            float Precio = querySet.getFloat("Precio");
            String Modelo = querySet.getString("Modelo");
            int Stock = querySet.getInt("Stock");
            float VRAM = querySet.getFloat("VRAM");
            float Frecuencia = querySet.getFloat("Frecuencia");
            String TipoMem = querySet.getString("TipoMem");
            float Consumo = querySet.getFloat("Consumo");
            int CodFab = querySet.getInt("CodFab");
            result.add(new Gpu(CodGpu, Precio, Modelo, Stock, VRAM, Frecuencia, TipoMem, Consumo, CodFab));
        }
        statement.close();
        return result;
    }

    public Gpu requestById(long id) throws SQLException {
        Statement statement = null;
        Gpu result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodGpu, Precio, Modelo, Stock, VRAM, Frecuencia, TipoMem, Consumo, CodFab FROM gpu WHERE CodGpu=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodGpu = querySet.getInt("CodGpu");
            float Precio = querySet.getFloat("Precio");
            String Modelo = querySet.getString("Modelo");
            int Stock = querySet.getInt("Stock");
            float VRAM = querySet.getFloat("VRAM");
            float Frecuencia = querySet.getFloat("Frecuencia");
            String TipoMem = querySet.getString("TipoMem");
            float Consumo = querySet.getFloat("Consumo");
            int CodFab = querySet.getInt("CodFab");
            result = new Gpu(CodGpu, Precio, Modelo, Stock, VRAM, Frecuencia, TipoMem, Consumo, CodFab);
        }
        statement.close();
        return result;
    }

    public long create(Gpu gpu) throws SQLException {
        String Modelo = gpu.getModelo();
        float Precio = gpu.getPrecio();
        int Stock = gpu.getStock();
        float VRAM = gpu.getVRAM();
        float Frecuencia = gpu.getFrecuencia();
        String TipoMem = gpu.getTipoMem();
        float Consumo = gpu.getConsumo();
        int CodFab = gpu.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO gpu (Precio, Modelo, Stock, VRAM, Frecuencia, TipoMem, Consumo, CodFab) VALUES (%.2f, '%s', %d, %.2f, %.2f, '%s', %.2f, %s)",
            Precio, Modelo, Stock, VRAM, Frecuencia, TipoMem, Consumo, CodFab != 0 ? String.valueOf(CodFab) : "NULL"
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating gpu failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating gpu failed, no ID obtained.");
            }
        }
    }

    public int update(Gpu gpu) throws SQLException {
        int CodGpu = gpu.getCodGpu();
        float Precio = gpu.getPrecio();
        String Modelo = gpu.getModelo();
        int Stock = gpu.getStock();
        float VRAM = gpu.getVRAM();
        float Frecuencia = gpu.getFrecuencia();
        String TipoMem = gpu.getTipoMem();
        float Consumo = gpu.getConsumo();
        int CodFab = gpu.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE gpu SET Precio=%.2f, Modelo='%s', Stock=%d, VRAM=%.2f, Frecuencia=%.2f, TipoMem='%s', Consumo=%.2f, CodFab=%s WHERE CodGpu=%d",
            Precio, Modelo, Stock, VRAM, Frecuencia, TipoMem, Consumo, CodFab != 0 ? String.valueOf(CodFab) : "NULL", CodGpu
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM gpu WHERE CodGpu=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
