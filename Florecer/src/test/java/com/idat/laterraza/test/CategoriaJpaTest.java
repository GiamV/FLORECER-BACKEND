package com.idat.laterraza.test;

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
import org.springframework.test.annotation.Rollback;

import com.idat.florecer.dao.ICategoriaDao;
import com.idat.florecer.entity.Categoria;
import com.idat.florecer.service.ICategoriaService;
import com.idat.florecer.service.ICategoriaServiceImpl;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
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
    public void updateTest(){
        objcategoria.setIdCategoria(Long.valueOf(2));
        objcategoria.setCategoria("verduras");
        objcategoria.setEstado(1);
        Categoria categoria = categoriaService.save(objcategoria);
        assertNotNull(categoria);
    }
    
    @Test 
    @Rollback(false)
    public void deleteTest(){
        objcategoria.setIdCategoria(Long.valueOf(2));
        objcategoria.setCategoria("verduras");
        objcategoria.setEstado(0);
        Categoria categoria = categoriaService.save(objcategoria);
        assertNotNull(categoria);
    }
        

    
    
    

}
