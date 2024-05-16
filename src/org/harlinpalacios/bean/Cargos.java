package org.harlinpalacios.bean;

public class Cargos {
    private int codigoCargoEm;
    private String nombreCargo;
    private String descripcionCargo;
    
    public Cargos(){
    
    }
    
    public Cargos (int codigoCargoEm, String nombreCargo, String descripcionCargo){
        this.codigoCargoEm = codigoCargoEm;
        this.nombreCargo = nombreCargo;
        this.descripcionCargo = descripcionCargo;
    }

    public int getCodigoCargoEm() {
        return codigoCargoEm;
    }

    public void setCodigoCargoEm(int codigoCargoEm) {
        this.codigoCargoEm = codigoCargoEm;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }
    
    
    
    
}
