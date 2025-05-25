package componentes.gpu.refrigeracionGpu.refrigeracionGpu_liquida;

import java.sql.*;
import java.util.ArrayList;

public class RefrigeracionGpu_liquidaService {
    Connection conn;

    public RefrigeracionGpu_liquidaService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<RefrigeracionGpu_liquida> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<RefrigeracionGpu_liquida> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT PotBomb, Consumo, CodRefGpu FROM refrigeracionGpu_liquida";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            float PotBomb = querySet.getFloat("PotBomb");
            float Consumo = querySet.getFloat("Consumo");
            int CodRefGpu = querySet.getInt("CodRefGpu");
            result.add(new RefrigeracionGpu_liquida(PotBomb, Consumo, CodRefGpu));
        }
        statement.close();
        return result;
    }

    public RefrigeracionGpu_liquida requestById(int codRefGpu) throws SQLException {
        Statement statement = null;
        RefrigeracionGpu_liquida result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT PotBomb, Consumo, CodRefGpu FROM refrigeracionGpu_liquida WHERE CodRefGpu=%d", codRefGpu);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            float PotBomb = querySet.getFloat("PotBomb");
            float Consumo = querySet.getFloat("Consumo");
            int CodRefGpu = querySet.getInt("CodRefGpu");
            result = new RefrigeracionGpu_liquida(PotBomb, Consumo, CodRefGpu);
        }
        statement.close();
        return result;
    }

    public int create(RefrigeracionGpu_liquida refrigeracionGpuLiquida) throws SQLException {
        float PotBomb = refrigeracionGpuLiquida.getPotBomb();
        float Consumo = refrigeracionGpuLiquida.getConsumo();
        int CodRefGpu = refrigeracionGpuLiquida.getCodRefGpu();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO refrigeracionGpu_liquida (PotBomb, Consumo, CodRefGpu) VALUES (%.2f, %.2f, %d)",
            PotBomb, Consumo, CodRefGpu
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public int update(RefrigeracionGpu_liquida refrigeracionGpuLiquida) throws SQLException {
        float PotBomb = refrigeracionGpuLiquida.getPotBomb();
        float Consumo = refrigeracionGpuLiquida.getConsumo();
        int CodRefGpu = refrigeracionGpuLiquida.getCodRefGpu();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE refrigeracionGpu_liquida SET PotBomb=%.2f, Consumo=%.2f WHERE CodRefGpu=%d",
            PotBomb, Consumo, CodRefGpu
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(int codRefGpu) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM refrigeracionGpu_liquida WHERE CodRefGpu=%d", codRefGpu);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
