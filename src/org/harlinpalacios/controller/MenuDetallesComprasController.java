package org.harlinpalacios.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
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
import org.harlinpalacios.bean.DetallesCompra;
import org.harlinpalacios.bean.Compras;
import org.harlinpalacios.bean.Producto;
import org.harlinpalacios.db.Conexion;
import org.harlinpalacios.system.Principal;


public class MenuDetallesComprasController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<DetallesCompra> listarCompras;
    private ObservableList<Producto> listarProductos;
    
    //Setiar los Objetos
    //Iniciales de ComboBox cmb"Funcion"
    
    @FXML private TextField txtCodigoDF;
    @FXML private TextField txtCostoU;
    @FXML private TextField txtCantidadDF;
    @FXML private ComboBox cmbCodigoP;
    @FXML private ComboBox cmbNumeroDocu;
    
    @FXML private TableView tblDetallesFac;
    @FXML private TableColumn colCodigoDF;
    @FXML private TableColumn colCostoU;
    @FXML private TableColumn colCantidadDF;
    @FXML private TableColumn colCodigoD;
    @FXML private TableColumn colNumeroDocu;
    
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoP.setItems(getCompras());
        
    }
    
    
    public void cargarDatos(){
        tblDetallesFac.setItems(getCompras());
        colCodigoDF.setCellValueFactory(new PropertyValueFactory<DetallesCompra, Integer>("codigoDetalles"));
        colCostoU.setCellValueFactory(new PropertyValueFactory<DetallesCompra, Double>("costoUnitario"));
        colCantidadDF.setCellValueFactory(new PropertyValueFactory<DetallesCompra, Integer>("cantidad"));
        colCodigoD.setCellValueFactory(new PropertyValueFactory<DetallesCompra, Integer>("codigoProductos"));
        colNumeroDocu.setCellValueFactory(new PropertyValueFactory<DetallesCompra, Integer>("numeroDocumento"));

    }
    
    
    public void seleccionarElementos(){
       txtCodigoDF.setText(String.valueOf(((DetallesCompra)tblDetallesFac.getSelectionModel().getSelectedItem()).getCodigoDetalleFac()));
        txtCostoU.setText(String.valueOf(((DetallesCompra)tblDetallesFac.getSelectionModel().getSelectedItem()).getCostoUnitario()));
        txtCantidadDF.setText(String.valueOf(((DetallesCompra)tblDetallesFac.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbCodigoP.getSelectionModel().select(buscarProducto(((DetallesCompra)tblDetallesFac.getSelectionModel().getSelectedItem()).getCodigoProductos()));
        cmbNumeroDocu.getSelectionModel().select(buscarCompras(((DetallesCompra)tblDetallesFac.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
    }

    public Producto buscarProducto (int codigoProductos){
        Producto resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos(?)}");
            procedimiento.setInt(1, codigoProductos);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next());
                resultado = new Producto(registro.getInt("CodigoTipoProducto"),
                                            registro.getString("descripcionProducto"),
                                            registro.getDouble("precioUnitario"),
                                            registro.getDouble("precioDocena"),
                                            registro.getDouble("precioMayor"),
                                            registro.getString("imagenProducto"),
                                            registro.getInt("existencia"),
                                            registro.getInt("codigoTipoPro"),
                                            registro.getInt("codigoProveedores")
                            
                );
                
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return resultado;
    }
    
    public Compras buscarCompras (int codigoCompras){
        Compras resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados(?)}");
            procedimiento.setInt(1, codigoCompras);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next());
                resultado = new Compras(registro.getInt("codigoEmpleados"),
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
    
    
    public ObservableList<Producto> getProductos(){
        ArrayList<Producto> lista = new ArrayList<Producto>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Producto (resultado.getInt("codigoProductos"),
                                        resultado.getString("descripcionProducto"),
                                        resultado.getDouble("precioUnitario"),
                                        resultado.getDouble("precioDocena"),
                                        resultado.getDouble("precioMayor"),
                                        resultado.getInt("existencia"),
                                        resultado.getInt("codigoTipoPro"),
                                        resultado.getInt("codigoProveedores")
                
                
                ));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listarProductos = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                java.sql.Date fecha = resultado.getDate("fechaDocumento");
                LocalDate fechaDP = fecha.toLocalDate();

                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                        fechaDP,
                        resultado.getString("descripcion"),
                        resultado.getString("totalDocumento")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarCompras = FXCollections.observableArrayList(lista);
    }
    
    
    public ObservableList<DetallesCompras> getCompras (){
        ArrayList<DetallesCompras> Lista = new ArrayList<>();
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarFacturas()");
           ResultSet resultado = procedimiento.executeQuery();
           while (resultado.next()){
               Lista.add(new DetallesCompras (resultado.getInt("codigoDetalleFac"),
                                       resultado.getString("precioUnitario"),
                                       resultado.getString("cantidad"),
                                       resultado.getDouble("sueldo"),
                                       resultado.getString("direccion"),
                                       resultado.getString("turno"),
                                       resultado.getInt("codigoCargoEm") 
               ));
           }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listarCompras = FXCollections.observableArrayList(Lista);
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
        DetallesCompra registro = new DetallesCompra();
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
                
                listarCompras.add(registro);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        txtCodigoDF.setEditable(false);
        txtCostoU.setEditable(false);
        txtCantidadDF.setEditable(false);
        cmbCodigoDF.setEditable(false);
        cmbNumeroDocu.setEditable(false);
        
    }
    public void activarControles(){
        txtCodigoDF.setEditable(true);
        txtCostoU.setEditable(true);
        txtCantidadDF.setEditable(true);
        cmbCodigoDF.setEditable(true);
        cmbNumeroDocu.setEditable(true);
        
    }
   
    public void limpiarControles(){
        txtCodigoDF.clear();
        txtCostoU.clear();
        txtCantidadDF.clear();
        cmbCodigoDF.getSelectionModel().getSelectedItem();
        cmbNumeroDocu.getSelectionModel().getSelectedItem();
    }
    
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
   
    
}