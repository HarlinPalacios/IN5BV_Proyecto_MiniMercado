package org.harlinpalacios.bean;

public class Email {
    private int codigoEmailProveedor;
    private String emailProveedor;
    private String descripcion;
    
    
public Email(){

}

    public Email(int codigoEmailProveedor, String emailProveedor, String descripcion) {
        this.codigoEmailProveedor = codigoEmailProveedor;
        this.emailProveedor = emailProveedor;
        this.descripcion = descripcion;
    }

    public int getCodigoEmailProveedor() {
        return codigoEmailProveedor;
    }

    public void setCodigoEmailProveedor(int codigoEmailProveedor) {
        this.codigoEmailProveedor = codigoEmailProveedor;
    }

    public String getEmailProveedor() {
        return emailProveedor;
    }

    public void setEmailProveedor(String emailProveedor) {
        this.emailProveedor = emailProveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



}
