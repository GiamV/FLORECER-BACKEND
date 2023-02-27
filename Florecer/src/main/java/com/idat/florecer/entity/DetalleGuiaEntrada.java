package com.idat.florecer.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="detalla_guia")
public class DetalleGuiaEntrada {
	private static final long serialVersionUID=1L;	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Long idDetalleGuia;
	private int cantidad;
	private float precio;
	
	@ManyToOne
	@JoinColumn(name="IdProducto")
	Producto producto;
	
	@ManyToOne
	@JoinColumn(name="IdGuiaEntrada")
	GuiaEntrada guia_Entrada;
	
	@ManyToOne
	@JoinColumn(name="idProveedor")
	Proveedor proveedor;

	public Long getIdDetalleGuia() {
		return idDetalleGuia;
	}

	public void setIdDetalleGuia(Long idDetalleGuia) {
		this.idDetalleGuia = idDetalleGuia;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public GuiaEntrada getGuia_Entrada() {
		return guia_Entrada;
	}

	public void setGuia_Entrada(GuiaEntrada guia_Entrada) {
		this.guia_Entrada = guia_Entrada;
	}
	

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
