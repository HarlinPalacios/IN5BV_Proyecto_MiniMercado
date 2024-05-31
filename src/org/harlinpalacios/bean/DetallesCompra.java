package org.harlinpalacios.bean;

public class DetallesCompra {
    private int codigoDetalleCom;
    private Double costoUnitario;
    private int cantidad;
    private int codigoProductos;
    private int numeroDocumento;
    
    
    public DetallesCompra(){
        
    }

    public DetallesCompra(int codigoDetalleCom, Double costoUnitario, int cantidad, int numeroDocumento) {
        this.codigoDetalleCom = codigoDetalleCom;
        this.costoUnitario = costoUnitario;
        this.cantidad = cantidad;
        this.numeroDocumento = numeroDocumento;
    }

    public int getCodigoDetalleCom() {
        return codigoDetalleCom;
    }

    public void setCodigoDetalleCom(int codigoDetalleCom) {
        this.codigoDetalleCom = codigoDetalleCom;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(Double costoUnitario) {
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
