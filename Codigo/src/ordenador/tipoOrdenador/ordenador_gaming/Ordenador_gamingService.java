package ordenador.tipoOrdenador.ordenador_gaming;

import java.sql.*;
import java.util.ArrayList;

public class Ordenador_gamingService {
    Connection conn;

    public Ordenador_gamingService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Ordenador_gaming> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Ordenador_gaming> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT RGB, OC, CodOrd FROM ordenador_gaming";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int RGB = querySet.getInt("RGB");
            int OC = querySet.getInt("OC");
            int CodOrd = querySet.getInt("CodOrd");
            result.add(new Ordenador_gaming(RGB, OC, CodOrd));
        }
        statement.close();
        return result;
    }

    public Ordenador_gaming requestById(long id) throws SQLException {
        Statement statement = null;
        Ordenador_gaming result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT RGB, OC, CodOrd FROM ordenador_gaming WHERE CodOrd=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int RGB = querySet.getInt("RGB");
            int OC = querySet.getInt("OC");
            int CodOrd = querySet.getInt("CodOrd");
            result = new Ordenador_gaming(RGB, OC, CodOrd);
        }
        statement.close();
        return result;
    }

    public long create(Ordenador_gaming ordenadorGaming) throws SQLException {
        int RGB = ordenadorGaming.getRGB();
        int OC = ordenadorGaming.getOC();
        int CodOrd = ordenadorGaming.getCodOrd();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO ordenador_gaming (RGB, OC, CodOrd) VALUES (%d, %d, %d)",
            RGB, OC, CodOrd
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating ordenador_gaming failed, no rows affected.");
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

    public int update(Ordenador_gaming ordenadorGaming) throws SQLException {
        int RGB = ordenadorGaming.getRGB();
        int OC = ordenadorGaming.getOC();
        int CodOrd = ordenadorGaming.getCodOrd();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE ordenador_gaming SET RGB=%d, OC=%d WHERE CodOrd=%d",
            RGB, OC, CodOrd
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM ordenador_gaming WHERE CodOrd=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
