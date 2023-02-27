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
@Table(name="guia_entrada")
public class GuiaEntrada {
	private static final long serialVersionUID=1L;	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Long idGuiaEntrada;
	private float neto;
	@Column(name="fecha_compra")
	@Temporal(TemporalType.DATE)
	private Date fechamat;
	private int estado;
	
	@ManyToOne
	@JoinColumn(name="idUsuario")
	Usuario usuario;
	
	public Long getIdGuiaEntrada() {
		return idGuiaEntrada;
	}

	public void setIdGuiaEntrada(Long idGuiaEntrada) {
		this.idGuiaEntrada = idGuiaEntrada;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
