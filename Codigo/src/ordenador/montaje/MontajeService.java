package ordenador.montaje;

import java.sql.*;
import java.util.ArrayList;

public class MontajeService {
    Connection conn;

    public MontajeService(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Montaje> requestAll() throws SQLException {
        Statement statement = null;
        ArrayList<Montaje> result = new ArrayList<>();
        statement = this.conn.createStatement();
        String sql = "SELECT CodMontaje, Fecha, Detalles, Precio, CodOrd, CodMon FROM montaje";
        ResultSet querySet = statement.executeQuery(sql);
        while (querySet.next()) {
            int CodMontaje = querySet.getInt("CodMontaje");
            Date Fecha = querySet.getDate("Fecha");
            String Detalles = querySet.getString("Detalles");
            float Precio = querySet.getFloat("Precio");
            int CodOrd = querySet.getInt("CodOrd");
            int CodMon = querySet.getInt("CodMon");
            result.add(new Montaje(CodMontaje, Fecha, Detalles, Precio, CodOrd, CodMon));
        }
        statement.close();
        return result;
    }

    public Montaje requestById(long id) throws SQLException {
        Statement statement = null;
        Montaje result = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"SELECT CodMontaje, Fecha, Detalles, Precio, CodOrd, CodMon FROM montaje WHERE CodMontaje=%d", id);
        ResultSet querySet = statement.executeQuery(sql);
        if (querySet.next()) {
            int CodMontaje = querySet.getInt("CodMontaje");
            Date Fecha = querySet.getDate("Fecha");
            String Detalles = querySet.getString("Detalles");
            float Precio = querySet.getFloat("Precio");
            int CodOrd = querySet.getInt("CodOrd");
            int CodMon = querySet.getInt("CodMon");
            result = new Montaje(CodMontaje, Fecha, Detalles, Precio, CodOrd, CodMon);
        }
        statement.close();
        return result;
    }

    public long create(Montaje montaje) throws SQLException {
        Date Fecha = montaje.getFecha();
        String Detalles = montaje.getDetalles();
        float Precio = montaje.getPrecio();
        int CodOrd = montaje.getCodOrd();
        int CodMon = montaje.getCodMon();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "INSERT INTO montaje (Fecha, Detalles, Precio, CodOrd, CodMon) VALUES ('%s', '%s', %.2f, %d, %d)",
            Fecha != null ? Fecha.toString() : "GETDATE()", Detalles, Precio, CodOrd, CodMon
        );
        int affectedRows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating montaje failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                statement.close();
                return id;
            } else {
                statement.close();
                throw new SQLException("Creating montaje failed, no ID obtained.");
            }
        }
    }

    public int update(Montaje montaje) throws SQLException {
        int CodMontaje = montaje.getCodMontaje();
        Date Fecha = montaje.getFecha();
        String Detalles = montaje.getDetalles();
        float Precio = montaje.getPrecio();
        int CodOrd = montaje.getCodOrd();
        int CodMon = montaje.getCodMon();

        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,
            "UPDATE montaje SET Fecha='%s', Detalles='%s', Precio=%.2f, CodOrd=%d, CodMon=%d WHERE CodMontaje=%d",
            Fecha != null ? Fecha.toString() : "GETDATE()", Detalles, Precio, CodOrd, CodMon, CodMontaje
        );
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format(java.util.Locale.US,"DELETE FROM montaje WHERE CodMontaje=%d", id);
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
