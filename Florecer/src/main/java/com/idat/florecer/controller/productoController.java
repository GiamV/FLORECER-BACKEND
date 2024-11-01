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

import com.idat.florecer.entity.Producto;
import com.idat.florecer.service.IProductoService;
import com.idat.florecer.serviceR.CategoriaServiceIm;

@CrossOrigin(origins= {"http://localhost/4200"})
@RestController
@RequestMapping("/producto")
public class productoController {
	
	@Autowired
	private IProductoService productoService;
	@Autowired
	private CategoriaServiceIm productoS;
	
	//LISTAR PRODUCTOS
	@GetMapping("/productos")
	public List<Producto> listar(){
		return productoService.findAll();
	}
	
	//LISTAR PRODUCTOS
		@GetMapping("/productoscat/{id}")
		public List<Producto> listarbycodcat(@PathVariable Long id){
			return productoS.findByCate(id);
		}
	
	//BUSCAR PRODUCTO POR ID
	@GetMapping("/producto/{id}")
	public Producto producto (@PathVariable Long id) {
		return productoService.findById(id);
	}
	
	//CREAR NUEVO PRODUCTO
	@PostMapping("/productonew")
	public Producto productonew(@RequestBody Producto producto) {
		productoService.save(producto);
		return productoService.findById(producto.getIdProducto()); 
	}
	
	//ACTUALIZAR PRODUCTO
	@PutMapping("/productoupdate/{id}")
	public Producto actualizar(@RequestBody Producto producto,@PathVariable Long id) {
		Producto productoActual=productoService.findById(id);
		productoActual.setProducto(producto.getProducto());
		productoActual.setCategoria(producto.getCategoria());
		productoActual.setEstado(producto.getEstado());
		productoActual.setDescripcion(producto.getDescripcion());
		productoActual.setImagen(producto.getImagen());
		productoActual.setPrecio(producto.getPrecio());
		productoActual.setPrecioCompra(producto.getPrecioCompra());
		productoActual.setStock(producto.getStock());
	
		productoService.save(productoActual);
		return productoService.findById(id);
	}
	
	//ELIMINAR PRODUCTO
	@DeleteMapping("/productodelete/{id}")
	public void delete(@PathVariable Long id) {
		productoService.eliminarProducto(id);
	}
	
	@DeleteMapping("/productoestado/{id}")
	public void deleteestado(@PathVariable Long id) {
		Producto productoActual=productoService.findById(id);
		if(productoActual.getEstado()==1) {
			productoActual.setEstado(0);
		}else {
			productoActual.setEstado(1);
		}
		
		productoService.save(productoActual);
	}

}
