package com.idat.florecer.controller;

import java.util.ArrayList;
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

import com.idat.florecer.entity.CabeceraVenta;
import com.idat.florecer.entity.DetalleGuiaEntrada;
import com.idat.florecer.entity.DetalleVenta;
import com.idat.florecer.entity.GuiaEntrada;
import com.idat.florecer.service.IDetalleGuiaEntradaService;
import com.idat.florecer.service.IGuiaEntradaService;
import com.idat.florecer.service.IUsuarioService;
import com.idat.florecer.serviceR.DetaGuiaServiceIm;

@CrossOrigin(origins= {"http://localhost/4200"})
@RestController
@RequestMapping("/detalleGuiaEntrada")
public class detalleGuiaEntradaController {
	@Autowired
	private IDetalleGuiaEntradaService detGuiaEntradaService;
	@Autowired
	private IGuiaEntradaService guiaEntradaService;
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private DetaGuiaServiceIm guiaEntradaSe;
	
	//LISTAR DETALLESGUIAENTRADA
		@GetMapping("/detallesGuiaEntrada")
		public List<DetalleGuiaEntrada> listar(){
			return detGuiaEntradaService.findAll();
		}
		@GetMapping("/detallesGuiaEncodId/{id}")
		public List<DetalleGuiaEntrada> listarDetaGuia(@PathVariable Long id){
			return guiaEntradaSe.findBycodGui(id);
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
			Long idDet=detGuiaEntradaService.findById(id).getGuia_Entrada().getIdGuiaEntrada();
			detGuiaEntradaService.eliminarDetalleGuiaEntrada(id);
			actualizarCabecera(idDet);
		}
		
		//TRAER DETALLE POR CARRITO DE COMPRAS
		@GetMapping("/detalleCompra/{idUsuario}")
		public List<DetalleGuiaEntrada> listarDetalleC (@PathVariable Long idUsuario) {
			
			
			List<DetalleGuiaEntrada> listaDetalle=detGuiaEntradaService.findAll();
			ArrayList<DetalleGuiaEntrada> ListaCarrito= new ArrayList<DetalleGuiaEntrada>();
			
			System.out.println(idUsuario);
			for(int i=0;i<listaDetalle.size();i++) {
				
				System.out.println(listaDetalle.get(i).getGuia_Entrada().getIdGuiaEntrada());
				//cabeceraService.findById(listaDetalle.get(i).getCabecera().getIdCabecera());
				if ((guiaEntradaService.findById(listaDetalle.get(i).getGuia_Entrada().getIdGuiaEntrada())).getEstado()==1&&
				(usuarioService.findById(guiaEntradaService.findById(listaDetalle.get(i).getGuia_Entrada().getIdGuiaEntrada()).getUsuario().getIdUsuario()).getIdUsuario().equals(idUsuario))) {
					ListaCarrito.add(listaDetalle.get(i));
				}
			}
			
			return ListaCarrito;
		}
		
		
		//TRAER DETALLE POR CARRITO DE COMPRAS
		@GetMapping("/detalleGuiaCarritoU/{idUsuario}")
		public List<DetalleGuiaEntrada> listarDetalle (@PathVariable Long idUsuario) {
			
			
			List<DetalleGuiaEntrada> listaDetalle=detGuiaEntradaService.findAll();
			ArrayList<DetalleGuiaEntrada> ListaCarrito= new ArrayList<DetalleGuiaEntrada>();
			
			
			for(int i=0;i<listaDetalle.size();i++) {
				
				
				//cabeceraService.findById(listaDetalle.get(i).getCabecera().getIdCabecera());
				if ((guiaEntradaService.findById(listaDetalle.get(i).getGuia_Entrada().getIdGuiaEntrada())).getEstado()==1&&
				(usuarioService.findById(guiaEntradaService.findById(listaDetalle.get(i).getGuia_Entrada().getIdGuiaEntrada()).getUsuario().getIdUsuario()).getIdUsuario().equals(idUsuario))) {
					ListaCarrito.add(listaDetalle.get(i));
				}
			}
			
			return ListaCarrito;
		}
		
		//ACTUALIZAR CANTIDAD DE PRODUCTO
		@PutMapping("/detalleGuiaCarrito/{id}")
		public void updateCant(@PathVariable Long id,@RequestBody DetalleGuiaEntrada det) {
			DetalleGuiaEntrada detalleActual=detGuiaEntradaService.findById(id);
			detalleActual.setCantidad(det.getCantidad());
			detGuiaEntradaService.save(detalleActual);
			
			actualizarCabecera(detalleActual.getGuia_Entrada().getIdGuiaEntrada());
			
		}
		
		
		
		public void actualizarCabecera(Long idCabecera) {
			GuiaEntrada cabecera=guiaEntradaService.findById(idCabecera);

			List<DetalleGuiaEntrada> listaDetalles=listarDetalle(cabecera.getUsuario().getIdUsuario());
			
			float bruto=0;
			for(int i=0;i<listaDetalles.size();i++) {
				DetalleGuiaEntrada detalle=listaDetalles.get(i);
				bruto=bruto+(detalle.getCantidad()*detalle.getPrecio());
			}
			cabecera.setNeto(bruto);
			guiaEntradaService.save(cabecera);
			
		}
}
