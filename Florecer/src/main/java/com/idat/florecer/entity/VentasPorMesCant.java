package com.idat.florecer.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dashboards2")
public class VentasPorMesCant {
	

@Id
private String mes;
private Double totalVentas;




// Getters y setters
public String getMes() {
    return mes;
}

public void setMes(String mes) {
    this.mes = mes;
}

public Double getTotalVentas() {
    return totalVentas;
}

public void setTotalVentas(Double totalDinero) {
    this.totalVentas = totalDinero;
}
	

}
