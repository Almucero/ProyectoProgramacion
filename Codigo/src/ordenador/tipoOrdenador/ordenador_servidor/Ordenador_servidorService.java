package ordenador.tipoOrdenador.ordenador_servidor;

import java.sql.*;
import java.util.ArrayList;

public class Ordenador_servidorService {
    Connection conn;

    public Ordenador_servidorService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Ordenador_servidor> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Ordenador_servidor> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT escalabilidad, CodOrd FROM ordenador_servidor";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            String escalabilidad = querySet.getString("escalabilidad");
            int CodOrd = querySet.getInt("CodOrd");
            result.add(new Ordenador_servidor(escalabilidad, CodOrd));
        }
        statement.close();
        return result;
    }

    public Ordenador_servidor requestById(long id) throws SQLException {
        Statement statement = null;
        Ordenador_servidor result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT escalabilidad, CodOrd FROM ordenador_servidor WHERE CodOrd=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            String escalabilidad = querySet.getString("escalabilidad");
            int CodOrd = querySet.getInt("CodOrd");
            result = new Ordenador_servidor(escalabilidad, CodOrd);
        }
        statement.close();
        return result;
    }

    public long create(Ordenador_servidor ordenadorServidor) throws SQLException {
        String escalabilidad = ordenadorServidor.getEscalabilidad();
        int CodOrd = ordenadorServidor.getCodOrd();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO ordenador_servidor (escalabilidad, CodOrd) VALUES ('%s', %d)",
            escalabilidad, CodOrd
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating ordenador_servidor failed, no rows affected.");
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

    public int update(Ordenador_servidor ordenadorServidor) throws SQLException {
        String escalabilidad = ordenadorServidor.getEscalabilidad();
        int CodOrd = ordenadorServidor.getCodOrd();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE ordenador_servidor SET escalabilidad='%s' WHERE CodOrd=%d",
            escalabilidad, CodOrd
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM ordenador_servidor WHERE CodOrd=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
