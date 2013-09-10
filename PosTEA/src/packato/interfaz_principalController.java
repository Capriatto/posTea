/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package packato;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import static packato.dataBase.getConexion;

/**
 *
 * @author Juan_Sebastian
 */
public class interfaz_principalController implements Initializable {

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
            Logger.getLogger(interfaz_principalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void guardarNota() {
        if (nombreNota != null) {
            String nombreDeNota = nombreNota.getText();
            int nombreCategoria = comboCategoria.getSelectionModel().getSelectedIndex();
            rbAlta.setToggleGroup(prioridad);
            rbMedia.setToggleGroup(prioridad);
            rbBaja.setToggleGroup(prioridad);
            String toggle = prioridad.selectedToggleProperty().toString();
            int prioridadSelected;
            if (toggle.contains("rbAlta")) {
                prioridadSelected= 1;
            } else if (toggle.contains("rbMedia")) {
                 prioridadSelected= 2;
            } else {
                 prioridadSelected= 3;
            }
            String contenido= txtContenido.getText();
            System.out.println("contenido = " + contenido);
        }
 }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCombo();
    }
}
