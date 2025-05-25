package componentes.gpu.refrigeracionGpu.refrigeracionGpu_aire;

import java.sql.*;
import java.util.ArrayList;

public class RefrigeracionGpu_aireService {
    Connection conn;

    public RefrigeracionGpu_aireService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<RefrigeracionGpu_aire> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<RefrigeracionGpu_aire> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT Velocidad, CodRefGpu FROM refrigeracionGpu_aire";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            float Velocidad = querySet.getFloat("Velocidad");
            int CodRefGpu = querySet.getInt("CodRefGpu");
            result.add(new RefrigeracionGpu_aire(Velocidad, CodRefGpu));
        }
        statement.close();
        return result;
    }

    public RefrigeracionGpu_aire requestById(int codRefGpu) throws SQLException {
        Statement statement = null;
        RefrigeracionGpu_aire result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT Velocidad, CodRefGpu FROM refrigeracionGpu_aire WHERE CodRefGpu=%d", codRefGpu);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            float Velocidad = querySet.getFloat("Velocidad");
            int CodRefGpu = querySet.getInt("CodRefGpu");
            result = new RefrigeracionGpu_aire(Velocidad, CodRefGpu);
        }
        statement.close();
        return result;
    }

    public int create(RefrigeracionGpu_aire refrigeracionGpuAire) throws SQLException {
        float Velocidad = refrigeracionGpuAire.getVelocidad();
        int CodRefGpu = refrigeracionGpuAire.getCodRefGpu();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO refrigeracionGpu_aire (Velocidad, CodRefGpu) VALUES (%.2f, %d)",
            Velocidad, CodRefGpu
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public int update(RefrigeracionGpu_aire refrigeracionGpuAire) throws SQLException {
        float Velocidad = refrigeracionGpuAire.getVelocidad();
        int CodRefGpu = refrigeracionGpuAire.getCodRefGpu();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE refrigeracionGpu_aire SET Velocidad=%.2f WHERE CodRefGpu=%d",
            Velocidad, CodRefGpu
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(int codRefGpu) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM refrigeracionGpu_aire WHERE CodRefGpu=%d", codRefGpu);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
