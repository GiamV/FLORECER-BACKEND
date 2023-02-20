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
import com.idat.florecer.entity.DetalleVenta;
import com.idat.florecer.service.ICabeceraVentaService;
import com.idat.florecer.service.IDetalleVentaService;
import com.idat.florecer.service.IUsuarioService;
import com.idat.florecer.serviceR.DetaVServiceIm;

@CrossOrigin(origins= {"http://localhost/4200"})
@RestController
@RequestMapping("/detalle")
public class detalleController {
	
	@Autowired
	private IDetalleVentaService detalleService;
	
	@Autowired
	private ICabeceraVentaService cabeceraService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private DetaVServiceIm detaService;
	
	
	//LISTAR DETALLES
	@GetMapping("/detalles")
	public List<DetalleVenta> listar(){
		return detalleService.findAll();
	}
	
	//BUSCAR DETALLE POR ID
	@GetMapping("/detalle/{id}")
	public DetalleVenta detalle (@PathVariable Long id) {
		return detalleService.findById(id);
	}
	
	//CREAR NUEVO DETALLE
	@PostMapping("/detallenew")
	public DetalleVenta detallenew(@RequestBody DetalleVenta detalle) {
		detalleService.save(detalle);
		
		//CabeceraVenta cabe=cabeceraService.findById(detalle.getCabecera().getIdCabecera());
		
		
		return detalleService.findById(detalle.getIdDetalleVenta()); 
	}
	
	//ACTUALIZAR DETALLE
	@PutMapping("/detalleupdate/{id}")
	public DetalleVenta actualizar(@RequestBody DetalleVenta detalle,@PathVariable Long id) {
		DetalleVenta detalleActual=detalleService.findById(id);
		detalleActual.setProducto(detalle.getProducto());
		detalleActual.setCantidad(detalle.getCantidad());
		detalleActual.setCabecera(detalle.getCabecera());
		detalleActual.setPrecio(detalle.getPrecio());
		detalleActual.setEstado(detalle.getEstado());
	
		detalleService.save(detalleActual);
		return detalleService.findById(id);
	}
	
	//ELIMINAR DETALLE
	@DeleteMapping("/detalledelete/{id}")
	public void delete(@PathVariable Long id) {
		detalleService.eliminarDetalleVenta(id);
	}
	
	//TRAER DETALLE POR CARRITO DE COMPRAS
	@GetMapping("/detalleCarrito/{idUsuario}")
	public List<DetalleVenta> listarDetalle (@PathVariable Long idUsuario) {
		
		
		List<DetalleVenta> listaDetalle=detalleService.findAll();
		ArrayList<DetalleVenta> ListaCarrito= new ArrayList<DetalleVenta>();
		
		
		for(int i=0;i<listaDetalle.size();i++) {
			
			
			//cabeceraService.findById(listaDetalle.get(i).getCabecera().getIdCabecera());
			if ((cabeceraService.findById(listaDetalle.get(i).getCabecera().getIdCabecera())).getTipoCabecera().equals("Carrito")&&
			(usuarioService.findById(cabeceraService.findById(listaDetalle.get(i).getCabecera().getIdCabecera()).getUsuario().getIdUsuario()).getIdUsuario().equals(idUsuario))) {
				ListaCarrito.add(listaDetalle.get(i));
			}
		}
		
		return ListaCarrito;
	}
	
	//ACTUALIZAR CANTIDAD DE PRODUCTO
	@PutMapping("/detalleCarrito/{id}")
	public void updateCant(@PathVariable Long id,@RequestBody DetalleVenta det) {
		DetalleVenta detalleActual=detalleService.findById(id);
		detalleActual.setCantidad(det.getCantidad());
		detalleService.save(detalleActual);
		
		actualizarCabecera(detalleActual.getCabecera().getIdCabecera());
		
	}
	
	//ELIMINAR DETALLE DE VENTA
	@DeleteMapping("detalleCarritodelete/{id}")
	public void deleteCarrito(@PathVariable Long id) {
		Long idDet=detalleService.findById(id).getCabecera().getIdCabecera();
		detalleService.eliminarDetalleVenta(id);
		actualizarCabecera(idDet);
	}
	

	public void actualizarCabecera(Long idCabecera) {
		CabeceraVenta cabecera=cabeceraService.findById(idCabecera);

		List<DetalleVenta> listaDetalles=listarDetalle(cabecera.getUsuario().getIdUsuario());
		
		float bruto=0;
		for(int i=0;i<listaDetalles.size();i++) {
			DetalleVenta detalle=listaDetalles.get(i);
			bruto=bruto+(detalle.getCantidad()*detalle.getPrecio());
		}
		cabecera.setBruto(bruto);
		cabecera.setIgv( (float) (bruto*0.18));
		cabecera.setNeto(bruto+cabecera.getIgv());
		cabeceraService.save(cabecera);
		
	}
	@GetMapping("/detallescliente/{idCab}")
	public List<DetalleVenta> listarCliente(@PathVariable Long idCab){
		return detaService.findByCaU(idCab);
	}
	
	@DeleteMapping("detalleestado/{id}")
	public void deleteestado(@PathVariable Long id) {
		DetalleVenta detalleActual=detalleService.findById(id);
		detalleActual.setEstado(0);
		detalleService.save(detalleActual);
	}

}
