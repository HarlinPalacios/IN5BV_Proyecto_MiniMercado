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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.harlinpalacios.bean.DetallesFacturas;
import org.harlinpalacios.bean.Facturas;
import org.harlinpalacios.db.Conexion;
import org.harlinpalacios.system.Principal;


public class MenuDetallesFacturasController {
    private Principal escenarioPrincipal;
    private enum operaciones{AGREGAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<DetallesFacturas> listarDetallesFsacturas;
    private ObservableList<Facturas> listarFacturas;
    
    
    
    @FXML private Button btnRegresar;
    
    @FXML private TextField txtcodigoDetalleFac;
    @FXML private TextField txtprecioUnitario;
    @FXML private TextField txtCantidad;
    @FXML private ComboBox cmbcodigoFactura;
    
    @FXML private TableView tblDetallesFacturas;
    @FXML private TableColumn colCodigoDetallesFac;
    @FXML private TableColumn colPrecioUnitacio;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colCodigoFactura;
    
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnActualizar;
    @FXML private Button btnReporte;
    
    
    public void initialize(URL location, ResourceBundle resources){
        cargarDatos();
        cmbcodigoFactura.setItems(getFacturas());
    }
    
    
    public void cargarDatos(){
        tblDetallesFacturas.setItems(getDetallesFacturas());
        colCodigoDetallesFac.setCellFactory(new PropertyValueFactory<DetallesFacturas, Integer>("codigoDetalleFac"));
        colPrecioUnitacio.setCellFactory(new PropertyValueFactory<DetallesFacturas, Double>("PrecioUnitacio"));
        colCantidad.setCellFactory(new PropertyValueFactory<DetallesFacturas, Integer>("Cantidad"));
        colCodigoFactura.setCellFactory(new PropertyValueFactory<DetallesFacturas, Integer>("CodigoFactura"));
    }
    
    public void seleccionarElementos(){
        txtcodigoDetalleFac.setText(String.valueOf(((DetallesFacturas)tblDetallesFacturas.getSelectionModel().getSelectedItem()).getCodigoDetalleFac()));
        txtprecioUnitario.setText(String.valueOf(((DetallesFacturas)tblDetallesFacturas.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtCantidad.setText(String.valueOf(((DetallesFacturas)tblDetallesFacturas.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbcodigoFactura.getSelectionModel().select(buscarFactura(((DetallesFacturas)tblDetallesFacturas.getSelectionModel().getSelectedItem()).getCodigoFactura()));
    }
    
    public Facturas buscarFactura (int codigoFactura){
        Facturas resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarFactura(?)}");
            procedimiento.setInt(1, codigoFactura);
            ResultSet registro = procedimiento.executeQuery();
            if (registro.next()){
                resultado = new Facturas(registro.getInt("codigoFactura"),
                                         registro.getString("estado"),
                                         registro.getDouble("totalFactura"),
                                         registro.getString("fechaFacura"),
                                         registro.getInt("codigoCliente"),
                                         registro.getInt("codigoEmpleados")
                );
            
            }   
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public ObservableList<DetallesFacturas> getDetallesFacturas(){
        ArrayList<DetallesFacturas> lista = new ArrayList<DetallesFacturas>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDetallesFacturas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new DetallesFacturas (resultado.getInt("codigoDetalleFac"),
                                        resultado.getDouble("precioUnitario"),
                                        resultado.getInt("cantidad"),
                                        resultado.getInt("codigoFactura")                
                ));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listarDetallesFsacturas = FXCollections.observableArrayList(lista);
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
                                        resultado.getString("fechaFactura"),
                                        resultado.getInt("codigoCliente"),
                                        resultado.getInt("codigoEmpleados")                
                
                ));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listarFacturas = FXCollections.observableArrayList(lista);
    }
    
    public void agregar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnActualizar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnActualizar.setDisable(false);
                btnReporte.setDisable(false);
                cargarDatos();
                break;
        }
    }
    
    public void guardar(){
        DetallesFacturas registro = new DetallesFacturas();
        registro.setCodigoDetalleFac(Integer.parseInt(txtcodigoDetalleFac.getText()));
        registro.setPrecioUnitario(Double.parseDouble(txtprecioUnitario.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarDetallesFacturas (?, ?, ?, ?)}");
                procedimiento.setInt(1, registro.getCodigoDetalleFac());
                procedimiento.setDouble(2, registro.getPrecioUnitario());
                procedimiento.setInt(3, registro.getCantidad());
                procedimiento.setInt(4, registro.getCodigoFactura());
                
                listarDetallesFsacturas.add(registro);
                
                
            }catch(Exception e){
                e.printStackTrace();
            }
            
    }
    
    public void desactivarControles(){
        txtcodigoDetalleFac.setEditable(false);
        txtprecioUnitario.setEditable(false);
        txtCantidad.setEditable(false);
        cmbcodigoFactura.setDisable(false);
    }
    
    public void activarControles(){
        txtcodigoDetalleFac.setEditable(true);
        txtprecioUnitario.setEditable(true);
        txtCantidad.setEditable(true);
        cmbcodigoFactura.setDisable(true);
    }
    
    public void limpiarControles(){
        txtcodigoDetalleFac.clear();
        txtprecioUnitario.clear();
        txtCantidad.clear();
        cmbcodigoFactura.getSelectionModel().clearSelection();
        
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void regresar (ActionEvent event){
        if (event.getSource() == btnRegresar){
            escenarioPrincipal.menuDetallesFacturaView();
        }
    }
    
    
}
