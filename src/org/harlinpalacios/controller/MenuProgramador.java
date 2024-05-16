package org.harlinpalacios.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.harlinpalacios.system.Principal;

/* 
Herreccia Multiple concepto, Interfaces. POO
*/
public class MenuProgramador implements Initializable {
    private Principal escenarioPrincipal;
    @FXML private Button btnRegresar; 
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
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