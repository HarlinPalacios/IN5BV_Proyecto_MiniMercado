package org.harlinpalacios.bean;

public class DetallesCompra {
    private int codigoDetalleFac;
    private double costoUnitario;
    private int cantidad;
    private int codigoProductos;
    private int numeroDocumento;
    
    
    public DetallesCompra(){
        
    }

    public DetallesCompra(int codigoDetalleFac, double costoUnitario, int cantidad, int codigoProductos, int numeroDocumento) {
        this.codigoDetalleFac = codigoDetalleFac;
        this.costoUnitario = costoUnitario;
        this.cantidad = cantidad;
        this.codigoProductos = codigoProductos;
        this.numeroDocumento = numeroDocumento;
    }

    public int getCodigoDetalleFac() {
        return codigoDetalleFac;
    }

    public void setCodigoDetalleFac(int codigoDetalleFac) {
        this.codigoDetalleFac = codigoDetalleFac;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCodigoProductos() {
        return codigoProductos;
    }

    public void setCodigoProductos(int codigoProductos) {
        this.codigoProductos = codigoProductos;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    
    
}
