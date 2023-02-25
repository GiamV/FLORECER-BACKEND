package com.idat.florecer.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="detalleProveedor")
public class DetalleProveedor {
	private static final long serialVersionUID=1L;	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idDetalle_Proveedor;
	
	@ManyToOne
	@JoinColumn(name="IdProducto")
	Producto producto;
	
	@ManyToOne
	@JoinColumn(name="IdProveedor")
	Proveedor proveedor;
	
	private int estado;

	public Long getIdDetalle_Proveedor() {
		return idDetalle_Proveedor;
	}

	public void setIdDetalle_Proveedor(Long idDetalle_Proveedor) {
		this.idDetalle_Proveedor = idDetalle_Proveedor;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
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

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
}
