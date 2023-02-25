package com.idat.florecer.service;

import java.util.List;

import com.idat.florecer.entity.GuiaEntrada;

public interface IGuiaEntradaService {
	//DATA ACCESS OBJECT
		public List<GuiaEntrada> findAll();
			
		//CREAR METODO PARA AGREGAR:
		public void save(GuiaEntrada guiaEntrada);
			
		//CREAR METODO PARA EDITAR:
		public GuiaEntrada editarGuiaEntrada(Long id);
			
		//CREAR METODO PARA ELIMINAR:
		public void eliminarGuiaEntrada(Long id);
		
		//CREAR METODO PARA BUSCAR X ID:
		public GuiaEntrada findById(Long id);
}
