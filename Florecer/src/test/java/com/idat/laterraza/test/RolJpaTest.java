
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

import com.idat.florecer.dao.IRolDao;
import com.idat.florecer.entity.Rol;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class RolJpaTest {
    
    private static Rol objrol;
    
    @Autowired
    private IRolDao rolService;  
    
    @BeforeAll
    public static void Inicio(){
        objrol= new Rol();
        System.out.print("BeforeAll --> Inicio");
    }
    
    @AfterAll
    public static void Final(){
        objrol= null;
        System.out.println("AfterAll --> Final()");
    }
    
    @Test 
    public void Mensaje(){
        System.out.println("Prueba Inicial");
        System.out.println("@Test --> PruebaInicial");
    }
    
    @Test 
    public void findAllTest(){
        Iterable<Rol> categoria = rolService.findAll();
        assertNotNull(categoria);
    }
    
    
    @Test 
    public void findById(){
        long id=1;
        Optional<Rol> categoria = rolService.findById(id);
        assertNotNull(categoria);
    }
    
    @Test 
    @Rollback(false)
    public void addTest(){
        objrol.setIdRol(Long.valueOf(1));
        objrol.setRol("usuario");

        Rol rol = rolService.save(objrol);
        assertNotNull(rol);
    }
    
    @Test 
    @Rollback(false)
    public void updateTest(){
        objrol.setIdRol(Long.valueOf(1));
        objrol.setRol("usuario");

        Rol rol = rolService.save(objrol);
        assertNotNull(rol);
    }
    
    @Test 
    @Rollback(false)
    public void deleteTest(){
        objrol.setIdRol(Long.valueOf(1));
        objrol.setRol("usuario");

        Rol rol = rolService.save(objrol);
        assertNotNull(rol);
    }
    
}
