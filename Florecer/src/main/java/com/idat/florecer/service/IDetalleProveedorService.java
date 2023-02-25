package com.idat.florecer.service;

import java.util.List;

import com.idat.florecer.entity.DetalleProveedor;

public interface IDetalleProveedorService {
	//DATA ACCESS OBJECT
			public List<DetalleProveedor> findAll();
				
			//CREAR METODO PARA AGREGAR:
			public void save(DetalleProveedor detalleProveedor);
				
			//CREAR METODO PARA EDITAR:
			public DetalleProveedor editarDetalleProveedor(Long id);
				
			//CREAR METODO PARA ELIMINAR:
			public void eliminarDetalleProveedor(Long id);
			
			//CREAR METODO PARA BUSCAR X ID:
			public DetalleProveedor findById(Long id);
}
