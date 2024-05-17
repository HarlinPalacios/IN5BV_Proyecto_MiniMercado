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


import org.harlinpalacios.bean.Empleados;
import org.harlinpalacios.db.Conexion;
import org.harlinpalacios.system.Principal;

/**
 *Documentacion
 * Nombre: Harlin Palacios
 * Grado: 5to Perito
 * Seccion: IN5BV
 * Carne: 2020586
 * Fecgha de creacion: 11/4/24
 * Fecha de Modificacion  13/05/24
 */

public class MenuEmpleadosController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Empleados> listarEmpleados;
    
    @FXML private Button btnRegresar;
    
    @FXML private TextField txtCodigoE;
    @FXML private TextField txtNombreE;
    @FXML private TextField txtApellidoE;
    @FXML private TextField txtSueldo;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTurno;
    @FXML private TextField txtCodigoDeCargo;
    
    
    @FXML private TableView tblEmpleados;
    @FXML private TableColumn colCodigoE;
    @FXML private TableColumn colNombreE;
    @FXML private TableColumn colApellidoE;
    @FXML private TableColumn colSueldo;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colTurno;
    @FXML private TableColumn colCodigoCargo;
    
    
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
     
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos(){
        tblEmpleados.setItems(getEmpleados());
        colCodigoE.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoEmpleados"));
        colNombreE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombreEmpleado"));
        colApellidoE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidoEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados, String>("sueldo"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleados, String>("direccion"));
        colTurno.setCellValueFactory(new PropertyValueFactory<Empleados, String>("turno"));
        colCodigoCargo.setCellValueFactory(new PropertyValueFactory<Empleados, String>("codigoCargoEm"));
    }
    
    public void seleccionarElemento(){
        txtCodigoE.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleados()));
        txtNombreE.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNombreEmpleado());
        txtApellidoE.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidoEmpleado());
        txtSueldo.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        txtDireccion.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getDireccion());
        txtTurno.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getTurno());
        txtCodigoDeCargo.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargoEm()));
        
    }
    
    public ObservableList<Empleados> getEmpleados (){
        ArrayList<Empleados> Lista = new ArrayList<>();
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarEmpleados()");
           ResultSet resultado = procedimiento.executeQuery();
           while (resultado.next()){
               Lista.add(new Empleados (resultado.getInt("codigoEmpleados"),
                                       resultado.getString("nombreEmpleado"),
                                       resultado.getString("apellidoEmpleado"),
                                       resultado.getDouble("sueldo"),
                                       resultado.getString("direccion"),
                                       resultado.getString("turno"),
                                       resultado.getInt("codigoCargoEm") 
               ));
           }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listarEmpleados = FXCollections.observableArrayList(Lista);
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
        Empleados registro = new Empleados();
        registro.setCodigoEmpleados(Integer.parseInt(txtCodigoE.getText()));
        registro.setNombreEmpleado(txtNombreE.getText());
        registro.setApellidoEmpleado(txtApellidoE.getText());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
        registro.setDireccion(txtDireccion.getText());
        registro.setTurno(txtTurno.getText());
        registro.setCodigoCargoEm(Integer.parseInt(txtCodigoDeCargo.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmppleado(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoCargoEm());
            procedimiento.setString(2, registro.getNombreEmpleado());
            procedimiento.setString(3, registro.getApellidoEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEm());
            procedimiento.execute();
            listarEmpleados.add(registro);
            
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
                if(tblEmpleados.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimana el registro", 
                            "Eliminar Empleado", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleado(?)}");
                            procedimiento.setInt(1, ((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleados());
                            procedimiento.execute();
                            listarEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());
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
                if(tblEmpleados.getSelectionModel().getSelectedItem() !=null){
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmpleado(?, ?, ?, ?, ?, ?, ?)}");
            Empleados registro = (Empleados)tblEmpleados.getSelectionModel().getSelectedItem();
            registro.setNombreEmpleado(txtNombreE.getText());
            registro.setApellidoEmpleado(txtApellidoE.getText());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            registro.setDireccion(txtDireccion.getText());
            registro.setTurno(txtTurno.getText());
            procedimiento.setInt(1, registro.getCodigoEmpleados());
            procedimiento.setString(2, registro.getNombreEmpleado());
            procedimiento.setString(3, registro.getApellidoEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEm());
            procedimiento.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void desactivarControles(){
        txtCodigoE.setEditable(false);
        txtNombreE.setEditable(false);
        txtApellidoE.setEditable(false);
        txtSueldo.setEditable(false);
        txtDireccion.setEditable(false);
        txtTurno.setEditable(false);
        txtCodigoDeCargo.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoE.setEditable(true);
        txtNombreE.setEditable(true);
        txtApellidoE.setEditable(true);
        txtSueldo.setEditable(true);
        txtDireccion.setEditable(true);
        txtTurno.setEditable(true);
        txtCodigoDeCargo.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoE.clear();
        txtNombreE.clear();
        txtApellidoE.clear();
        txtSueldo.clear();
        txtDireccion.clear();
        txtTurno.clear();
        txtCodigoDeCargo.clear();
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