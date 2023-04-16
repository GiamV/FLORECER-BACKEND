package com.idat.florecer.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="proveedor")
public class Proveedor {
	private static final long serialVersionUID=1L;	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Long idProveedor;
	private String rup;
	private String telefono;
	private String correo;
	private String nombreComercial;
	private String Descripcion;
	private String imagen;
	private int estado;
	
	public Long getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}
	public String getRup() {
		return rup;
	}
	public void setRup(String rup) {
		this.rup = rup;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getNombreComercial() {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
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
