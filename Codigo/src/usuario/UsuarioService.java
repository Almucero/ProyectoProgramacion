package usuario;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioService {
    Connection conn;

    public UsuarioService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Usuario> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Usuario> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodUsu, Nombre, Ape1Usu, Ape2Usu, DNI, FecNac, DireccionCompleta, Correo, Contrasenia, EsAdministrador FROM usuario";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodUsu = querySet.getInt("CodUsu");
            String Nombre = querySet.getString("Nombre");
            String Ape1Usu = querySet.getString("Ape1Usu");
            String Ape2Usu = querySet.getString("Ape2Usu");
            String DNI = querySet.getString("DNI");
            Date FecNac = querySet.getDate("FecNac");
            String DireccionCompleta = querySet.getString("DireccionCompleta");
            String Correo = querySet.getString("Correo");
            String Contrasenia = querySet.getString("Contrasenia");
            int EsAdministrador = querySet.getInt("EsAdministrador");
            result.add(new Usuario(CodUsu, Nombre, Ape1Usu, Ape2Usu, DNI, FecNac, DireccionCompleta, Correo, Contrasenia, EsAdministrador));
        }
        statement.close();
        return result;
    }

    public Usuario requestById(long id) throws SQLException {
        Statement statement = null;
        Usuario result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodUsu, Nombre, Ape1Usu, Ape2Usu, DNI, FecNac, DireccionCompleta, Correo, Contrasenia, EsAdministrador FROM usuario WHERE CodUsu=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodUsu = querySet.getInt("CodUsu");
            String Nombre = querySet.getString("Nombre");
            String Ape1Usu = querySet.getString("Ape1Usu");
            String Ape2Usu = querySet.getString("Ape2Usu");
            String DNI = querySet.getString("DNI");
            Date FecNac = querySet.getDate("FecNac");
            String DireccionCompleta = querySet.getString("DireccionCompleta");
            String Correo = querySet.getString("Correo");
            String Contrasenia = querySet.getString("Contrasenia");
            int EsAdministrador = querySet.getInt("EsAdministrador");
            result = new Usuario(CodUsu, Nombre, Ape1Usu, Ape2Usu, DNI, FecNac, DireccionCompleta, Correo, Contrasenia, EsAdministrador);
        }
        statement.close();
        return result;
    }

    public long create(Usuario usuario) throws SQLException {
        String Nombre = usuario.getNombre();
        String Ape1Usu = usuario.getApe1Usu();
        String Ape2Usu = usuario.getApe2Usu();
        String DNI = usuario.getDNI();
        Date FecNac = usuario.getFecNac();
        String DireccionCompleta = usuario.getDireccionCompleta();
        String Correo = usuario.getCorreo();
        String Contrasenia = usuario.getContrasenia();
        int EsAdministrador = usuario.getEsAdministrador();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO usuario (Nombre, Ape1Usu, Ape2Usu, DNI, FecNac, DireccionCompleta, Correo, Contrasenia, EsAdministrador) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %d)",
            Nombre, Ape1Usu, Ape2Usu, DNI, FecNac != null ? FecNac.toString() : "NULL", DireccionCompleta, Correo, Contrasenia, EsAdministrador
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating usuario failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating usuario failed, no ID obtained.");
            }
        }
    }

    public int update(Usuario usuario) throws SQLException {
        int CodUsu = usuario.getCodUsu();
        String Nombre = usuario.getNombre();
        String Ape1Usu = usuario.getApe1Usu();
        String Ape2Usu = usuario.getApe2Usu();
        String DNI = usuario.getDNI();
        Date FecNac = usuario.getFecNac();
        String DireccionCompleta = usuario.getDireccionCompleta();
        String Correo = usuario.getCorreo();
        String Contrasenia = usuario.getContrasenia();
        int EsAdministrador = usuario.getEsAdministrador();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE usuario SET Nombre='%s', Ape1Usu='%s', Ape2Usu='%s', DNI='%s', FecNac='%s', DireccionCompleta='%s', Correo='%s', Contrasenia='%s', EsAdministrador=%d WHERE CodUsu=%d",
            Nombre, Ape1Usu, Ape2Usu, DNI, FecNac != null ? FecNac.toString() : "NULL", DireccionCompleta, Correo, Contrasenia, EsAdministrador, CodUsu
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM usuario WHERE CodUsu=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
