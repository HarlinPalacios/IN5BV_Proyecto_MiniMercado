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
    private ObservableList<Producto> listarProductos;
    private ObservableList<DetallesCompra> listarDetallesCompra;
    private ObservableList<Compras> listarCompras;
    
    //Setiar los Objetos
    //Iniciales de ComboBox cmb"Funcion"
    
    @FXML private TextField txtCodigoDC;
    @FXML private TextField txtCostoU;
    @FXML private TextField txtCantidadDC;
    @FXML private ComboBox cmbCodigoP;
    @FXML private ComboBox cmbNumeroDocu;
    
    @FXML private TableView tblDetallesCom;
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
        cmbCodigoP.setItems(getProductos());
        cmbNumeroDocu.setItems(getCompras());
    }
    
    
    public void cargarDatos(){
        tblDetallesCom.setItems(getCompras());
        colCodigoDF.setCellValueFactory(new PropertyValueFactory<DetallesCompra, Integer>("codigoDetalles"));
        colCostoU.setCellValueFactory(new PropertyValueFactory<DetallesCompra, Double>("costoUnitario"));
        colCantidadDF.setCellValueFactory(new PropertyValueFactory<DetallesCompra, Integer>("cantidad"));
        colCodigoD.setCellValueFactory(new PropertyValueFactory<DetallesCompra, Integer>("codigoProductos"));
        colNumeroDocu.setCellValueFactory(new PropertyValueFactory<DetallesCompra, Integer>("numeroDocumento"));

    }
    
    
    public void seleccionarElementos(){
       txtCodigoDC.setText(String.valueOf(((DetallesCompra)tblDetallesCom.getSelectionModel().getSelectedItem()).getCodigoDetalleCom()));
        txtCostoU.setText(String.valueOf(((DetallesCompra)tblDetallesCom.getSelectionModel().getSelectedItem()).getCostoUnitario()));
        txtCantidadDC.setText(String.valueOf(((DetallesCompra)tblDetallesCom.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbCodigoP.getSelectionModel().select(buscarProducto(((DetallesCompra)tblDetallesCom.getSelectionModel().getSelectedItem()).getCodigoProductos()));
        cmbNumeroDocu.getSelectionModel().select(buscarCompras(((DetallesCompra)tblDetallesCom.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCompra(?)}");
            procedimiento.setInt(1, codigoCompras);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next());
                resultado = new Compras(registro.getInt("numeroDocumento"),
                                            registro.get("fechaDocumento"),
                                            registro.getString("descripcion"),
                                            registro.getDouble("totalDocumento")
                            
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
    
    
   public ObservableList<DetallesCompra> geDetallesCompra (){
        // Variable lista                       
        ArrayList<DetallesCompra> lista = new ArrayList<>();
        // amnejo de excepcion
        // variable que almace la linea de conexion
        try{                             
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            // funciona por medio del resultado y que avanze de fila en fila
            // la lista de tipo Arraylist donde recivimos nuestros dato
            while(resultado.next()){
                lista.add(new DetallesCompra (resultado.getInt("codigoDetalles"),
                                        resultado.getString("costoUnitario"),
                                        resultado.getInt("cantidad"),
                                        resultado.getInt("codigoProductos"),
                                        resultado.getInt("numeroDocumento")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    return listarDetallesCompra = FXCollections.observableArrayList(lista);
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
        registro.setCodigoDetalleCom(Integer.parseInt(txtCodigoDC.getText()));
        registro.setCodigoProductos(((Producto)cmbCodigoP.getSelectionModel().getSelectedItem()).getCodigoProductos());
        registro.setNumeroDocumento(((Compras)cmbNumeroDocu.getSelectionModel().getSelectedItem()).getNumeroDocumento());
        registro.setCostoUnitario(Double.parseDouble(txtCostoU.getText()));
        registro.setCodigoDetalleCom(Integer.parseInt(txtCantidadDC.getText()));
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarDetallesCompras(?, ?, ?, ?, ?)}");
                procedimiento.setInt(1, registro.getCodigoDetalleCom());
                procedimiento.setDouble(2, registro.getCostoUnitario());
                procedimiento.setInt(3, registro.getCantidad());
                procedimiento.setInt(4, registro.getCodigoProductos());
                procedimiento.setInt(5, registro.getNumeroDocumento());
                
                listarDetallesCompra.add(registro);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        txtCodigoDC.setEditable(false);
        txtCostoU.setEditable(false);
        txtCantidadDC.setEditable(false);
        cmbCodigoP.setEditable(false);
        cmbNumeroDocu.setEditable(false);
        
    }
    public void activarControles(){
        txtCodigoDC.setEditable(true);
        txtCostoU.setEditable(true);
        txtCantidadDC.setEditable(true);
        cmbCodigoP.setEditable(true);
        cmbNumeroDocu.setEditable(true);
        
    }
   
    public void limpiarControles(){
        txtCodigoDC.clear();
        txtCostoU.clear();
        txtCantidadDC.clear();
        cmbCodigoP.getSelectionModel().getSelectedItem();
        cmbNumeroDocu.getSelectionModel().getSelectedItem();
    }
    
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
   
    
}