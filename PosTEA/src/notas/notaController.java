/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package notas;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import packato.dataBase;
import static packato.dataBase.getConexion;

/**
 *
 * @author Juan_Sebastian
 */
public class notaController implements Initializable {

    int nickRecibido;
    @FXML
    private Label label;
    @FXML
    ComboBox comboCategoria;
    @FXML
    TextField nombreNota;
    @FXML
    ToggleGroup prioridad;
    @FXML
    RadioButton rbAlta;
    @FXML
    RadioButton rbMedia;
    @FXML
    RadioButton rbBaja;
    @FXML
    TextArea txtContenido;
    @FXML
    TextField txtBuscar;
    @FXML
    ListView lista;

    public void cargarCombo() {
        try {// combo categorias
            dataBase.getConexion();
            Statement statement = getConexion().createStatement();
            ResultSet rs;
            rs = statement.executeQuery("select nombre from categoria");
            while (rs.next()) {
                comboCategoria.getItems().add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(notaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void guardarNota() throws SQLException {
        if (nombreNota != null) {
            String nombreDeNota = nombreNota.getText();
            int nombreCategoria = (comboCategoria.getSelectionModel().getSelectedIndex() + 1);
            rbAlta.setToggleGroup(prioridad);
            rbMedia.setToggleGroup(prioridad);
            rbBaja.setToggleGroup(prioridad);
            String toggle = prioridad.selectedToggleProperty().toString();
            int prioridadSelected;
            if (toggle.contains("rbAlta")) {
                prioridadSelected = 1;
            } else if (toggle.contains("rbMedia")) {
                prioridadSelected = 2;
            } else {
                prioridadSelected = 3;
            }
            String contenido = txtContenido.getText();
            nickRecibido = Integer.parseInt(System.getProperty("key"));
            insert(nombreDeNota, nombreCategoria, prioridadSelected, contenido, nickRecibido);

        }
    }

    public void insert(String nombre, int id_categoria, int id_prioridad, String contenido, int id_usuario) throws SQLException {
        dataBase.getConexion();
        String sentencia = "insert into nota (nombre, id_categoria, id_prioridad, contenido, id_usuario) values (?,?,?,?,?) ";
        PreparedStatement pstmt = getConexion().prepareStatement(sentencia);
        pstmt.setString(1, nombre);
        pstmt.setInt(2, id_categoria);
        pstmt.setInt(3, id_prioridad);
        pstmt.setString(4, contenido);
        pstmt.setInt(5, id_usuario);
        pstmt.executeUpdate();

    }

    @FXML
    public void keyTypedSearch() throws SQLException {
        try {
            ListView<String> list = new ListView<String>();

ObservableList<String> items = null ;
items =FXCollections.observableArrayList ( );
            String buscar = txtBuscar.getText();
            String sql = "SELECT nota.nombre FROM nota, usuario  where  usuario.id="+2+" and nombre like"+"'%"+buscar+"%'" ;
            Statement pst = getConexion().createStatement();
           ResultSet resul = pst.executeQuery(sql);
//            lista.getItems().clear();
            while (resul.next()) {
             items.add(resul.getString(1));
           }
            lista.setItems(items);
           
        } catch (SQLException x) {
            Logger.getLogger(notaController.class.getName()).log(Level.SEVERE, null, x);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCombo();
    }
}
