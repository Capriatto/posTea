/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package packato;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import notas.notaController;

/**
 * FXML Controller class
 *
 * @author Juan_Sebastian
 */
public class Interfaz_principalController implements Initializable {
 ActionEvent evento;
 private Stage stage;
  String nick;
 String contraseña;
 int id_usuario;
 @FXML
 TextField txtNombre;
 @FXML
 TextField txtContraseña;
 @FXML  
 Button btnEntrar;
 @FXML
  Label lblError;


    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException, SQLException {
        lblError.setText("");
        evento = event;
         nick= txtNombre.getText().trim();
         contraseña=txtContraseña.getText().trim();
        if (!nick.isEmpty() || !contraseña.isEmpty()) {

            if (login(nick,contraseña)) {
               
                System.out.println("Se encontraron los datos.");
                cambiarInterface("/notas/nota.fxml");
                consultarId();
                System.setProperty("key", String.valueOf(id_usuario));
              } else {
                Toolkit.getDefaultToolkit().beep();
               lblError.setText("Error al entrar, no existe un usuario con estos datos.");
            }

        }
      
    }
     public void cambiarInterface(String fxml) throws IOException {
        Node node = (Node) evento.getSource();
        stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

     public void consultarId() {
     try {
        Connection conexion= dataBase.getConexion();
         PreparedStatement pstm=conexion.prepareStatement("select id from usuario where nick=? limit 1");
         pstm.setString(1, nick);
        ResultSet rs=  pstm.executeQuery();
        id_usuario=rs.getInt(1);
     } catch (SQLException ex) {
         Logger.getLogger(Interfaz_principalController.class.getName()).log(Level.SEVERE, null, ex);
     }
   }
     
    public boolean login(String nick, String contraseña) {
        try {
            Connection conexion = dataBase.getConexion();
            PreparedStatement instruccion = conexion.prepareStatement("SELECT * FROM usuario WHERE nick= ?  AND contraseña= ?");
            instruccion.setString(1, nick);
            instruccion.setString(2, contraseña);
            ResultSet resultados = instruccion.executeQuery();
            
            
            if (resultados.next()) {

                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(notaController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
