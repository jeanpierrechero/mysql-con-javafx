package basededatos;

import static basededatos.JavaApplication1.getAlumnos;
import static basededatos.JavaApplication1.grabarAlumno;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author jean pierre
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label1;
    
    @FXML
    private TextField tfl_nombre, tfl_apellido, tfl_id;  
    
    @FXML
    private TableColumn tv_nombre, tv_apellido;
    
        
    @FXML
    private void metodoGrabar(ActionEvent event) {
               
        try {
            String user = "root";
            String pass = "";
            String streamm = "jdbc:mysql://localhost:3306/alumno";
            
            Connection c = DriverManager.getConnection(streamm, user, pass);
            Statement st = c.createStatement();
            String nombre = tfl_nombre.getText();
            String apellido = tfl_apellido.getText();
            Alumno a1 = new Alumno(nombre,apellido);
            grabarAlumno(c,a1);
            System.out.println("Se guardo correctamente");
            
            getAlumnos(c).stream().forEach(System.out::println);
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }  
    
    @FXML
    private void metodoBuscar(ActionEvent event) throws SQLException{
    
        
            String user = "root";
            String pass = "";
            String streamm = "jdbc:mysql://localhost:3306/alumno";
            
            Connection c = DriverManager.getConnection(streamm, user, pass);
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(
                    "select id, nombre, apellido from alumnos where id = "
                            +tfl_id.getText());
            if(rs.next()){
                tfl_nombre.setText(rs.getString("nombre"));
                tfl_apellido.setText(rs.getString("apellido"));
            
            }
            else{
                label1.setText("No existe");
            }
            
                 
    }
    
    @FXML
    private void metodoNuevo(ActionEvent event){
    
        tfl_nombre.setText("");
        tfl_apellido.setText("");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Todo
    }    
    
}
