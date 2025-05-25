package ordenador.tipoOrdenador.ordenador_workstation;

import java.sql.*;
import java.util.ArrayList;

public class Ordenador_workstationService {
    Connection conn;

    public Ordenador_workstationService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Ordenador_workstation> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Ordenador_workstation> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT render, certificado, CodOrd FROM ordenador_workstation";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int render = querySet.getInt("render");
            String certificado = querySet.getString("certificado");
            int CodOrd = querySet.getInt("CodOrd");
            result.add(new Ordenador_workstation(render, certificado, CodOrd));
        }
        statement.close();
        return result;
    }

    public Ordenador_workstation requestById(long id) throws SQLException {
        Statement statement = null;
        Ordenador_workstation result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT render, certificado, CodOrd FROM ordenador_workstation WHERE CodOrd=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int render = querySet.getInt("render");
            String certificado = querySet.getString("certificado");
            int CodOrd = querySet.getInt("CodOrd");
            result = new Ordenador_workstation(render, certificado, CodOrd);
        }
        statement.close();
        return result;
    }

    public long create(Ordenador_workstation ordenadorWorkstation) throws SQLException {
        int render = ordenadorWorkstation.getRender();
        String certificado = ordenadorWorkstation.getCertificado();
        int CodOrd = ordenadorWorkstation.getCodOrd();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO ordenador_workstation (render, certificado, CodOrd) VALUES (%d, %s, %d)",
            render, certificado != null ? "'" + certificado + "'" : "NULL", CodOrd
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating ordenador_workstation failed, no rows affected.");
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

    public int update(Ordenador_workstation ordenadorWorkstation) throws SQLException {
        int render = ordenadorWorkstation.getRender();
        String certificado = ordenadorWorkstation.getCertificado();
        int CodOrd = ordenadorWorkstation.getCodOrd();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE ordenador_workstation SET render=%d, certificado=%s WHERE CodOrd=%d",
            render, certificado != null ? "'" + certificado + "'" : "NULL", CodOrd
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM ordenador_workstation WHERE CodOrd=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
