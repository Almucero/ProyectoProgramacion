package montador;

import java.sql.*;
import java.util.ArrayList;

public class MontadorService {
    Connection conn;

    public MontadorService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Montador> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Montador> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodMon, Nombre, Apellidos, DNI, FechaIni, Titulacion FROM montador";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodMon = querySet.getInt("CodMon");
            String Nombre = querySet.getString("Nombre");
            String Apellidos = querySet.getString("Apellidos");
            String DNIstr = querySet.getString("DNI");
            char DNI = DNIstr != null && DNIstr.length() > 0 ? DNIstr.charAt(0) : '\0';
            Date FechaIni = querySet.getDate("FechaIni");
            String Titulacion = querySet.getString("Titulacion");
            result.add(new Montador(CodMon, Nombre, Apellidos, DNI, FechaIni, Titulacion));
        }
        statement.close();
        return result;
    }

    public Montador requestById(long id) throws SQLException {
        Statement statement = null;
        Montador result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodMon, Nombre, Apellidos, DNI, FechaIni, Titulacion FROM montador WHERE CodMon=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodMon = querySet.getInt("CodMon");
            String Nombre = querySet.getString("Nombre");
            String Apellidos = querySet.getString("Apellidos");
            String DNIstr = querySet.getString("DNI");
            char DNI = DNIstr != null && DNIstr.length() > 0 ? DNIstr.charAt(0) : '\0';
            Date FechaIni = querySet.getDate("FechaIni");
            String Titulacion = querySet.getString("Titulacion");
            result = new Montador(CodMon, Nombre, Apellidos, DNI, FechaIni, Titulacion);
        }
        statement.close();
        return result;
    }

    public long create(Montador montador) throws SQLException {
        String Nombre = montador.getNombre();
        String Apellidos = montador.getApellidos();
        char DNI = montador.getDNI();
        Date FechaIni = montador.getFechaIni();
        String Titulacion = montador.getTitulacion();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO montador (Nombre, Apellidos, DNI, FechaIni, Titulacion) VALUES ('%s', '%s', '%s', '%s', '%s')",
            Nombre, Apellidos, String.valueOf(DNI), FechaIni != null ? FechaIni.toString() : "GETDATE()", Titulacion
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating montador failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating montador failed, no ID obtained.");
            }
        }
    }

    public int update(Montador montador) throws SQLException {
        int CodMon = montador.getCodMon();
        String Nombre = montador.getNombre();
        String Apellidos = montador.getApellidos();
        char DNI = montador.getDNI();
        Date FechaIni = montador.getFechaIni();
        String Titulacion = montador.getTitulacion();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE montador SET Nombre='%s', Apellidos='%s', DNI='%s', FechaIni='%s', Titulacion='%s' WHERE CodMon=%d",
            Nombre, Apellidos, String.valueOf(DNI), FechaIni != null ? FechaIni.toString() : "GETDATE()", Titulacion, CodMon
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM montador WHERE CodMon=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
