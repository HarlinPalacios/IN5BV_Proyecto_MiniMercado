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
import org.harlinpalacios.bean.TelefonoPro;
import org.harlinpalacios.db.Conexion;
import org.harlinpalacios.system.Principal;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuTelefonoProController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones{AGREGAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<TelefonoPro> listarTelefono;
    
    @FXML private Button btnRegresar;
    
    @FXML private TextField txtCodigoT;
    @FXML private TextField txtNumeroP;
    @FXML private TextField txtNumeroS;
    @FXML private TextField txtObservacion;
    
    @FXML private TableView tblTelefono;
    @FXML private TableColumn colCodigoT;
    @FXML private TableColumn colNumeroP;
    @FXML private TableColumn colNuumeroS;
    @FXML private TableColumn colObservacion;
    
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
        tblTelefono.setItems(getTelefonoPro());
        colCodigoT.setCellValueFactory(new PropertyValueFactory<TelefonoPro, Integer>("codigoTelefonoProveedor"));
        colNumeroP.setCellValueFactory(new PropertyValueFactory<TelefonoPro, Integer>("numeroPrincipal"));
        colNuumeroS.setCellValueFactory(new PropertyValueFactory<TelefonoPro, Integer>("numeroSecundario"));
        colObservacion.setCellValueFactory(new PropertyValueFactory<TelefonoPro, Integer>("observaciones"));
    }
    
    public void selecionarElementos(){
        txtCodigoT.setText(String.valueOf(((TelefonoPro)tblTelefono.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor()));
        txtNumeroP.setText(((TelefonoPro)tblTelefono.getSelectionModel().getSelectedItem()).getNumeroPrincipal());
        txtNumeroS.setText(((TelefonoPro)tblTelefono.getSelectionModel().getSelectedItem()).getNumeroSecundario());
        txtObservacion.setText(((TelefonoPro)tblTelefono.getSelectionModel().getSelectedItem()).getObservaciones());
    }
    
    public ObservableList<TelefonoPro> getTelefonoPro (){
        // Variable lista                       
        ArrayList<TelefonoPro> lista = new ArrayList<>();
        // amnejo de excepcion
        // variable que almace la linea de conexion
        try{                             
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTelefonoPro()}");
            ResultSet resultado = procedimiento.executeQuery();
            // Funciona por medio del resultado y que avanze de fila en fila
            // La lista de tipo Arraylist donde recivimos nuestros dato
            while(resultado.next()){
                lista.add(new TelefonoPro (resultado.getInt("codigoTelefonoProveedor"),
                                        resultado.getString("numeroPrincipal"),
                                        resultado.getString("numeroSecundario"),
                                        resultado.getString("observaciones")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    return listarTelefono = FXCollections.observableArrayList(lista);
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
        TelefonoPro registro = new TelefonoPro();
        registro.setCodigoTelefonoProveedor(Integer.parseInt(txtCodigoT.getText()));
        registro.setNumeroPrincipal(txtNumeroP.getText());
        registro.setNumeroSecundario(txtNumeroS.getText());
        registro.setObservaciones(txtObservacion.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTelefonoPro(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.execute();
            listarTelefono.add(registro);
            
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
                if(tblTelefono.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimana el registro", 
                            "Eliminar Cargos", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTelefonoPro(?)}");
                            procedimiento.setInt(1, ((TelefonoPro)tblTelefono.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor());
                            procedimiento.execute();
                            listarTelefono.remove(tblTelefono.getSelectionModel().getSelectedItem());
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
                if(tblTelefono.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/harlinpalacios/imagenes/Actualizar Clientes.png"));
                    imgReporte.setImage(new Image("/org/harlinpalacios/imagenes/Cancelar.png"));
                    activarControles();
                    txtCodigoT.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarTelefonoPro(?, ?, ?, ?)}");
            TelefonoPro registro = (TelefonoPro)tblTelefono.getSelectionModel().getSelectedItem();
            registro.setNumeroPrincipal(txtNumeroP.getText());
            registro.setNumeroSecundario(txtNumeroS.getText());
            registro.setObservaciones(txtObservacion.getText());
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.execute();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void desactivarControles(){
        txtCodigoT.setEditable(false);
        txtNumeroP.setEditable(false);
        txtNumeroS.setEditable(false);
        txtObservacion.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoT.setEditable(true);
        txtNumeroP.setEditable(true);
        txtNumeroS.setEditable(true);
        txtObservacion.setEditable(true);
    }
    
    ///////////////////
    // limpiar el texto//
    public void limpiarControles(){
        txtCodigoT.clear();
        txtNumeroP.clear();
        txtNumeroS.clear();
        txtObservacion.clear();
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
