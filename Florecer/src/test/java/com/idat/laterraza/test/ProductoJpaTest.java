
package com.idat.laterraza.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.idat.florecer.LaTerrazaSApplication;
import com.idat.florecer.dao.ICategoriaDao;
import com.idat.florecer.dao.IProductoDao;
import com.idat.florecer.entity.Categoria;
import com.idat.florecer.entity.Producto;

@SpringBootTest(classes = LaTerrazaSApplication.class) // Especifica la clase principal de la aplicación
public class ProductoJpaTest {
  
private static Producto objproducto;
    
    @Autowired
    private IProductoDao productoService;
    @Autowired
    private ICategoriaDao categoriaService;
    
    @BeforeAll
    public static void Inicio(){
        objproducto= new Producto();
        System.out.print("BeforeAll --> Inicio");
    }
    
    @AfterAll
    public static void Final(){
        objproducto= null;
        System.out.println("AfterAll --> Final()");
    }
    
    @Test 
    public void Mensaje(){
        System.out.println("Prueba Inicial");
        System.out.println("@Test --> PruebaInicial");
    }
    
    @Test 
    public void findAllTest(){
        Iterable<Producto> productos = productoService.findAll();
        assertNotNull(productos);
    }
    
    
    @Test 
    public void findById(){
        long id=1;
        Optional<Producto> producto = productoService.findById(id);
        assertNotNull(producto);
    }
    
    @Test 
    @Rollback(false)
    public void addTest(){

        Categoria cate =new Categoria();
        cate.setCategoria("categoria");
        cate.setEstado(0);
        cate=categoriaService.save(cate);
        
        objproducto.setCategoria(cate);
        objproducto.setDescripcion("descripcion");
        objproducto.setEstado(0);
        objproducto.setIdProducto(Long.valueOf(1));
        objproducto.setImagen("imagen");
        objproducto.setPrecio(0);
        objproducto.setProducto("producto");
        
        Producto prod = productoService.save(objproducto);
        assertNotNull(prod);
    }
    
    @Test
    @Rollback(false)
    public void updateTest() {
        // Crear y guardar una categoría inicial
        Categoria cate = new Categoria();
        cate.setCategoria("categoria");
        cate.setEstado(0);
        cate = categoriaService.save(cate);

        // Crear y guardar un producto asociado a la categoría
        Producto productoExistente = new Producto();
        productoExistente.setCategoria(cate);
        productoExistente.setDescripcion("descripcion");
        productoExistente.setEstado(0);
        productoExistente.setImagen("imagen");
        productoExistente.setPrecio(100);
        productoExistente.setProducto("producto inicial");
        productoExistente = productoService.save(productoExistente);

        // Actualizar los datos del producto
        productoExistente.setDescripcion("descripcion actualizada");
        productoExistente.setPrecio(150);
        productoExistente.setProducto("producto actualizado");

        // Guardar el producto actualizado
        Producto productoActualizado = productoService.save(productoExistente);

        // Verificar los cambios
        assertNotNull(productoActualizado);
        assertEquals("descripcion actualizada", productoActualizado.getDescripcion());
        assertEquals(150, productoActualizado.getPrecio());
        assertEquals("producto actualizado", productoActualizado.getProducto());
    }


    @Test
    @Rollback(false)
    public void deleteTest() {
        Categoria cate = new Categoria();
        cate.setCategoria("categoria");
        cate.setEstado(0);
        cate = categoriaService.save(cate);

        Producto productoAEliminar = new Producto();
        productoAEliminar.setCategoria(cate);
        productoAEliminar.setDescripcion("descripcion");
        productoAEliminar.setEstado(0);
        productoAEliminar.setImagen("imagen");
        productoAEliminar.setPrecio(100);
        productoAEliminar.setProducto("producto a eliminar");
        productoAEliminar = productoService.save(productoAEliminar);

        productoService.deleteById(productoAEliminar.getIdProducto());

        Optional<Producto> productoEliminado = productoService.findById(productoAEliminar.getIdProducto());
        assertTrue(productoEliminado.isEmpty());
    }

}
