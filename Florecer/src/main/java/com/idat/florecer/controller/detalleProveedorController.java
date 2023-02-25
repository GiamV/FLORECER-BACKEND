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

import com.idat.florecer.entity.DetalleProveedor;
import com.idat.florecer.service.IDetalleProveedorService;

@CrossOrigin(origins= {"http://localhost/4200"})
@RestController
@RequestMapping("/detalleProveedor")
public class detalleProveedorController {
	@Autowired
	private IDetalleProveedorService detalleprovService;
	
	//LISTAR DETALLES
		@GetMapping("/detallesProv")
		public List<DetalleProveedor> listar(){
			return detalleprovService.findAll();
		}
	//LISTAR DET PROVEEDORES X ID
		@GetMapping("/detallesProv/{id}")
		public DetalleProveedor Detalle_Proveedor(@PathVariable Long id) {
			return detalleprovService.findById(id);
		}
	//CREAR NUEVO DETALLE PROVEEDOR
		@PostMapping("/detallesProvnew")
		public DetalleProveedor detprovanew(@RequestBody DetalleProveedor detalleProveedor) {
			detalleprovService.save(detalleProveedor);
			return detalleprovService.findById(detalleProveedor.getIdDetalle_Proveedor());
		}
	//ACTUALIZAR DETALLE PROVEEDOR
		@PutMapping("/detallesProvUpdate/{id}")
		public DetalleProveedor actualizar(@RequestBody DetalleProveedor detalleProveedor,@PathVariable Long id) {
			DetalleProveedor detalleProveedorActual=detalleprovService.findById(id);
			detalleProveedorActual.setProducto(detalleProveedor.getProducto());
			detalleProveedorActual.setProveedor(detalleProveedor.getProveedor());
			
			detalleprovService.save(detalleProveedorActual);
			return detalleprovService .findById(id);	
		}
	//ELIMINAR DETALLE PROVEEDOR 
		@DeleteMapping("/detallesProvDelete/{id}")
		public void delete(@PathVariable Long id) {
			detalleprovService.eliminarDetalleProveedor(id);
		}
		
		@DeleteMapping("/detallesProvEstado/{id}")
		public void deleteestado(@PathVariable Long id) {
			DetalleProveedor detalleProveedorActual=detalleprovService.findById(id);
			detalleProveedorActual.setEstado(0);
			detalleprovService.save(detalleProveedorActual);
			
		}
}
