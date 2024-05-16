package org.harlinpalacios.bean;

public class TipoProducto {
    private int codigoTipoPro;
    private String descripcion;
    
    public TipoProducto(){

}
    
    public TipoProducto(int codigoTipoPro, String descripcion){
        this.codigoTipoPro = codigoTipoPro;
        this.descripcion = descripcion;
    }

    public int getCodigoTipoPro() {
        return codigoTipoPro;
    }

    public void setCodigoTipoPro(int codigoTipoPro) {
        this.codigoTipoPro = codigoTipoPro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}


