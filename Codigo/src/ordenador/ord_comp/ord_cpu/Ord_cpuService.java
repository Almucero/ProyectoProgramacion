package ordenador.ord_comp.ord_cpu;

import java.sql.*;
import java.util.ArrayList;

public class Ord_cpuService {
    Connection conn;

    public Ord_cpuService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Ord_cpu> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Ord_cpu> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodOrd, CodCpu, CodRefCpu, Cantidad FROM ord_cpu";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodOrd = querySet.getInt("CodOrd");
            int CodCpu = querySet.getInt("CodCpu");
            int CodRefCpu = querySet.getInt("CodRefCpu");
            int Cantidad = querySet.getInt("Cantidad");
            result.add(new Ord_cpu(CodOrd, CodCpu, CodRefCpu, Cantidad));
        }
        statement.close();
        return result;
    }

    public Ord_cpu requestById(int codOrd, int codCpu, int codRefCpu) throws SQLException {
        Statement statement = null;
        Ord_cpu result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "SELECT CodOrd, CodCpu, CodRefCpu, Cantidad FROM ord_cpu WHERE CodOrd=%d AND CodCpu=%d AND CodRefCpu=%d",
            codOrd, codCpu, codRefCpu
        );
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodOrd = querySet.getInt("CodOrd");
            int CodCpu = querySet.getInt("CodCpu");
            int CodRefCpu = querySet.getInt("CodRefCpu");
            int Cantidad = querySet.getInt("Cantidad");
            result = new Ord_cpu(CodOrd, CodCpu, CodRefCpu, Cantidad);
        }
        statement.close();
        return result;
    }

    public int create(Ord_cpu ordCpu) throws SQLException {
        int CodOrd = ordCpu.getCodOrd();
        int CodCpu = ordCpu.getCodCpu();
        int CodRefCpu = ordCpu.getCodRefCpu();
        int Cantidad = ordCpu.getCantidad();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO ord_cpu (CodOrd, CodCpu, CodRefCpu, Cantidad) VALUES (%d, %d, %d, %d)",
            CodOrd, CodCpu, CodRefCpu, Cantidad
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public int update(Ord_cpu ordCpu) throws SQLException {
        int CodOrd = ordCpu.getCodOrd();
        int CodCpu = ordCpu.getCodCpu();
        int CodRefCpu = ordCpu.getCodRefCpu();
        int Cantidad = ordCpu.getCantidad();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE ord_cpu SET Cantidad=%d WHERE CodOrd=%d AND CodCpu=%d AND CodRefCpu=%d",
            Cantidad, CodOrd, CodCpu, CodRefCpu
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(int codOrd, int codCpu, int codRefCpu) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "DELETE FROM ord_cpu WHERE CodOrd=%d AND CodCpu=%d AND CodRefCpu=%d",
            codOrd, codCpu, codRefCpu
        );
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
