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
import org.harlinpalacios.bean.TipoProducto;
import org.harlinpalacios.db.Conexion;
import org.harlinpalacios.system.Principal;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuTipoProductoContoller implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones{AGREGAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<TipoProducto> listarTipoProducto;
    
    @FXML private Button btnRegresar;
    @FXML private TextField txtCodigoTP;
    @FXML private TextField txtDescripcionP;
    
    @FXML private TableView tblTipoProducto;
    @FXML private TableColumn colCodigoTP;
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
        tblTipoProducto.setItems(getCodigoTipoPro());
        colCodigoTP.setCellValueFactory(new PropertyValueFactory<TipoProducto, Integer>("codigoTipoPro"));
        colDescripcionP.setCellValueFactory(new PropertyValueFactory<TipoProducto, Integer>("descripcion"));
    }
    
    public void selecionarElementos(){
        txtCodigoTP.setText(String.valueOf(((TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoPro()));
        txtDescripcionP.setText(((TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem()).getDescripcion());
    }
    
    public ObservableList<TipoProducto> getCodigoTipoPro (){
        // Variable lista                       
        ArrayList<TipoProducto> lista = new ArrayList<>();
        // amnejo de excepcion
        // variable que almace la linea de conexion
        try{                             
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            // Funciona por medio del resultado y que avanze de fila en fila
            // La lista de tipo Arraylist donde recivimos nuestros dato
            while(resultado.next()){
                lista.add(new TipoProducto (resultado.getInt("codigoTipoPro"),
                                        resultado.getString("descripcion")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    return listarTipoProducto = FXCollections.observableArrayList(lista);
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
        TipoProducto registro = new TipoProducto();
        registro.setCodigoTipoPro(Integer.parseInt(txtCodigoTP.getText()));
        registro.setDescripcion(txtDescripcionP.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoProducto(?, ?)}");
            procedimiento.setInt(1, registro.getCodigoTipoPro());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
            listarTipoProducto.add(registro);
            
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
                if(tblTipoProducto.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimana el registro", 
                            "Eliminar Tipo de Producto", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoProducto(?)}");
                            procedimiento.setInt(1, ((TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoPro());
                            procedimiento.execute();
                            listarTipoProducto.remove(tblTipoProducto.getSelectionModel().getSelectedItem());
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
                if(tblTipoProducto.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/harlinpalacios/imagenes/Actualizar Clientes.png"));
                    imgReporte.setImage(new Image("/org/harlinpalacios/imagenes/Cancelar.png"));
                    activarControles();
                    txtCodigoTP.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarTipoProducto(?, ?)}");
            TipoProducto registro = (TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcionP.getText());
            procedimiento.setInt(1, registro.getCodigoTipoPro());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
   
    
    public void desactivarControles(){
        txtCodigoTP.setEditable(false);
        txtDescripcionP.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoTP.setEditable(true);
        txtDescripcionP.setEditable(true);
    }
    
    ///////////////////
    // limpiar el texto//
    public void limpiarControles(){
        txtCodigoTP.clear();
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
