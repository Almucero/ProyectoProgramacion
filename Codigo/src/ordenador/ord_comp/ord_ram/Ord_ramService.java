package ordenador.ord_comp.ord_ram;

import java.sql.*;
import java.util.ArrayList;

public class Ord_ramService {
    Connection conn;

    public Ord_ramService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Ord_ram> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Ord_ram> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodOrd, CodRam, Cantidad FROM ord_ram";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodOrd = querySet.getInt("CodOrd");
            int CodRam = querySet.getInt("CodRam");
            int Cantidad = querySet.getInt("Cantidad");
            result.add(new Ord_ram(CodOrd, CodRam, Cantidad));
        }
        statement.close();
        return result;
    }

    public Ord_ram requestById(int codOrd, int codRam) throws SQLException {
        Statement statement = null;
        Ord_ram result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "SELECT CodOrd, CodRam, Cantidad FROM ord_ram WHERE CodOrd=%d AND CodRam=%d",
            codOrd, codRam
        );
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodOrd = querySet.getInt("CodOrd");
            int CodRam = querySet.getInt("CodRam");
            int Cantidad = querySet.getInt("Cantidad");
            result = new Ord_ram(CodOrd, CodRam, Cantidad);
        }
        statement.close();
        return result;
    }

    public int create(Ord_ram ordRam) throws SQLException {
        int CodOrd = ordRam.getCodOrd();
        int CodRam = ordRam.getCodRam();
        int Cantidad = ordRam.getCantidad();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO ord_ram (CodOrd, CodRam, Cantidad) VALUES (%d, %d, %d)",
            CodOrd, CodRam, Cantidad
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public int update(Ord_ram ordRam) throws SQLException {
        int CodOrd = ordRam.getCodOrd();
        int CodRam = ordRam.getCodRam();
        int Cantidad = ordRam.getCantidad();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE ord_ram SET Cantidad=%d WHERE CodOrd=%d AND CodRam=%d",
            Cantidad, CodOrd, CodRam
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(int codOrd, int codRam) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "DELETE FROM ord_ram WHERE CodOrd=%d AND CodRam=%d",
            codOrd, codRam
        );
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
