package ordenador.tipoOrdenador.ordenador_embebido;

import java.sql.*;
import java.util.ArrayList;

public class Ordenador_embebidoService {
    Connection conn;

    public Ordenador_embebidoService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Ordenador_embebido> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Ordenador_embebido> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT SisTiemReal, CodOrd FROM ordenador_embebido";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int SisTiemReal = querySet.getInt("SisTiemReal");
            int CodOrd = querySet.getInt("CodOrd");
            result.add(new Ordenador_embebido(SisTiemReal, CodOrd));
        }
        statement.close();
        return result;
    }

    public Ordenador_embebido requestById(long id) throws SQLException {
        Statement statement = null;
        Ordenador_embebido result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT SisTiemReal, CodOrd FROM ordenador_embebido WHERE CodOrd=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int SisTiemReal = querySet.getInt("SisTiemReal");
            int CodOrd = querySet.getInt("CodOrd");
            result = new Ordenador_embebido(SisTiemReal, CodOrd);
        }
        statement.close();
        return result;
    }

    public long create(Ordenador_embebido ordenadorEmbebido) throws SQLException {
        int SisTiemReal = ordenadorEmbebido.getSisTiemReal();
        int CodOrd = ordenadorEmbebido.getCodOrd();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO ordenador_embebido (SisTiemReal, CodOrd) VALUES (%d, %d)",
            SisTiemReal, CodOrd
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating ordenador_embebido failed, no rows affected.");
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

    public int update(Ordenador_embebido ordenadorEmbebido) throws SQLException {
        int SisTiemReal = ordenadorEmbebido.getSisTiemReal();
        int CodOrd = ordenadorEmbebido.getCodOrd();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE ordenador_embebido SET SisTiemReal=%d WHERE CodOrd=%d",
            SisTiemReal, CodOrd
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM ordenador_embebido WHERE CodOrd=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
