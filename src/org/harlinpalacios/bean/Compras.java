package org.harlinpalacios.bean;

public class Compras {
   private int numeroDocumento;
   private String fechaDocumento;
   private String descripcionCompras;
   private Boolean totalDocumento;
   
    
public Compras(){
    
}

    public Compras(int numeroDocumento, String fechaDocumento, String descripcionCompras, Boolean totalDocumento) {
        this.numeroDocumento = numeroDocumento;
        this.fechaDocumento = fechaDocumento;
        this.descripcionCompras = descripcionCompras;
        this.totalDocumento = totalDocumento;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getDescripcionCompras() {
        return descripcionCompras;
    }

    public void setDescripcionCompras(String descripcionCompras) {
        this.descripcionCompras = descripcionCompras;
    }

    public Boolean isTotalDocumento() {
        return totalDocumento;
    }

    public void setTotalDocumento(Boolean totalDocumento) {
        this.totalDocumento = totalDocumento;
    }


    

    

}
