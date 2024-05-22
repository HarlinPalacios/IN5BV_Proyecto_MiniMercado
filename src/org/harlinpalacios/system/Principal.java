package org.harlinpalacios.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.harlinpalacios.controller.MenuCargosController;
import org.harlinpalacios.controller.MenuClientesController;
import org.harlinpalacios.controller.MenuComprasController;
import org.harlinpalacios.controller.MenuEmailProveedorController;
import org.harlinpalacios.controller.MenuEmpleadosController;
import org.harlinpalacios.controller.MenuFacturasController;
import org.harlinpalacios.controller.MenuPrincipalController;
import org.harlinpalacios.controller.MenuProductosController;
import org.harlinpalacios.controller.MenuProgramador;
import org.harlinpalacios.controller.MenuProveedoresController;
import org.harlinpalacios.controller.MenuTelefonoProController;
import org.harlinpalacios.controller.MenuTipoProductoContoller;
 
/**
 *Documentacion
 * Nombre completo:Harlin Williams Palacios Alvarez
 * Fecha de creacion: 11/04/2024
 * Fecha de Modificacion: 10/05/2024 
 */
public class Principal extends Application {
  private Stage escenarioPrincipal;
  private Scene escena;
  private final String URLVIEW = "/org/harlinpalacios/view/";

  
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
       this.escenarioPrincipal = escenarioPrincipal;
       this.escenarioPrincipal.setTitle("Mini Mercado");
       menuPrincipalView();
      //Parent root = FXMLLoader.load(getClass().getResource("/org/harlinpalacios/view/MenuPrincipalView.fxml"));
      // Scene escena = new Scene(root);
      // escenarioPrincipal.setScene(escena);
       escenarioPrincipal.show();      
    }
    
    
     public Initializable cambiarEscena(String fxmlName, int width, int heigth) throws Exception{
         Initializable resultado;
         FXMLLoader loader = new FXMLLoader();
         
         InputStream file = Principal.class.getResourceAsStream(URLVIEW + fxmlName);
         loader.setBuilderFactory(new JavaFXBuilderFactory());
         loader.setLocation(Principal.class.getResource(URLVIEW + fxmlName));
         
         escena = new Scene ((AnchorPane)loader.load(file), width, heigth);
         escenarioPrincipal.setScene(escena);
         escenarioPrincipal.sizeToScene();
         
         resultado = (Initializable)loader.getController();
         
        return resultado;
              }
   
    public void menuPrincipalView (){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 600,400);
            menuPrincipalView.setEscenarioPrincipal(this);  
        }catch(Exception e){
            e.printStackTrace();
        }
    }
   
    public void menuClientesView(){
        try{
            MenuClientesController menuClienteView = (MenuClientesController)cambiarEscena("MenuClientesView.fxml", 720,551);
            menuClienteView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
   
    }
    
    public void menuProveedoresView(){
        try{
            MenuProveedoresController menuProveedoresView =(MenuProveedoresController) cambiarEscena("MenuProveedoresView.fxml",754,565);
            menuProveedoresView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void menuProductosView(){
        try{
            MenuProductosController menuProductosView =(MenuProductosController) cambiarEscena("MenuProductosView.fxml",856,405);
            menuProductosView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void menuEmpleadosView(){
        try{
            MenuEmpleadosController menuEmpleadosView =(MenuEmpleadosController) cambiarEscena("MenuEmpleadosView.fxml",595,443);
            menuEmpleadosView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void menuComprasView(){
        try{
            MenuComprasController menuComprasController = (MenuComprasController)cambiarEscena("MenuComprasView.fxml", 657,359);
            menuComprasController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
   
    }
    
    
    public void menuCargosView(){
        try{
            MenuCargosController menuCargosController = (MenuCargosController)cambiarEscena("MenuCargosView.fxml", 720,405);
            menuCargosController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuTipoProductoView(){
        try{
            MenuTipoProductoContoller menuTipoProductoController = (MenuTipoProductoContoller)cambiarEscena("MenuTipoProductoView.fxml", 680,380);
            menuTipoProductoController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void menuProgramador(){
        try{
            MenuProgramador menuProgramador = (MenuProgramador)cambiarEscena("MenuProgramador.fxml", 569,375);
            menuProgramador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
   
    }
           
    
    public void menuTelefonoView(){
        try{
            MenuTelefonoProController menuTelefonoView = (MenuTelefonoProController)cambiarEscena("MenuTelefonoView.fxml", 710,357);
            menuTelefonoView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuEmialView(){
            try{
                MenuEmailProveedorController menuEmailProView = (MenuEmailProveedorController)cambiarEscena("MenuEmailProView.fxml", 607,334);
                menuEmailProView.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
  
    public void menuFacturasView(){
            try{
                MenuFacturasController menuFacturasView = (MenuFacturasController)cambiarEscena("MenuFacturasView.fxml", 684,415);
                menuFacturasView.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
 
}