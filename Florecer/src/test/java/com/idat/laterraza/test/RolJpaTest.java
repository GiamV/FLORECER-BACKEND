
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
import org.springframework.test.context.ActiveProfiles;

import com.idat.florecer.LaTerrazaSApplication;
import com.idat.florecer.dao.IRolDao;
import com.idat.florecer.entity.Rol;

@SpringBootTest(classes = LaTerrazaSApplication.class) // Especifica la clase principal de la aplicación
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
	    Iterable<Rol> rol = rolService.findAll();
	    assertNotNull(rol);
	}
	
	
	@Test 
	public void findById(){
	    long id=1;
	    Optional<Rol> rol = rolService.findById(id);
	    assertNotNull(rol);
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
	public void updateTest() {
	    // Crear y guardar un Rol
	    objrol.setIdRol(Long.valueOf(1));
	    objrol.setRol("usuario");
	
	    Rol rol = rolService.save(objrol);
	    assertNotNull(rol);  // Verificar que se guardó correctamente
	
	    // Actualizar el Rol
	    rol.setRol("admin");  // Modificamos el rol
	    Rol updatedRol = rolService.save(rol);  // Guardamos los cambios
	
	    // Verificar que el Rol fue actualizado correctamente
	    assertNotNull(updatedRol);
	    assertEquals("admin", updatedRol.getRol());  // Asegurarse de que el rol sea 'admin'
	}
	
	@Test
	@Rollback(false)
	public void deleteTest(){
	    // Primero, guardamos el rol
		Rol objrol = new Rol();
	    objrol.setRol("usuario");
	    
	    Rol rol = rolService.save(objrol);
	    
	    assertNotNull(rol.getIdRol());
	
	    // Eliminar el rol
	    rolService.delete(rol);
	
	    // Verificamos que el rol haya sido eliminado
	    Optional<Rol> deletedRol = rolService.findById(rol.getIdRol());
	    assertFalse(deletedRol.isPresent(), "La CabeceraVenta no fue eliminada correctamente");
	}
    

    
}
