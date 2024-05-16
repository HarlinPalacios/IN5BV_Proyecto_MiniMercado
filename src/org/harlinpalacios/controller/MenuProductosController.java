package org.harlinpalacios.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import org.harlinpalacios.bean.Productos;
import org.harlinpalacios.bean.Proveedores;
import org.harlinpalacios.bean.TipoProducto;
import org.harlinpalacios.db.Conexion;
import org.harlinpalacios.system.Principal;


public class MenuProductosController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Productos> listarProductos;
    private ObservableList<Proveedores> listarProveedores;
    private ObservableList<TipoProducto> listarTipoDProducto;
    
    //Setiar los Objetos
    //Iniciales de Combo Box cmb"Funcion"
    
    
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void cargardatos(){
        
        
        
    }
    
    
    public ObservableList<Productos> getProducto(){
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Producto (resultado))
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProductos = FXColections.observableArrayList()
    }
}