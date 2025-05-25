package componentes.cpu.refrigeracionCpu.refrigeracionCpu_liquida;

import java.sql.*;
import java.util.ArrayList;

public class RefrigeracionCpu_liquidaService {
    Connection conn;

    public RefrigeracionCpu_liquidaService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<RefrigeracionCpu_liquida> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<RefrigeracionCpu_liquida> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT PotBomb, CodRefCpu FROM refrigeracionCpu_liquida";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            float PotBomb = querySet.getFloat("PotBomb");
            int CodRefCpu = querySet.getInt("CodRefCpu");
            result.add(new RefrigeracionCpu_liquida(PotBomb, CodRefCpu));
        }
        statement.close();
        return result;
    }

    public RefrigeracionCpu_liquida requestById(int codRefCpu) throws SQLException {
        Statement statement = null;
        RefrigeracionCpu_liquida result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT PotBomb, CodRefCpu FROM refrigeracionCpu_liquida WHERE CodRefCpu=%d", codRefCpu);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            float PotBomb = querySet.getFloat("PotBomb");
            int CodRefCpu = querySet.getInt("CodRefCpu");
            result = new RefrigeracionCpu_liquida(PotBomb, CodRefCpu);
        }
        statement.close();
        return result;
    }

    public int create(RefrigeracionCpu_liquida refrigeracionCpuLiquida) throws SQLException {
    float PotBomb = refrigeracionCpuLiquida.getPotBomb();
    int CodRefCpu = refrigeracionCpuLiquida.getCodRefCpu();

    Statement statement = null;
    statement = this.conn.createStatement();
    String sql = String.format(java.util.Locale.US,
        "INSERT INTO refrigeracionCpu_liquida (PotBomb, CodRefCpu) VALUES (%.2f, %d)",
        PotBomb, CodRefCpu
    );
    int affectedRows = statement.executeUpdate(sql);
    statement.close();
    return affectedRows;
}

public int update(RefrigeracionCpu_liquida refrigeracionCpuLiquida) throws SQLException {
    float PotBomb = refrigeracionCpuLiquida.getPotBomb();
    int CodRefCpu = refrigeracionCpuLiquida.getCodRefCpu();

    Statement statement = null;
    statement = this.conn.createStatement();
    String sql = String.format(java.util.Locale.US,
        "UPDATE refrigeracionCpu_liquida SET PotBomb=%.2f WHERE CodRefCpu=%d",
        PotBomb, CodRefCpu
    );
    int affectedRows = statement.executeUpdate(sql);
    statement.close();
    return affectedRows;
}

    public boolean delete(int codRefCpu) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM refrigeracionCpu_liquida WHERE CodRefCpu=%d", codRefCpu);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
