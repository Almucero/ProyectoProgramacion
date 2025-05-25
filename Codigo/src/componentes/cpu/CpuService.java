package componentes.cpu;

import java.sql.*;
import java.util.ArrayList;

public class CpuService {
    Connection conn;

    public CpuService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Cpu> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Cpu> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodCpu, Precio, Modelo, Consumo, Stock, Nucleos, Socket, Frecuencia, CodFab FROM cpu";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodCpu = querySet.getInt("CodCpu");
            float Precio = querySet.getFloat("Precio");
            String Modelo = querySet.getString("Modelo");
            float Consumo = querySet.getFloat("Consumo");
            int Stock = querySet.getInt("Stock");
            int Nucleos = querySet.getInt("Nucleos");
            String Socket = querySet.getString("Socket");
            float Frecuencia = querySet.getFloat("Frecuencia");
            int CodFab = querySet.getInt("CodFab");
            result.add(new Cpu(CodCpu, Precio, Modelo, Consumo, Stock, Nucleos, Socket, Frecuencia, CodFab));
        }
        statement.close();
        return result;
    }

    public Cpu requestById(long id) throws SQLException {
        Statement statement = null;
        Cpu result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodCpu, Precio, Modelo, Consumo, Stock, Nucleos, Socket, Frecuencia, CodFab FROM cpu WHERE CodCpu=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodCpu = querySet.getInt("CodCpu");
            float Precio = querySet.getFloat("Precio");
            String Modelo = querySet.getString("Modelo");
            float Consumo = querySet.getFloat("Consumo");
            int Stock = querySet.getInt("Stock");
            int Nucleos = querySet.getInt("Nucleos");
            String Socket = querySet.getString("Socket");
            float Frecuencia = querySet.getFloat("Frecuencia");
            int CodFab = querySet.getInt("CodFab");
            result = new Cpu(CodCpu, Precio, Modelo, Consumo, Stock, Nucleos, Socket, Frecuencia, CodFab);
        }
        statement.close();
        return result;
    }

    public long create(Cpu cpu) throws SQLException {
        String Modelo = cpu.getModelo();
        float Precio = cpu.getPrecio();
        float Consumo = cpu.getConsumo();
        int Stock = cpu.getStock();
        int Nucleos = cpu.getNucleos();
        String Socket = cpu.getSocket();
        float Frecuencia = cpu.getFrecuencia();
        int CodFab = cpu.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO cpu (Precio, Modelo, Consumo, Stock, Nucleos, Socket, Frecuencia, CodFab) VALUES (%.2f, '%s', %.2f, %d, %d, '%s', %.2f, %s)",
            Precio, Modelo, Consumo, Stock, Nucleos, Socket, Frecuencia, CodFab != 0 ? String.valueOf(CodFab) : "NULL"
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating cpu failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating cpu failed, no ID obtained.");
            }
        }
    }

    public int update(Cpu cpu) throws SQLException {
        int CodCpu = cpu.getCodCpu();
        float Precio = cpu.getPrecio();
        String Modelo = cpu.getModelo();
        float Consumo = cpu.getConsumo();
        int Stock = cpu.getStock();
        int Nucleos = cpu.getNucleos();
        String Socket = cpu.getSocket();
        float Frecuencia = cpu.getFrecuencia();
        int CodFab = cpu.getCodFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE cpu SET Precio=%.2f, Modelo='%s', Consumo=%.2f, Stock=%d, Nucleos=%d, Socket='%s', Frecuencia=%.2f, CodFab=%s WHERE CodCpu=%d",
            Precio, Modelo, Consumo, Stock, Nucleos, Socket, Frecuencia, CodFab != 0 ? String.valueOf(CodFab) : "NULL", CodCpu
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM cpu WHERE CodCpu=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
