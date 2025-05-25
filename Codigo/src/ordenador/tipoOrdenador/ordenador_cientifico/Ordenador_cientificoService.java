package ordenador.tipoOrdenador.ordenador_cientifico;

import java.sql.*;
import java.util.ArrayList;

public class Ordenador_cientificoService {
    Connection conn;

    public Ordenador_cientificoService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Ordenador_cientifico> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Ordenador_cientifico> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT multiCpu, CodOrd FROM ordenador_cientifico";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int multiCpu = querySet.getInt("multiCpu");
            int CodOrd = querySet.getInt("CodOrd");
            result.add(new Ordenador_cientifico(multiCpu, CodOrd));
        }
        statement.close();
        return result;
    }

    public Ordenador_cientifico requestById(long id) throws SQLException {
        Statement statement = null;
        Ordenador_cientifico result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT multiCpu, CodOrd FROM ordenador_cientifico WHERE CodOrd=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int multiCpu = querySet.getInt("multiCpu");
            int CodOrd = querySet.getInt("CodOrd");
            result = new Ordenador_cientifico(multiCpu, CodOrd);
        }
        statement.close();
        return result;
    }

    public long create(Ordenador_cientifico ordenadorCientifico) throws SQLException {
        int multiCpu = ordenadorCientifico.getMultiCpu();
        int CodOrd = ordenadorCientifico.getCodOrd();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO ordenador_cientifico (multiCpu, CodOrd) VALUES (%d, %d)",
            multiCpu, CodOrd
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating ordenador_cientifico failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                // Como CodOrd es PK y FK, devolvemos CodOrd
                return CodOrd;
            }
        }
    }

    public int update(Ordenador_cientifico ordenadorCientifico) throws SQLException {
        int multiCpu = ordenadorCientifico.getMultiCpu();
        int CodOrd = ordenadorCientifico.getCodOrd();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE ordenador_cientifico SET multiCpu=%d WHERE CodOrd=%d",
            multiCpu, CodOrd
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM ordenador_cientifico WHERE CodOrd=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
