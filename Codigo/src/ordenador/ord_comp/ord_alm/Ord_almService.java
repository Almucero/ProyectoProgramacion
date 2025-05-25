package ordenador.ord_comp.ord_alm;

import java.sql.*;
import java.util.ArrayList;

public class Ord_almService {
    Connection conn;

    public Ord_almService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Ord_alm> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Ord_alm> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodOrd, CodAlmSecundario, Cantidad FROM ord_alm";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodOrd = querySet.getInt("CodOrd");
            int CodAlmSecundario = querySet.getInt("CodAlmSecundario");
            int Cantidad = querySet.getInt("Cantidad");
            result.add(new Ord_alm(CodOrd, CodAlmSecundario, Cantidad));
        }
        statement.close();
        return result;
    }

    public Ord_alm requestById(int codOrd, int codAlmSecundario) throws SQLException {
        Statement statement = null;
        Ord_alm result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodOrd, CodAlmSecundario, Cantidad FROM ord_alm WHERE CodOrd=%d AND CodAlmSecundario=%d", codOrd, codAlmSecundario);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodOrd = querySet.getInt("CodOrd");
            int CodAlmSecundario = querySet.getInt("CodAlmSecundario");
            int Cantidad = querySet.getInt("Cantidad");
            result = new Ord_alm(CodOrd, CodAlmSecundario, Cantidad);
        }
        statement.close();
        return result;
    }

    public int create(Ord_alm ordAlm) throws SQLException {
        int CodOrd = ordAlm.getCodOrd();
        int CodAlmSecundario = ordAlm.getCodAlmSecundario();
        int Cantidad = ordAlm.getCantidad();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO ord_alm (CodOrd, CodAlmSecundario, Cantidad) VALUES (%d, %d, %d)",
            CodOrd, CodAlmSecundario, Cantidad
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public int update(Ord_alm ordAlm) throws SQLException {
        int CodOrd = ordAlm.getCodOrd();
        int CodAlmSecundario = ordAlm.getCodAlmSecundario();
        int Cantidad = ordAlm.getCantidad();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE ord_alm SET Cantidad=%d WHERE CodOrd=%d AND CodAlmSecundario=%d",
            Cantidad, CodOrd, CodAlmSecundario
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(int codOrd, int codAlmSecundario) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM ord_alm WHERE CodOrd=%d AND CodAlmSecundario=%d", codOrd, codAlmSecundario);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
