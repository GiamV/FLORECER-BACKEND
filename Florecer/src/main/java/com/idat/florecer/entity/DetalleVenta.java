package com.idat.florecer.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="detalle_venta")
public class DetalleVenta {
	private static final long serialVersionUID=1L;	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Long idDetalleVenta;
	private int cantidad;
	private float precio;
	@ManyToOne
	@JoinColumn(name="IdProducto")
	Producto producto;
	@ManyToOne
	@JoinColumn(name="IdCabecera")
	CabeceraVenta cabecera;
	
	private int estado;
	
	public Long getIdDetalleVenta() {
		return idDetalleVenta;
	}
	public void setIdDetalleVenta(Long idDetalleVenta) {
		this.idDetalleVenta = idDetalleVenta;
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
	public CabeceraVenta getCabecera() {
		return cabecera;
	}
	public void setCabecera(CabeceraVenta cabecera) {
		this.cabecera = cabecera;
	}
	
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
