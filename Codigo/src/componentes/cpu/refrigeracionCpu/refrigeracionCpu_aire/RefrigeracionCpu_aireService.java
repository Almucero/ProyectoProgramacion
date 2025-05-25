package componentes.cpu.refrigeracionCpu.refrigeracionCpu_aire;

import java.sql.*;
import java.util.ArrayList;

public class RefrigeracionCpu_aireService {
    Connection conn;

    public RefrigeracionCpu_aireService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<RefrigeracionCpu_aire> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<RefrigeracionCpu_aire> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT Velocidad, CodRefCpu FROM refrigeracionCpu_aire";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            float Velocidad = querySet.getFloat("Velocidad");
            int CodRefCpu = querySet.getInt("CodRefCpu");
            result.add(new RefrigeracionCpu_aire(Velocidad, CodRefCpu));
        }
        statement.close();
        return result;
    }

    public RefrigeracionCpu_aire requestById(int codRefCpu) throws SQLException {
        Statement statement = null;
        RefrigeracionCpu_aire result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT Velocidad, CodRefCpu FROM refrigeracionCpu_aire WHERE CodRefCpu=%d", codRefCpu);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            float Velocidad = querySet.getFloat("Velocidad");
            int CodRefCpu = querySet.getInt("CodRefCpu");
            result = new RefrigeracionCpu_aire(Velocidad, CodRefCpu);
        }
        statement.close();
        return result;
    }

    public int create(RefrigeracionCpu_aire refrigeracionCpuAire) throws SQLException {
        float Velocidad = refrigeracionCpuAire.getVelocidad();
        int CodRefCpu = refrigeracionCpuAire.getCodRefCpu();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO refrigeracionCpu_aire (Velocidad, CodRefCpu) VALUES (%.2f, %d)",
            Velocidad, CodRefCpu
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public int update(RefrigeracionCpu_aire refrigeracionCpuAire) throws SQLException {
        float Velocidad = refrigeracionCpuAire.getVelocidad();
        int CodRefCpu = refrigeracionCpuAire.getCodRefCpu();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE refrigeracionCpu_aire SET Velocidad=%.2f WHERE CodRefCpu=%d",
            Velocidad, CodRefCpu
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(int codRefCpu) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM refrigeracionCpu_aire WHERE CodRefCpu=%d", codRefCpu);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
