package com.idat.florecer.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tipo_pago")
public class TipoPago {
	private static final long serialVersionUID=1L;	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Long idTipoPago;
	private String tipoPago;
	
	public Long getIdTipoPago() {
		return idTipoPago;
	}
	public void setIdTipoPago(Long idTipoPago) {
		this.idTipoPago = idTipoPago;
	}

	public String getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
