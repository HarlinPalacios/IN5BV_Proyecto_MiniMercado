package org.harlinpalacios.bean;

public class Empleados {
   private int codigoEmpleados;
   private String nombreEmpleado;
   private String apellidoEmpleado;
   private Double sueldo;
   private String direccion;
   private String turno;
   private int codigoCargoEm;
    
public Empleados(){
    
}

    public Empleados(int codigoEmpleados, String nombreEmpleado, String apellidoEmpleado, Double sueldo, String direccion, String turno, int codigoCargoEm) {
        this.codigoEmpleados = codigoEmpleados;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.sueldo = sueldo;
        this.direccion = direccion;
        this.turno = turno;
        this.codigoCargoEm = codigoCargoEm;
    }

    public int getCodigoEmpleados() {
        return codigoEmpleados;
    }

    public void setCodigoEmpleados(int codigoEmpleados) {
        this.codigoEmpleados = codigoEmpleados;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getCodigoCargoEm() {
        return codigoCargoEm;
    }

    public void setCodigoCargoEm(int codigoCargoEm) {
        this.codigoCargoEm = codigoCargoEm;
    }

    

    
    
    
    
    

}
