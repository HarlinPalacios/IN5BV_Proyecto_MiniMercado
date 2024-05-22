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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.harlinpalacios.bean.Clientes;
import org.harlinpalacios.bean.Empleados;
import org.harlinpalacios.bean.Facturas;
import org.harlinpalacios.db.Conexion;
import org.harlinpalacios.system.Principal;


public class MenuFacturasController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Facturas> listarFacturas;
    private ObservableList<Clientes> listarClientes;
    private ObservableList<Empleados> listarEmpleados;
    
    //Setiar los Objetos
    //Iniciales de ComboBox cmb"Funcion"
    
    @FXML private TextField txtCodigoF;
    @FXML private TextField txtEstado;
    @FXML private TextField txtTotalFac;
    @FXML private ComboBox cmbCodigoCliente;
    @FXML private ComboBox cmbCodigoEmpleado;
    
    @FXML private TableView tblFactura;
    @FXML private TableColumn colCodigoF;
    @FXML private TableColumn colEstado;
    @FXML private TableColumn colTotalFac;
    @FXML private TableColumn colCodigoCliente;
    @FXML private TableColumn colCodigoEmpleado;
    
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoCliente.setItems(getClientes());
        cmbCodigoEmpleado.setItems(getEmpleados());
        
    }
    
    
    public void cargarDatos(){
        tblFactura.setItems(getFacturas());
        colCodigoF.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("codigoFactura"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Facturas, String>("estado"));
        colTotalFac.setCellValueFactory(new PropertyValueFactory<Facturas, Double>("totalFactura"));
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("codigoCliente"));
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("codigoEmpleados"));
    }
    
    
    public void seleccionarElementos(){
       txtCodigoF.setText(String.valueOf(((Facturas)tblFactura.getSelectionModel().getSelectedItem()).getCodigoFactura()));
        txtEstado.setText(((Facturas)tblFactura.getSelectionModel().getSelectedItem()).getEstado());
        txtTotalFac.setText(String.valueOf(((Facturas)tblFactura.getSelectionModel().getSelectedItem()).getTotalFactura()));
        cmbCodigoCliente.getSelectionModel().select(buscarCliente(((Facturas)tblFactura.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        cmbCodigoEmpleado.getSelectionModel().select(buscarEmpleados(((Facturas)tblFactura.getSelectionModel().getSelectedItem()).getCodigoEmpleados()));
    }

    public Clientes buscarCliente (int codigoClientes){
        Clientes resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes(?)}");
            procedimiento.setInt(1, codigoClientes);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next());
                resultado = new Clientes(registro.getInt("codigoCliente"),
                                            registro.getString("nombreCliente"),
                                            registro.getString("apellidoCliente"),
                                            registro.getString("NITCliente"),
                                            registro.getString("telefonoCliente"),
                                            registro.getString("direccionCliente"),
                                            registro.getString("correoCliente")
                            
                );
                
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return resultado;
    }
    
    public Empleados buscarEmpleados (int codigoTipoP){
        Empleados resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados(?)}");
            procedimiento.setInt(1, codigoTipoP);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next());
                resultado = new Empleados(registro.getInt("codigoEmpleados"),
                                            registro.getString("nombreEmpleado"),
                                            registro.getString("apellidoEmpleado"),
                                            registro.getDouble("sueldo"),
                                            registro.getString("direccion"),
                                            registro.getString("turno"),
                                            registro.getInt("codigoCargoEm")
                            
                );
                
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return resultado;
    }
    
    
    public ObservableList<Facturas> getFacturas(){
        ArrayList<Facturas> lista = new ArrayList<Facturas>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarFacturas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Facturas (resultado.getInt("codigoFactura"),
                                        resultado.getString("estado"),
                                        resultado.getDouble("totalFactura"),
                                        resultado.getInt("codigoCliente"),
                                        resultado.getInt("codigoEmpleados")                
                
                ));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listarFacturas = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Clientes> getClientes (){
        ArrayList<Clientes> Lista = new ArrayList<>();
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
           ResultSet resultado = procedimiento.executeQuery();
           while (resultado.next()){
               Lista.add(new Clientes (resultado.getInt("codigoCliente"),
                                       resultado.getString("nombreCliente"),
                                       resultado.getString("apellidoCliente"),
                                       resultado.getString("NITCliente"),
                                       resultado.getString("telefonoCliente"),
                                       resultado.getString("direccionCliente"),
                                       resultado.getString("correoCliente") 
               ));
           }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listarClientes = FXCollections.observableArrayList(Lista);
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
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
        
    }
    
    public void guardar(){
        Facturas registro = new Facturas();
        registro.setCodigoFactura(Integer.parseInt(txtCodigoF.getText()));
        registro.setCodigoCliente(((Clientes)cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoEmpleados(((Empleados)cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoEmpleados());
        registro.setEstado(txtEstado.getText());
        registro.setTotalFactura(Double.parseDouble(txtTotalFac.getText()));
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarFacturas(?, ?, ?, ?, ?)}");
                procedimiento.setInt(1, registro.getCodigoFactura());
                procedimiento.setString(2, registro.getEstado());
                procedimiento.setDouble(3, registro.getTotalFactura());
                procedimiento.setInt(4, registro.getCodigoCliente());
                procedimiento.setInt(5, registro.getCodigoEmpleados());
                
                listarFacturas.add(registro);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        txtCodigoF.setEditable(false);
        txtEstado.setEditable(false);
        txtTotalFac.setEditable(false);
        cmbCodigoCliente.setEditable(false);
        cmbCodigoEmpleado.setEditable(false);
        
    }
    public void activarControles(){
        txtCodigoF.setEditable(true);
        txtEstado.setEditable(true);
        txtTotalFac.setEditable(true);
        cmbCodigoCliente.setEditable(true);
        cmbCodigoEmpleado.setEditable(true);
        
    }
   
    public void limpiarControles(){
        txtCodigoF.clear();
        txtEstado.clear();
        txtTotalFac.clear();
        cmbCodigoCliente.getSelectionModel().getSelectedItem();
        cmbCodigoEmpleado.getSelectionModel().getSelectedItem();
    }
    
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
   
    
}