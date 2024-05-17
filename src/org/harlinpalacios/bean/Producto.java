package org.harlinpalacios.bean;

public class Producto {
    private int codigoProductos;
    private String descripcionProducto;
    private Double precioUnitario;
    private Double precioDocena;
    private Double precioMayor;
    private int existencia;
    private int codigoTipoPro;
    private int codigoProveedores;
    
    
    public Producto(){
    
}
    
    public Producto(int codigoProductos, String descripcionProducto, Double precioUnitario, Double precioDocena, Double precioMayor, int existencia, int codigoTipoPro, int codigoProveedores){
    this.codigoProductos = codigoProductos;
    this.descripcionProducto = descripcionProducto;
    this.precioUnitario = precioUnitario;
    this.precioDocena = precioDocena;
    this.precioMayor = precioMayor;
    this.existencia = existencia;
    this.codigoTipoPro = codigoTipoPro;
    this.codigoProveedores = codigoProveedores;
    }

    public int getCodigoProductos() {
        return codigoProductos;
    }

    public void setCodigoProductos(int codigoProductos) {
        this.codigoProductos = codigoProductos;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getPrecioDocena() {
        return precioDocena;
    }

    public void setPrecioDocena(Double precioDocena) {
        this.precioDocena = precioDocena;
    }

    public Double getPrecioMayor() {
        return precioMayor;
    }

    public void setPrecioMayor(Double precioMayor) {
        this.precioMayor = precioMayor;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public int getCodigoTipoPro() {
        return codigoTipoPro;
    }

    public void setCodigoTipoPro(int codigoTipoPro) {
        this.codigoTipoPro = codigoTipoPro;
    }

    public int getCodigoProveedores() {
        return codigoProveedores;
    }

    public void setCodigoProveedores(int codigoProveedores) {
        this.codigoProveedores = codigoProveedores;
    }
    
}
