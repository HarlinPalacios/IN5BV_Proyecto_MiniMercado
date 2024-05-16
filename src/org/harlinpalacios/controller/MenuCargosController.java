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
import org.harlinpalacios.bean.Cargos;
import org.harlinpalacios.db.Conexion;
import org.harlinpalacios.system.Principal;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuCargosController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones{AGREGAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Cargos> listarCargos;
    
    @FXML private Button btnRegresar;
    @FXML private TextField txtCodigoC;
    @FXML private TextField txtNombreC;
    @FXML private TextField txtDescripcionC;
    
    @FXML private TableView tblCargos;
    @FXML private TableColumn colCodigoC;
    @FXML private TableColumn colNombreC;
    @FXML private TableColumn colDescripcionC;
    
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
        tblCargos.setItems(getCargos());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Cargos, Integer>("codigoCargoEm"));
        colNombreC.setCellValueFactory(new PropertyValueFactory<Cargos, Integer>("nombreCargo"));
        colDescripcionC.setCellValueFactory(new PropertyValueFactory<Cargos, Integer>("descripcionCargo"));
    }
    
    public void selecionarElementos(){
        txtCodigoC.setText(String.valueOf(((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getCodigoCargoEm()));
        txtNombreC.setText(((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getNombreCargo());
        txtDescripcionC.setText(((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getDescripcionCargo());
    }
    
    public ObservableList<Cargos> getCargos (){
        // Variable lista                       
        ArrayList<Cargos> lista = new ArrayList<>();
        // amnejo de excepcion
        // variable que almace la linea de conexion
        try{                             
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargo()}");
            ResultSet resultado = procedimiento.executeQuery();
            // Funciona por medio del resultado y que avanze de fila en fila
            // La lista de tipo Arraylist donde recivimos nuestros dato
            while(resultado.next()){
                lista.add(new Cargos (resultado.getInt("codigoCargoEm"),
                                        resultado.getString("nombreCargo"),
                                        resultado.getString("descripcionCargo")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    return listarCargos = FXCollections.observableArrayList(lista);
    }
    
    public void agregar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("org/harlinpalacios/imagenes/Guardar.png"));
                imgEliminar.setImage(new Image("org/harlinpalacios/imagenes/Cancelar.png"));
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
                imgAgregar.setImage(new Image("org/harlinpalacios/imagenes/Agregar Cliente.png"));
                imgEliminar.setImage(new Image("org/harlinpalacios/imagenes/Eliminar Cliente.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                
                break;
        }
    }
    
    public void guardar(){
    Cargos registro = new Cargos();
    registro.setCodigoCargoEm(Integer.parseInt(txtCodigoC.getText()));
    registro.setNombreCargo(txtNombreC.getText());
    registro.setDescripcionCargo(txtDescripcionC.getText());
    
    
    try{
        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCargo(?, ?, ?)}");
        procedimiento.setInt(1, registro.getCodigoCargoEm());
        procedimiento.setString(2, registro.getNombreCargo());
        procedimiento.setString(3, registro.getDescripcionCargo());
        listarCargos.add(registro);
    }catch(Exception e){
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
                if(tblCargos.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimana el registro", 
                            "Eliminar Cargos", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCargo(?)}");
                            procedimiento.setInt(1, ((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getCodigoCargoEm());
                            procedimiento.execute();
                            listarCargos.remove(tblCargos.getSelectionModel().getSelectedItem());
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
                if(tblCargos.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/harlinpalacios/imagenes/Actualizar Clientes.png"));
                    imgReporte.setImage(new Image("/org/harlinpalacios/imagenes/Cancelar.png"));
                    activarControles();
                    txtCodigoC.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCargo(?, ?, ?)}");
            Cargos registro = (Cargos)tblCargos.getSelectionModel().getSelectedItem();
            registro.setNombreCargo(txtNombreC.getText());
            registro.setDescripcionCargo(txtDescripcionC.getText());
            procedimiento.setInt(1, registro.getCodigoCargoEm());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void desactivarControles(){
        txtCodigoC.setEditable(false);
        txtNombreC.setEditable(false);
        txtDescripcionC.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoC.setEditable(true);
        txtNombreC.setEditable(true);
        txtDescripcionC.setEditable(true);
    }
    
    ///////////////////
    // limpiar el texto//
    public void limpiarControles(){
        txtCodigoC.clear();
        txtNombreC.clear();
        txtDescripcionC.clear();
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
