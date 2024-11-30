package com.idat.laterraza.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import com.idat.florecer.entity.Categoria;
import com.idat.florecer.service.ICategoriaService;
import com.idat.florecer.service.ICategoriaServiceImpl;

@SpringBootTest(classes = LaTerrazaSApplication.class) // Especifica la clase principal de la aplicación
public class CategoriaJpaTest {
	
private static Categoria objcategoria;
	
    @Autowired
    private ICategoriaDao categoriaService;
	
    @BeforeAll
    public static void Inicio(){
        objcategoria= new Categoria();
        System.out.print("BeforeAll --> Inicio");
    }
    
    @AfterAll
    public static void Final(){
        objcategoria= null;
        System.out.println("AfterAll --> Final()");
    }
    
    @Test 
    public void Mensaje(){
        System.out.println("Prueba Inicial");
        System.out.println("@Test --> PruebaInicial");
    }
    
    @Test 
    public void findAllTest(){
        Iterable<Categoria> categoria = categoriaService.findAll();
        assertNotNull(categoria);
    }
    
    
    @Test 
    public void findById(){
        long id=1;
        Optional<Categoria> categoria = categoriaService.findById(id);
        assertNotNull(categoria);
    }
    
    @Test 
    @Rollback(false)
    public void addTest(){
        objcategoria.setCategoria("Ropa de Niño");
        objcategoria.setEstado(1);
        Categoria categoria = categoriaService.save(objcategoria);
        assertNotNull(categoria);
    }
    
    @Test
    @Rollback(false)
    public void updateTest() {
        // Crear y guardar una categoría
        objcategoria.setCategoria("Ropa de Niño");
        objcategoria.setEstado(1);

        Categoria categoria = categoriaService.save(objcategoria);
        assertNotNull(categoria);  // Verificar que se guardó correctamente

        // Actualizar la categoría
        categoria.setCategoria("Ropa de Bebé");  // Modificar el nombre de la categoría
        categoria.setEstado(0);  // Cambiar el estado
        Categoria updatedCategoria = categoriaService.save(categoria);  // Guardar los cambios

        // Verificar que la categoría fue actualizada correctamente
        assertNotNull(updatedCategoria);
        assertEquals("Ropa de Bebé", updatedCategoria.getCategoria());
        assertEquals(0, updatedCategoria.getEstado());
    }

    
    @Test
    @Rollback(false)
    public void deleteTest() {
        // Crear y guardar una categoría
        objcategoria.setCategoria("Ropa de Niño");
        objcategoria.setEstado(1);

        Categoria categoria = categoriaService.save(objcategoria);
        assertNotNull(categoria);  // Verificar que se guardó correctamente
        assertNotNull(categoria.getIdCategoria());  // Asegurar que tiene un ID generado

        // Eliminar la categoría
        categoriaService.delete(categoria);

        // Verificar que la categoría fue eliminada correctamente
        Optional<Categoria> deletedCategoria = categoriaService.findById(categoria.getIdCategoria());
        assertFalse(deletedCategoria.isPresent(), "La categoría no fue eliminada correctamente");
    }

        

    
    
    

}
