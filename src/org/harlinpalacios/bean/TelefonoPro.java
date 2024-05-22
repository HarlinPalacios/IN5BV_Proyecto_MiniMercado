package org.harlinpalacios.bean;

public class TelefonoPro {
    private int codigoTelefonoProveedor;
    private String numeroPrincipal;
    private String numeroSecundario;
    private String observaciones;
    
    public TelefonoPro(){
        
    }

    public TelefonoPro(int codigoTelefonoProveedor, String numeroPrincipal, String numeroSecundario, String observaciones) {
        this.codigoTelefonoProveedor = codigoTelefonoProveedor;
        this.numeroPrincipal = numeroPrincipal;
        this.numeroSecundario = numeroSecundario;
        this.observaciones = observaciones;
    }

    public int getCodigoTelefonoProveedor() {
        return codigoTelefonoProveedor;
    }

    public void setCodigoTelefonoProveedor(int codigoTelefonoProveedor) {
        this.codigoTelefonoProveedor = codigoTelefonoProveedor;
    }

    public String getNumeroPrincipal() {
        return numeroPrincipal;
    }

    public void setNumeroPrincipal(String numeroPrincipal) {
        this.numeroPrincipal = numeroPrincipal;
    }

    public String getNumeroSecundario() {
        return numeroSecundario;
    }

    public void setNumeroSecundario(String numeroSecundario) {
        this.numeroSecundario = numeroSecundario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
  
    
}
