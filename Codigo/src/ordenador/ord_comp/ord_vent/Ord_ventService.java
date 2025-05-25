package ordenador.ord_comp.ord_vent;

import java.sql.*;
import java.util.ArrayList;

public class Ord_ventService {
    Connection conn;

    public Ord_ventService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Ord_vent> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Ord_vent> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodOrd, CodVent, Cantidad FROM ord_vent";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodOrd = querySet.getInt("CodOrd");
            int CodVent = querySet.getInt("CodVent");
            int Cantidad = querySet.getInt("Cantidad");
            result.add(new Ord_vent(CodOrd, CodVent, Cantidad));
        }
        statement.close();
        return result;
    }

    public Ord_vent requestById(int codOrd, int codVent) throws SQLException {
        Statement statement = null;
        Ord_vent result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "SELECT CodOrd, CodVent, Cantidad FROM ord_vent WHERE CodOrd=%d AND CodVent=%d",
            codOrd, codVent
        );
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodOrd = querySet.getInt("CodOrd");
            int CodVent = querySet.getInt("CodVent");
            int Cantidad = querySet.getInt("Cantidad");
            result = new Ord_vent(CodOrd, CodVent, Cantidad);
        }
        statement.close();
        return result;
    }

    public int create(Ord_vent ordVent) throws SQLException {
        int CodOrd = ordVent.getCodOrd();
        int CodVent = ordVent.getCodVent();
        int Cantidad = ordVent.getCantidad();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO ord_vent (CodOrd, CodVent, Cantidad) VALUES (%d, %d, %d)",
            CodOrd, CodVent, Cantidad
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public int update(Ord_vent ordVent) throws SQLException {
        int CodOrd = ordVent.getCodOrd();
        int CodVent = ordVent.getCodVent();
        int Cantidad = ordVent.getCantidad();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE ord_vent SET Cantidad=%d WHERE CodOrd=%d AND CodVent=%d",
            Cantidad, CodOrd, CodVent
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(int codOrd, int codVent) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "DELETE FROM ord_vent WHERE CodOrd=%d AND CodVent=%d",
            codOrd, codVent
        );
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
