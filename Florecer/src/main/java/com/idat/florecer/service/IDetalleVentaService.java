package com.idat.florecer.service;

import java.util.List;

import com.idat.florecer.entity.DetalleVenta;

public interface IDetalleVentaService {
	//DATA ACCESS OBJECT
		public List<DetalleVenta> findAll();
		
		//CREAR METODO PARA AGREGAR:
		public void save(DetalleVenta detalleVenta);
		
		//CREAR METODO PARA EDITAR:
		public DetalleVenta editarDetalleVenta(Long id);
		
		//CREAR METODO PARA ELIMINAR:
		public void eliminarDetalleVenta(Long id);
		
		public DetalleVenta findById(Long id);
		
}
