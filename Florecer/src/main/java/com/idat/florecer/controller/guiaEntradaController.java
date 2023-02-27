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

import com.idat.florecer.entity.GuiaEntrada;
import com.idat.florecer.service.IGuiaEntradaService;

@CrossOrigin(origins= {"http://localhost/4200"})
@RestController
@RequestMapping("/guiaEntrada")
public class guiaEntradaController {
	@Autowired
	private IGuiaEntradaService guiaEntradaService;
	
	//LISTAR GUIA DE ENTRADAS
	@GetMapping("/guiaEntradas")
	public List<GuiaEntrada> listar(){
		return guiaEntradaService.findAll();
	}
	//LISTAR GUIA DE ENTRADAS X ID
	@GetMapping("/guiaEntradas/{id}")
	public GuiaEntrada GuiaEntradaxId(@PathVariable Long id) {
		return guiaEntradaService.findById(id);
	}
	//CREAR NUEVA GUIA DE ENTRADA
	@PostMapping("/guiaEntradaNew")
	public GuiaEntrada GuiaEntradarnew(@RequestBody GuiaEntrada guiaEntrada) {
		guiaEntradaService.save(guiaEntrada);
		return guiaEntradaService.findById(guiaEntrada.getIdGuiaEntrada());
	}
	//ACTUALIZAR GUIA DE ENTRADA
	@PutMapping("/guiaEntradaUpdate/{id}")
	public GuiaEntrada actualizar(@RequestBody GuiaEntrada guiaEntrada,@PathVariable Long id) {
		GuiaEntrada guiaEntradaActual=guiaEntradaService.findById(id);
		guiaEntradaActual.setNeto(guiaEntrada.getNeto());
		guiaEntradaActual.setFechamat(guiaEntrada.getFechamat());
		guiaEntradaActual.setEstado(guiaEntrada.getEstado());
		guiaEntradaActual.setUsuario(guiaEntrada.getUsuario());
		
		guiaEntradaService.save(guiaEntradaActual);
		return guiaEntradaService .findById(id);	
	}
	//ELIMINAR GUIA DE ENTRADA 
	@DeleteMapping("/guiaEntradaDelete/{id}")
	public void delete(@PathVariable Long id) {
		guiaEntradaService.eliminarGuiaEntrada(id);
	}
	
	@DeleteMapping("/guiaEntradaEstado/{id}")
	public void deleteestado(@PathVariable Long id) {
		GuiaEntrada guiaEntradaActual=guiaEntradaService.findById(id);
		guiaEntradaActual.setEstado(0);
		guiaEntradaService.save(guiaEntradaActual);
		
	}
}
