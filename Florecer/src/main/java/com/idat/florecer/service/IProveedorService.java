package com.idat.florecer.service;

import java.util.List;

import com.idat.florecer.entity.Proveedor;

public interface IProveedorService {
	//DATA ACCESS OBJECT
	public List<Proveedor> findAll();
		
	//CREAR METODO PARA AGREGAR:
	public void save(Proveedor proveedor);
		
	//CREAR METODO PARA EDITAR:
	public Proveedor editarProveedor(Long id);
		
	//CREAR METODO PARA ELIMINAR:
	public void eliminarProveedor(Long id);
	
	//CREAR METODO PARA BUSCAR X ID:
	public Proveedor findById(Long id);
}
