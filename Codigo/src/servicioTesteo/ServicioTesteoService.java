package servicioTesteo;

import java.sql.*;
import java.util.ArrayList;

public class ServicioTesteoService {
    Connection conn;

    public ServicioTesteoService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<ServicioTesteo> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<ServicioTesteo> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodSerTest, Nombre, Estrellas FROM servicioTesteo";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodSerTest = querySet.getInt("CodSerTest");
            String Nombre = querySet.getString("Nombre");
            float Estrellas = querySet.getFloat("Estrellas");
            result.add(new ServicioTesteo(CodSerTest, Nombre, Estrellas));
        }
        statement.close();
        return result;
    }

    public ServicioTesteo requestById(long id) throws SQLException {
        Statement statement = null;
        ServicioTesteo result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodSerTest, Nombre, Estrellas FROM servicioTesteo WHERE CodSerTest=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodSerTest = querySet.getInt("CodSerTest");
            String Nombre = querySet.getString("Nombre");
            float Estrellas = querySet.getFloat("Estrellas");
            result = new ServicioTesteo(CodSerTest, Nombre, Estrellas);
        }
        statement.close();
        return result;
    }

    public long create(ServicioTesteo servicioTesteo) throws SQLException {
        String Nombre = servicioTesteo.getNombre();
        float Estrellas = servicioTesteo.getEstrellas();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO servicioTesteo (Nombre, Estrellas) VALUES ('%s', %.2f)",
            Nombre, Estrellas
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating servicioTesteo failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating servicioTesteo failed, no ID obtained.");
            }
        }
    }

    public int update(ServicioTesteo servicioTesteo) throws SQLException {
        int CodSerTest = servicioTesteo.getCodSerTest();
        String Nombre = servicioTesteo.getNombre();
        float Estrellas = servicioTesteo.getEstrellas();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE servicioTesteo SET Nombre='%s', Estrellas=%.2f WHERE CodSerTest=%d",
            Nombre, Estrellas, CodSerTest
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM servicioTesteo WHERE CodSerTest=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
