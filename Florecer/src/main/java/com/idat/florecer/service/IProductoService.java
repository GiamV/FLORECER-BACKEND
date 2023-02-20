package com.idat.florecer.service;

import java.util.List;

import com.idat.florecer.entity.Producto;

public interface IProductoService {
	//DATA ACCESS OBJECT
			public List<Producto> findAll();
			
			//CREAR METODO PARA AGREGAR:
			public void save(Producto producto);
			
			//CREAR METODO PARA EDITAR:
			public Producto editarProducto(Long id);
			
			//CREAR METODO PARA ELIMINAR:
			public void eliminarProducto(Long id);
			
			public Producto findById(Long id);
}
