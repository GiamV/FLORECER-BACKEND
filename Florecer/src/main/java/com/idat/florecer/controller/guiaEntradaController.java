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

import com.idat.florecer.entity.GuiaEntrada;
import com.idat.florecer.entity.Producto;
import com.idat.florecer.service.IGuiaEntradaService;
import com.idat.florecer.service.IProductoService;
import com.idat.florecer.serviceR.DetaGuiaServiceIm;
import com.idat.florecer.serviceR.GuiaEmtradaServiceIm;

@CrossOrigin(origins= {"http://localhost/4200"})
@RestController
@RequestMapping("/guiaEntrada")
public class guiaEntradaController {
	@Autowired
	private IGuiaEntradaService guiaEntradaService;
	@Autowired
	private DetaGuiaServiceIm guiaService;
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private GuiaEmtradaServiceIm guiaEntraS;
	@Autowired
	private detalleGuiaEntradaController con;
	@Autowired
	private DetaGuiaServiceIm guiaEntradaSe;
	
	//LISTAR GUIA DE ENTRADAS
	@GetMapping("/guiaEntradas")
	public List<GuiaEntrada> listar(){
		return guiaEntradaService.findAll();
	}
	
	@GetMapping("/guiaEntradasPendiente")
	public List<GuiaEntrada> listarPen(){
		return guiaEntraS.listarGuiaPen();
	}
	
	//LISTAR GUIA DE ENTRADAS X ID
	@GetMapping("/guiaEntradas/{id}")
	public GuiaEntrada GuiaEntradaxId(@PathVariable Long id) {
		return guiaEntradaService.findById(id);
	}
	
	//LISTAR GUIA DE ENTRADAS X ID
		@GetMapping("/guiaEntradaUser/{id}")
		public GuiaEntrada GuiaEntradaxIdUE(@PathVariable Long id) {
			return guiaEntraS.FindByUserAndEst(id);
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
	
	//ACTUALIZAR GUIA DE ENTRADA
		@PutMapping("/guiaEnUpdate/{id}/{estado}")
		public GuiaEntrada actualizarGuiaEstado(@PathVariable Long id,@PathVariable Long estado,@RequestBody GuiaEntrada guiaEntrada) {
			GuiaEntrada guiaEntradaActual=guiaEntradaService.findById(id);
			if(estado==3){
			guiaEntradaActual.setEstado(3);
			List<DetalleGuiaEntrada> listaDetalle2=guiaEntradaSe.findBycodGui(guiaEntradaActual.getIdGuiaEntrada());
			 for(int i=0;i<listaDetalle2.size();i++) {

	                Producto productoActual=productoService.findById(listaDetalle2.get(i).getProducto().getIdProducto());
	                System.out.println(productoActual.getStock()+listaDetalle2.get(i).getCantidad());
	                productoActual.setStock(productoActual.getStock()+listaDetalle2.get(i).getCantidad());
	                productoService.save(productoActual);
	                //cabeceraService.findById(listaDetalle.get(i).getCabecera().getIdCabecera());
	            }
			
			}else {
				guiaEntradaActual.setEstado(4);
				
			}
			
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
	
	
	
	
	
	//CAMBIAR ESTADO DE CARRITO A VENTA
		@PutMapping("/ComprarCabecera/{idGuia}")
		public void Comprar(@PathVariable Long idGuia,@RequestBody GuiaEntrada cabecera) {
			GuiaEntrada guia=guiaEntradaService.findById(idGuia);
			System.out.println("====================================");
			System.out.println(guia.getUsuario().getIdUsuario()+"       "+idGuia);

			System.out.println("====================================");
			

			guiaEntraS.CompraCabe(idGuia,guia.getUsuario().getIdUsuario());
			//cabeceraregister(cabe.getUsuario().getIdUsuario());	
		}
		
		
	
	
}
