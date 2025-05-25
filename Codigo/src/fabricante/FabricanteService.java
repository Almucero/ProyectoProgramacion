package fabricante;

import java.sql.*;
import java.util.ArrayList;

public class FabricanteService {
    Connection conn;

    public FabricanteService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Fabricante> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Fabricante> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodFab, NomFab FROM fabricante";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodFab = querySet.getInt("CodFab");
            String NomFab = querySet.getString("NomFab");
            result.add(new Fabricante(CodFab, NomFab));
        }
        statement.close();
        return result;
    }

    public Fabricante requestById(long id) throws SQLException {
        Statement statement = null;
        Fabricante result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodFab, NomFab FROM fabricante WHERE CodFab=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodFab = querySet.getInt("CodFab");
            String NomFab = querySet.getString("NomFab");
            result = new Fabricante(CodFab, NomFab);
        }
        statement.close();
        return result;
    }

    public long create(Fabricante fabricante) throws SQLException {
        String NomFab = fabricante.getNomFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO fabricante (NomFab) VALUES ('%s')",
            NomFab
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating fabricante failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating fabricante failed, no ID obtained.");
            }
        }
    }

    public int update(Fabricante fabricante) throws SQLException {
        int CodFab = fabricante.getCodFab();
        String NomFab = fabricante.getNomFab();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE fabricante SET NomFab='%s' WHERE CodFab=%d",
            NomFab, CodFab
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM fabricante WHERE CodFab=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
