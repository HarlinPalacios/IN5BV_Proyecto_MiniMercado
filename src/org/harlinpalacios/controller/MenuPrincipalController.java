package org.harlinpalacios.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.harlinpalacios.system.Principal;

/* 
Herreccia Multiple concepto, Interfaces. POO
*/
public class MenuPrincipalController implements Initializable {
    private Principal escenarioPrincipal;

    @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnMenuProveedores;
    @FXML MenuItem btnMenuProductos;
    @FXML MenuItem btnMenuEmpleados;
    @FXML MenuItem btnMenuCompras;
    @FXML MenuItem btnMenuCargos;
    @FXML MenuItem btnMenuTipoProducto;
    @FXML MenuItem btnMenuProgramador;
    @FXML MenuItem btnMenuTelefono;
    @FXML MenuItem btnMenuEmail;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void clicClientes(ActionEvent event){
        if (event.getSource() == btnMenuClientes){
            escenarioPrincipal.menuClientesView();
        }
    }
    
   @FXML
   public void clicProveedores(ActionEvent event){
       if (event.getSource() == btnMenuProveedores){
           escenarioPrincipal.menuProveedoresView();
       }
   }
   
   @FXML
   public void clicProductos(ActionEvent event){
       if (event.getSource() == btnMenuProductos){
           escenarioPrincipal.menuProductosView();
       }
   }
   
   @FXML
   public void clicEmpleados(ActionEvent event){
       if (event.getSource() == btnMenuEmpleados){
           escenarioPrincipal.menuEmpleadosView();
       }
   }
    
   @FXML 
    public void clicCompras(ActionEvent event){
        if (event.getSource() == btnMenuCompras){
            escenarioPrincipal.menuComprasView();
        }
    }
    
    @FXML 
    public void clicCargos(ActionEvent event){
        if (event.getSource() == btnMenuCargos){
            escenarioPrincipal.menuCargosView();
        }
    }
    
    @FXML 
    public void clicTipoProducto(ActionEvent event){
        if (event.getSource() == btnMenuTipoProducto){
            escenarioPrincipal.menuTipoProductoView();
        }
    }
   
    @FXML 
    public void clicProgramador(ActionEvent event){
        if (event.getSource() == btnMenuProgramador){
            escenarioPrincipal.menuProgramador();
        }
    }
    
    @FXML 
    public void clicTelefonoPro(ActionEvent event){
        if (event.getSource() == btnMenuTelefono){
            escenarioPrincipal.menuTelefonoView();
        }
    }
    
    @FXML 
    public void clicEmailPro(ActionEvent event){
        if (event.getSource() == btnMenuEmail){
            escenarioPrincipal.menuEmialView();
        }
    }
    
}
