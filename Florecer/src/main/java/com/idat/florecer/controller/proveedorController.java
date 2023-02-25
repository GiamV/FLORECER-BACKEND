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

import com.idat.florecer.entity.Proveedor;
import com.idat.florecer.service.IProveedorService;

@CrossOrigin(origins= {"http://localhost/4200"})
@RestController
@RequestMapping("/proveedor")
public class proveedorController {
	@Autowired
	private IProveedorService provService;
	
	//LISTAR PROVEEDORES
			@GetMapping("/proveedores")
			public List<Proveedor> listar(){
				return provService.findAll();
			}
		//LISTAR PROVEEDOR X ID
			@GetMapping("/proveedores/{id}")
			public Proveedor Proveedor(@PathVariable Long id) {
				return provService.findById(id);
			}
		//CREAR NUEVO PROVEEDOR
			@PostMapping("/ProveedorNew")
			public Proveedor Proveedornew(@RequestBody Proveedor proveedor) {
				provService.save(proveedor);
				return provService.findById(proveedor.getIdProveedor());
			}
		//ACTUALIZAR PROVEEDOR
			@PutMapping("/ProveedorUpdate/{id}")
			public Proveedor actualizar(@RequestBody Proveedor proveedor,@PathVariable Long id) {
				Proveedor proveedorActual=provService.findById(id);
				proveedorActual.setRup(proveedor.getRup());
				proveedorActual.setTelefono(proveedor.getTelefono());
				proveedorActual.setCorreo(proveedor.getCorreo());
				proveedorActual.setNombreComercial(proveedor.getNombreComercial());
				proveedorActual.setDescripcion(proveedor.getDescripcion());
				
				provService.save(proveedorActual);
				return provService .findById(id);	
			}
		//ELIMINAR PROVEEDOR 
			@DeleteMapping("/ProveedorDelete/{id}")
			public void delete(@PathVariable Long id) {
				provService.eliminarProveedor(id);
			}
}
