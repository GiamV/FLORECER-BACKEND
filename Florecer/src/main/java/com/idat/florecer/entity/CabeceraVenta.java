package com.idat.florecer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="cabecera_venta")
public class CabeceraVenta {
	private static final long serialVersionUID=1L;	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Long idCabecera;
	private float bruto;
	private float igv;
	private float neto;
	
	private String tipoCabecera;
	
	public String getTipoCabecera() {
		return tipoCabecera;
	}

	public void setTipoCabecera(String tipoCabecera) {
		this.tipoCabecera = tipoCabecera;
	}

	@Column(name="fecha_venta")
	@Temporal(TemporalType.DATE)
	private Date fechamat;
	
	@ManyToOne
	@JoinColumn(name="idUsuario")
	Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="idTipoPago")
	TipoPago tipoPago;
	
	private int estado;

	public Long getIdCabecera() {
		return idCabecera;
	}

	public void setIdCabecera(Long idCabecera) {
		this.idCabecera = idCabecera;
	}

	public float getBruto() {
		return bruto;
	}

	public void setBruto(float bruto) {
		this.bruto = bruto;
	}

	public float getIgv() {
		return igv;
	}

	public void setIgv(float igv) {
		this.igv = igv;
	}

	public float getNeto() {
		return neto;
	}

	public void setNeto(float neto) {
		this.neto = neto;
	}

	public Date getFechamat() {
		return fechamat;
	}

	public void setFechamat(Date fechamat) {
		this.fechamat = fechamat;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public TipoPago getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(TipoPago tipoPago) {
		this.tipoPago = tipoPago;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
