package ordenador.tipoOrdenador.ordenador_PCOfinica;

import java.sql.*;
import java.util.ArrayList;

public class Ordenador_PCOficionaService {
    Connection conn;

    public Ordenador_PCOficionaService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Ordenador_PCOficina> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Ordenador_PCOficina> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT MainSoft, CodOrd FROM ordenador_PCOficina";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            String MainSoft = querySet.getString("MainSoft");
            int CodOrd = querySet.getInt("CodOrd");
            result.add(new Ordenador_PCOficina(MainSoft, CodOrd));
        }
        statement.close();
        return result;
    }

    public Ordenador_PCOficina requestById(long id) throws SQLException {
        Statement statement = null;
        Ordenador_PCOficina result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT MainSoft, CodOrd FROM ordenador_PCOficina WHERE CodOrd=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            String MainSoft = querySet.getString("MainSoft");
            int CodOrd = querySet.getInt("CodOrd");
            result = new Ordenador_PCOficina(MainSoft, CodOrd);
        }
        statement.close();
        return result;
    }

    public long create(Ordenador_PCOficina ordenadorPCOficina) throws SQLException {
        String MainSoft = ordenadorPCOficina.getMainSoft();
        int CodOrd = ordenadorPCOficina.getCodOrd();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO ordenador_PCOficina (MainSoft, CodOrd) VALUES ('%s', %d)",
            MainSoft, CodOrd
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating ordenador_PCOficina failed, no rows affected.");
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

    public int update(Ordenador_PCOficina ordenadorPCOficina) throws SQLException {
        String MainSoft = ordenadorPCOficina.getMainSoft();
        int CodOrd = ordenadorPCOficina.getCodOrd();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE ordenador_PCOficina SET MainSoft='%s' WHERE CodOrd=%d",
            MainSoft, CodOrd
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM ordenador_PCOficina WHERE CodOrd=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
