package org.harlinpalacios.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.harlinpalacios.bean.Email;
import org.harlinpalacios.db.Conexion;
import org.harlinpalacios.system.Principal;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuEmailProveedorController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones{AGREGAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Email> listarEmail;
    
    @FXML private Button btnRegresar;
    
    @FXML private TextField txtCodigoE;
    @FXML private TextField txtEmailP;
    @FXML private TextField txtDescripcionP;
    
    @FXML private TableView tblEmail;
    @FXML private TableColumn colCodigoE;
    @FXML private TableColumn colEmailP;
    @FXML private TableColumn colDescripcionP;
    
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    

    public void cargarDatos(){
        tblEmail.setItems(getEmail());
        colCodigoE.setCellValueFactory(new PropertyValueFactory<Email, Integer>("codigoEmailProveedor"));
        colEmailP.setCellValueFactory(new PropertyValueFactory<Email, Integer>("emailProveedor"));
        colDescripcionP.setCellValueFactory(new PropertyValueFactory<Email, Integer>("descripcion"));
    }
    
    public void selecionarElementos(){
        txtCodigoE.setText(String.valueOf(((Email)tblEmail.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor()));
        txtEmailP.setText(((Email)tblEmail.getSelectionModel().getSelectedItem()).getEmailProveedor());
        txtDescripcionP.setText(((Email)tblEmail.getSelectionModel().getSelectedItem()).getDescripcion());
    }
    
    public ObservableList<Email> getEmail (){
        // Variable lista                       
        ArrayList<Email> lista = new ArrayList<>();
        // amnejo de excepcion
        // variable que almace la linea de conexion
        try{                             
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmialPro()}");
            ResultSet resultado = procedimiento.executeQuery();
            // Funciona por medio del resultado y que avanze de fila en fila
            // La lista de tipo Arraylist donde recivimos nuestros dato
            while(resultado.next()){
                lista.add(new Email (resultado.getInt("codigoEmailProveedor"),
                                        resultado.getString("emailProveedor"),
                                        resultado.getString("descripcion")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    return listarEmail = FXCollections.observableArrayList(lista);
    }
    
    public void agregar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/harlinpalacios/imagenes/Guardar.png"));
                imgEliminar.setImage(new Image("/org/harlinpalacios/imagenes/Cancelar.png")); 
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/harlinpalacios/imagenes/Agregar Cliente.png"));
                imgEliminar.setImage(new Image("/org/harlinpalacios/imagenes/Eliminar Cliente.png"));         
                tipoDeOperaciones = operaciones.NINGUNO;
                break;                
        }
        
        
    }
    public void guardar(){
        Email registro = new Email();
        registro.setCodigoEmailProveedor(Integer.parseInt(txtCodigoE.getText()));
        registro.setEmailProveedor(txtEmailP.getText());
        registro.setDescripcion(txtDescripcionP.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmailPro(?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.execute();
            listarEmail.add(registro);
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("org/harlinpalacios/imagenes/Agregar.png"));
                imgEliminar.setImage(new Image("org/harlinpalacios/imagenes/Eliminar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;   
                
            default:
                if(tblEmail.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimana el registro", 
                            "Eliminar Email", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmailPro(?)}");
                            procedimiento.setInt(1, ((Email)tblEmail.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor());
                            procedimiento.execute();
                            listarEmail.remove(tblEmail.getSelectionModel().getSelectedItem());
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Elemento");
                
        }
        
    }
    
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblEmail.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/harlinpalacios/imagenes/Actualizar Clientes.png"));
                    imgReporte.setImage(new Image("/org/harlinpalacios/imagenes/Cancelar.png"));
                    activarControles();
                    txtCodigoE.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                    
                }else 
                    JOptionPane.showMessageDialog(null, "Debe seleccionar algun elemento");
                break;
            case ACTUALIZAR:
                    actualizar();
                    desactivarControles();
                    limpiarControles();
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnAgregar.setDisable(false);
                    btnEliminar.setDisable(false);
                    imgEditar.setImage(new Image ("/org/harlinpalacios/imagenes/Editar.png"));
                    imgReporte.setImage(new Image("/org/harlinpalacios/imagenes/Reporte clientes.png"));
                    tipoDeOperaciones = operaciones.NINGUNO;
                    cargarDatos();
                break;
        }
    }

    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmailPro(?, ?, ?)}");
            Email registro = (Email)tblEmail.getSelectionModel().getSelectedItem();
            registro.setEmailProveedor(txtEmailP.getText());
            registro.setDescripcion(txtDescripcionP.getText());
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.execute();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void desactivarControles(){
        txtCodigoE.setEditable(false);
        txtEmailP.setEditable(false);
        txtDescripcionP.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoE.setEditable(true);
        txtEmailP.setEditable(true);
        txtDescripcionP.setEditable(true);
    }
    
    ///////////////////
    // limpiar el texto//
    public void limpiarControles(){
        txtCodigoE.clear();
        txtEmailP.clear();
        txtDescripcionP.clear();
    }



    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void regresar (ActionEvent event){
        if (event.getSource() == btnRegresar){
        escenarioPrincipal.menuPrincipalView();
        }
    }
}
