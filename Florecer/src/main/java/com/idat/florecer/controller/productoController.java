package com.idat.florecer.controller;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    // Crear un logger para esta clase
    private static final Logger logger = LoggerFactory.getLogger(productoController.class);	
	
	@Autowired
	private IProductoService productoService;
	@Autowired
	private CategoriaServiceIm productoS;
	
	//LISTAR PRODUCTOS
	@GetMapping("/productos")
	public List<Producto> listar(){
		logger.debug("Se ha llamado al endpoint /productos");
        logger.info("Enviando listado de Productos");
		return productoService.findAll();
	}
	
	//LISTAR PRODUCTOS
		@GetMapping("/productoscat/{id}")
		public List<Producto> listarbycodcat(@PathVariable Long id){
			logger.debug("Se ha llamado al endpoint /productoscat/"+id);
	        logger.info("Enviando listado de Productos");
			return productoS.findByCate(id);
		}
	
	//BUSCAR PRODUCTO POR ID
	@GetMapping("/producto/{id}")
	public Producto producto (@PathVariable Long id) {
		logger.debug("Se ha llamado al endpoint /producto/"+id);
        logger.info("Enviando busqueda de producto");
		return productoService.findById(id);
	}
	
	//CREAR NUEVO PRODUCTO
	@PostMapping("/productonew")
	public Producto productonew(@RequestBody Producto producto) {
		productoService.save(producto);
		logger.debug("Se ha llamado al endpoint /productonew");
        logger.info("Enviando metodo Post");
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
		logger.debug("Se ha llamado al endpoint /productoupdate"+id);
        logger.info("Enviando metodo Put");
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
		logger.debug("Se ha llamado al endpoint /productodelete"+id);
        logger.info("Enviando metodo Delete");
		
		productoService.save(productoActual);
	}

}
