package com.idat.florecer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idat.florecer.entity.DetalleGuiaEntrada;
import com.idat.florecer.service.IDetalleGuiaEntradaService;

@CrossOrigin(origins= {"http://localhost/4200"})
@RestController
@RequestMapping("/detalleGuiaEntrada")
public class detalleGuiaEntradaController {
	@Autowired
	private IDetalleGuiaEntradaService detGuiaEntradaService;
	
	//LISTAR DETALLESGUIAENTRADA
		@GetMapping("/detallesGuiaEntrada")
		public List<DetalleGuiaEntrada> listar(){
			return detGuiaEntradaService.findAll();
		}
	//LISTAR DETALLESGUIAENTRADA X ID
		@GetMapping("/detallesGuiaEntrada/{id}")
		public DetalleGuiaEntrada DetalleGuiaEntrada(@PathVariable Long id) {
			return detGuiaEntradaService.findById(id);
		}
	//CREAR NUEVO DETALLESGUIAENTRADA
		@PostMapping("/detalleGuiaEntradanew")
		public DetalleGuiaEntrada detalleGuiaEntradanew(@RequestBody DetalleGuiaEntrada detalleGuiaEntrada) {
			detGuiaEntradaService.save(detalleGuiaEntrada);
			return detGuiaEntradaService.findById(detalleGuiaEntrada.getIdDetalleGuia());
		}
	//ACTUALIZAR DETALLESGUIAENTRADA
		@PutMapping("/detalleGuiaEntradaUpdate/{id}")
		public DetalleGuiaEntrada actualizar(@RequestBody DetalleGuiaEntrada detalleGuiaEntrada,@PathVariable Long id) {
			DetalleGuiaEntrada detalleGuiaEntradaActual=detGuiaEntradaService.findById(id);
			detalleGuiaEntradaActual.setPrecio(detalleGuiaEntrada.getPrecio());
			detalleGuiaEntradaActual.setCantidad(detalleGuiaEntrada.getCantidad());
			detalleGuiaEntradaActual.setProducto(detalleGuiaEntrada.getProducto());
			detalleGuiaEntradaActual.setGuia_Entrada(detalleGuiaEntrada.getGuia_Entrada());
			detalleGuiaEntradaActual.setProveedor(detalleGuiaEntrada.getProveedor());
			
			detGuiaEntradaService.save(detalleGuiaEntradaActual);
			return detGuiaEntradaService .findById(id);	
		}
	//ELIMINAR DETALLESGUIAENTRADA
		@DeleteMapping("/detalleGuiaEntradaDelete/{id}")
		public void delete(@PathVariable Long id) {
			detGuiaEntradaService.eliminarDetalleGuiaEntrada(id);
		}
}
