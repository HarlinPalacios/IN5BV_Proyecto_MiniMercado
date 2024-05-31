package org.harlinpalacios.bean;

public class DetallesFacturas {
    private int codigoDetalleFac;
    private Double precioUnitario;
    private int cantidad;
    private int codigoFactura;
    
    public DetallesFacturas(){
        
    }

    public DetallesFacturas(int codigoDetalleFac, Double precioUnitario, int cantidad, int codigoFactura) {
        this.codigoDetalleFac = codigoDetalleFac;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.codigoFactura = codigoFactura;
    }

    public int getCodigoDetalleFac() {
        return codigoDetalleFac;
    }

    public void setCodigoDetalleFac(int codigoDetalleFac) {
        this.codigoDetalleFac = codigoDetalleFac;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCodigoFactura() {
        return codigoFactura;
    }

    public void setCodigoFactura(int codigoFactura) {
        this.codigoFactura = codigoFactura;
    }


    
    
    
    
}
