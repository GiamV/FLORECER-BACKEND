package com.idat.florecer.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dashboards")
public class VentasPorMesDTO {
	

@Id
private String mes;
private Double totalDinero;




// Getters y setters
public String getMes() {
    return mes;
}

public void setMes(String mes) {
    this.mes = mes;
}

public Double getTotalDinero() {
    return totalDinero;
}

public void setTotalDinero(Double totalDinero) {
    this.totalDinero = totalDinero;
}





}
