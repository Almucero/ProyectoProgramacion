package ordenador.ord_comp.ord_gpu;

import java.sql.*;
import java.util.ArrayList;

public class Ord_gpuService {
    Connection conn;

    public Ord_gpuService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Ord_gpu> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Ord_gpu> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodOrd, CodGpu, CodRefGpu, Cantidad FROM ord_gpu";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodOrd = querySet.getInt("CodOrd");
            int CodGpu = querySet.getInt("CodGpu");
            int CodRefGpu = querySet.getInt("CodRefGpu");
            int Cantidad = querySet.getInt("Cantidad");
            result.add(new Ord_gpu(CodOrd, CodGpu, CodRefGpu, Cantidad));
        }
        statement.close();
        return result;
    }

    public Ord_gpu requestById(int codOrd, int codGpu, int codRefGpu) throws SQLException {
        Statement statement = null;
        Ord_gpu result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "SELECT CodOrd, CodGpu, CodRefGpu, Cantidad FROM ord_gpu WHERE CodOrd=%d AND CodGpu=%d AND CodRefGpu=%d",
            codOrd, codGpu, codRefGpu
        );
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodOrd = querySet.getInt("CodOrd");
            int CodGpu = querySet.getInt("CodGpu");
            int CodRefGpu = querySet.getInt("CodRefGpu");
            int Cantidad = querySet.getInt("Cantidad");
            result = new Ord_gpu(CodOrd, CodGpu, CodRefGpu, Cantidad);
        }
        statement.close();
        return result;
    }

    public int create(Ord_gpu ordGpu) throws SQLException {
        int CodOrd = ordGpu.getCodOrd();
        int CodGpu = ordGpu.getCodGpu();
        int CodRefGpu = ordGpu.getCodRefGpu();
        int Cantidad = ordGpu.getCantidad();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO ord_gpu (CodOrd, CodGpu, CodRefGpu, Cantidad) VALUES (%d, %d, %d, %d)",
            CodOrd, CodGpu, CodRefGpu, Cantidad
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public int update(Ord_gpu ordGpu) throws SQLException {
        int CodOrd = ordGpu.getCodOrd();
        int CodGpu = ordGpu.getCodGpu();
        int CodRefGpu = ordGpu.getCodRefGpu();
        int Cantidad = ordGpu.getCantidad();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE ord_gpu SET Cantidad=%d WHERE CodOrd=%d AND CodGpu=%d AND CodRefGpu=%d",
            Cantidad, CodOrd, CodGpu, CodRefGpu
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(int codOrd, int codGpu, int codRefGpu) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "DELETE FROM ord_gpu WHERE CodOrd=%d AND CodGpu=%d AND CodRefGpu=%d",
            codOrd, codGpu, codRefGpu
        );
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
