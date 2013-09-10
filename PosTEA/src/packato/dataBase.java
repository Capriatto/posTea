/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package packato;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class dataBase {

    private static Connection connection = null;

    public static Connection getConexion() throws SQLException {
        if (dataBase.connection == null) {
            construyendoConexion();
        }
        return dataBase.connection;
    }

    private static void construyendoConexion() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            dataBase.connection = DriverManager.getConnection("jdbc:sqlite:src\\resources\\PosTea");
            System.out.println("Database connection was successful!");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException(contruyendoConexion)  : " + e.getMessage());
            System.gc();
        } catch (SQLException e) {
            System.out.println("SQLException(contruyendoConexion) : " + e.getMessage());
            System.gc();
            JOptionPane.showMessageDialog(null, e.getMessage(), "No fue Posible Conectar con la Base de Datos", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (Exception e) {
            System.out.println(" Exception General (contruyendoConexion) : " + e.getMessage());
            System.gc();
        }

    }

    public static void liberarConexionS(Connection conex) {
        try {
            conex.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static String consulta(String query) throws SQLException {
        Statement statement = getConexion().createStatement();
        ResultSet rs;
        rs = statement.executeQuery(query);
        System.out.println("" + rs.getString(1));
      return query;
    }

    public static void liberaConexion(Connection conexion) {
        try {
            if (null != conexion) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void liberarStatement(PreparedStatement p) {
        try {
            if (null != p) {
                p.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}