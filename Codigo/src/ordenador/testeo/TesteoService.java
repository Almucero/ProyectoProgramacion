package ordenador.testeo;

import java.sql.*;
import java.util.ArrayList;

public class TesteoService {
    Connection conn;

    public TesteoService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Testeo> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Testeo> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodTest, Precio, Fecha, Reporte, CodOrd, CodSerTest FROM testeo";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodTest = querySet.getInt("CodTest");
            float Precio = querySet.getFloat("Precio");
            Date Fecha = querySet.getDate("Fecha");
            String Reporte = querySet.getString("Reporte");
            int CodOrd = querySet.getInt("CodOrd");
            int CodSerTest = querySet.getInt("CodSerTest");
            result.add(new Testeo(CodTest, Precio, Fecha, Reporte, CodOrd, CodSerTest));
        }
        statement.close();
        return result;
    }

    public Testeo requestById(long id) throws SQLException {
        Statement statement = null;
        Testeo result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodTest, Precio, Fecha, Reporte, CodOrd, CodSerTest FROM testeo WHERE CodTest=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodTest = querySet.getInt("CodTest");
            float Precio = querySet.getFloat("Precio");
            Date Fecha = querySet.getDate("Fecha");
            String Reporte = querySet.getString("Reporte");
            int CodOrd = querySet.getInt("CodOrd");
            int CodSerTest = querySet.getInt("CodSerTest");
            result = new Testeo(CodTest, Precio, Fecha, Reporte, CodOrd, CodSerTest);
        }
        statement.close();
        return result;
    }

    public long create(Testeo testeo) throws SQLException {
        float Precio = testeo.getPrecio();
        Date Fecha = testeo.getFecha();
        String Reporte = testeo.getReporte();
        int CodOrd = testeo.getCodOrd();
        int CodSerTest = testeo.getCodSerTest();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO testeo (Precio, Fecha, Reporte, CodOrd, CodSerTest) VALUES (%.2f, '%s', '%s', %d, %d)",
            Precio, Fecha != null ? Fecha.toString() : "GETDATE()", Reporte, CodOrd, CodSerTest
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating testeo failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating testeo failed, no ID obtained.");
            }
        }
    }

    public int update(Testeo testeo) throws SQLException {
        int CodTest = testeo.getCodTest();
        float Precio = testeo.getPrecio();
        Date Fecha = testeo.getFecha();
        String Reporte = testeo.getReporte();
        int CodOrd = testeo.getCodOrd();
        int CodSerTest = testeo.getCodSerTest();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE testeo SET Precio=%.2f, Fecha='%s', Reporte='%s', CodOrd=%d, CodSerTest=%d WHERE CodTest=%d",
            Precio, Fecha != null ? Fecha.toString() : "GETDATE()", Reporte, CodOrd, CodSerTest, CodTest
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM testeo WHERE CodTest=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
