import db.DB;
import db.DBIntegrityException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DB.getConnection();

            st = conn.prepareStatement(
                    "DELETE FROM department "
                    + "WHERE " +
                    "Id = ?");

            st.setInt(1, 2);

            int rowsAffected = st.executeUpdate();

            System.out.println("Done! Rows affected: " + rowsAffected);
        } catch (SQLException ex) {
            throw new DBIntegrityException(ex.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}