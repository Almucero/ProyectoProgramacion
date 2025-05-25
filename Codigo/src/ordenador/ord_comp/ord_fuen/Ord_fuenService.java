package ordenador.ord_comp.ord_fuen;

import java.sql.*;
import java.util.ArrayList;

public class Ord_fuenService {
    Connection conn;

    public Ord_fuenService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Ord_fuen> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Ord_fuen> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodOrd, CodFuen, Cantidad FROM ord_fuen";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodOrd = querySet.getInt("CodOrd");
            int CodFuen = querySet.getInt("CodFuen");
            int Cantidad = querySet.getInt("Cantidad");
            result.add(new Ord_fuen(CodOrd, CodFuen, Cantidad));
        }
        statement.close();
        return result;
    }

    public Ord_fuen requestById(int codOrd, int codFuen) throws SQLException {
        Statement statement = null;
        Ord_fuen result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "SELECT CodOrd, CodFuen, Cantidad FROM ord_fuen WHERE CodOrd=%d AND CodFuen=%d",
            codOrd, codFuen
        );
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodOrd = querySet.getInt("CodOrd");
            int CodFuen = querySet.getInt("CodFuen");
            int Cantidad = querySet.getInt("Cantidad");
            result = new Ord_fuen(CodOrd, CodFuen, Cantidad);
        }
        statement.close();
        return result;
    }

    public int create(Ord_fuen ordFuen) throws SQLException {
        int CodOrd = ordFuen.getCodOrd();
        int CodFuen = ordFuen.getCodFuen();
        int Cantidad = ordFuen.getCantidad();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO ord_fuen (CodOrd, CodFuen, Cantidad) VALUES (%d, %d, %d)",
            CodOrd, CodFuen, Cantidad
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public int update(Ord_fuen ordFuen) throws SQLException {
        int CodOrd = ordFuen.getCodOrd();
        int CodFuen = ordFuen.getCodFuen();
        int Cantidad = ordFuen.getCantidad();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE ord_fuen SET Cantidad=%d WHERE CodOrd=%d AND CodFuen=%d",
            Cantidad, CodOrd, CodFuen
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(int codOrd, int codFuen) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "DELETE FROM ord_fuen WHERE CodOrd=%d AND CodFuen=%d",
            codOrd, codFuen
        );
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
