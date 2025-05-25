package ordenador;

import java.sql.*;
import java.util.ArrayList;

public class OrdenadorService {
    Connection conn;

    public OrdenadorService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Ordenador> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Ordenador> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodOrd, Nombre, Precio, Proposito, Stock, SO, CodCha, CodPB, CodAlmPrincipal FROM ordenador";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodOrd = querySet.getInt("CodOrd");
            String Nombre = querySet.getString("Nombre");
            float Precio = querySet.getFloat("Precio");
            String Proposito = querySet.getString("Proposito");
            int Stock = querySet.getInt("Stock");
            String SO = querySet.getString("SO");
            int CodCha = querySet.getInt("CodCha");
            int CodPB = querySet.getInt("CodPB");
            int CodAlmPrincipal = querySet.getInt("CodAlmPrincipal");
            result.add(new Ordenador(CodOrd, Nombre, Precio, Proposito, Stock, SO, CodCha, CodPB, CodAlmPrincipal));
        }
        statement.close();
        return result;
    }

    public Ordenador requestById(long id) throws SQLException {
        Statement statement = null;
        Ordenador result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodOrd, Nombre, Precio, Proposito, Stock, SO, CodCha, CodPB, CodAlmPrincipal FROM ordenador WHERE CodOrd=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodOrd = querySet.getInt("CodOrd");
            String Nombre = querySet.getString("Nombre");
            float Precio = querySet.getFloat("Precio");
            String Proposito = querySet.getString("Proposito");
            int Stock = querySet.getInt("Stock");
            String SO = querySet.getString("SO");
            int CodCha = querySet.getInt("CodCha");
            int CodPB = querySet.getInt("CodPB");
            int CodAlmPrincipal = querySet.getInt("CodAlmPrincipal");
            result = new Ordenador(CodOrd, Nombre, Precio, Proposito, Stock, SO, CodCha, CodPB, CodAlmPrincipal);
        }
        statement.close();
        return result;
    }

    public long create(Ordenador ordenador) throws SQLException {
        String Nombre = ordenador.getNombre();
        float Precio = ordenador.getPrecio();
        String Proposito = ordenador.getProposito();
        int Stock = ordenador.getStock();
        String SO = ordenador.getSO();
        int CodCha = ordenador.getCodCha();
        int CodPB = ordenador.getCodPB();
        int CodAlmPrincipal = ordenador.getCodAlmPrincipal();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO ordenador (Nombre, Precio, Proposito, Stock, SO, CodCha, CodPB, CodAlmPrincipal) VALUES ('%s', %.2f, '%s', %d, '%s', %d, %d, %d)",
            Nombre, Precio, Proposito, Stock, SO, CodCha, CodPB, CodAlmPrincipal
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating ordenador failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating ordenador failed, no ID obtained.");
            }
        }
    }

    public int update(Ordenador ordenador) throws SQLException {
        int CodOrd = ordenador.getCodOrd();
        String Nombre = ordenador.getNombre();
        float Precio = ordenador.getPrecio();
        String Proposito = ordenador.getProposito();
        int Stock = ordenador.getStock();
        String SO = ordenador.getSO();
        int CodCha = ordenador.getCodCha();
        int CodPB = ordenador.getCodPB();
        int CodAlmPrincipal = ordenador.getCodAlmPrincipal();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE ordenador SET Nombre='%s', Precio=%.2f, Proposito='%s', Stock=%d, SO='%s', CodCha=%d, CodPB=%d, CodAlmPrincipal=%d WHERE CodOrd=%d",
            Nombre, Precio, Proposito, Stock, SO, CodCha, CodPB, CodAlmPrincipal, CodOrd
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM ordenador WHERE CodOrd=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
