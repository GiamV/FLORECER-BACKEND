
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
import com.idat.florecer.dao.IRolDao;
import com.idat.florecer.dao.IUsuarioDao;
import com.idat.florecer.entity.Rol;
import com.idat.florecer.entity.Usuario;

@SpringBootTest(classes = LaTerrazaSApplication.class) // Especifica la clase principal de la aplicación
public class UsuarioJpaTest {

private static Usuario objusuario;
    
	@Autowired
	private IUsuarioDao usuarioService;       
	@Autowired
	private IRolDao rolservice;
	
	@BeforeAll
	public static void Inicio(){
	    objusuario= new Usuario();
	    System.out.print("BeforeAll --> Inicio");
	}
	
	@AfterAll
	public static void Final(){
	    objusuario= null;
	    System.out.println("AfterAll --> Final()");
	}
	
	@Test 
	public void Mensaje(){
	    System.out.println("Prueba Inicial");
	    System.out.println("@Test --> PruebaInicial");
	}
	
	@Test 
	public void findAllTest(){
	    Iterable<Usuario> usuario = usuarioService.findAll();
	    assertNotNull(usuario);
	}
	
	
	@Test 
	public void findById(){
	    long id=1;
	    Optional<Usuario> usuario = usuarioService.findById(id);
	    assertNotNull(usuario);
	}
	
	@Test
	@Rollback(false)
	public void addTest() {
	    // Crear y guardar el rol en la base de datos
	    Rol rol = new Rol();
	    rol.setRol("usuario");  // Establece los atributos del rol
	    rol = rolservice.save(rol);  // Persistir el rol en la base de datos
	
	    // Ahora crea el usuario y asigna el rol que ya ha sido guardado
	    objusuario.setApellido("apellido");
	    objusuario.setContrasena("contrasena");
	    objusuario.setDireccion("direccion");
	    objusuario.setDni("12345678");
	    objusuario.setEstado(0);
	    objusuario.setIdUsuario(Long.valueOf(1)); // Puede que también quieras usar un ID generado por la BD
	    objusuario.setNombre("nombre");
	    objusuario.setRol(rol);  // Asocia el rol previamente guardado
	    objusuario.setSexo("sexo");
	    objusuario.setTelefono("987654321");
	    objusuario.setUsuario("usuario");
	
	    // Guarda el usuario
	    Usuario user = usuarioService.save(objusuario);
	    assertNotNull(user);
	}
	
	
	@Test
	@Rollback(false)
	public void updateTest() {
	    Rol rol = new Rol();
	    rol.setRol("usuario");
	    rol = rolservice.save(rol);
	    assertNotNull(rol);

	    Usuario usuario = new Usuario();
	    usuario.setApellido("apellido");
	    usuario.setContrasena("contrasena");
	    usuario.setDireccion("direccion");
	    usuario.setDni("12345678");
	    usuario.setEstado(0);
	    usuario.setNombre("nombre");
	    usuario.setRol(rol);
	    usuario.setSexo("sexo");
	    usuario.setTelefono("987654321");
	    usuario.setUsuario("usuario");
	    usuario = usuarioService.save(usuario);
	    assertNotNull(usuario);

	    Optional<Usuario> optionalUsuario = usuarioService.findById(usuario.getIdUsuario());
	    assertTrue(optionalUsuario.isPresent());

	    Usuario usuarioExistente = optionalUsuario.get();
	    usuarioExistente.setApellido("nuevoApellido");
	    usuarioExistente.setDireccion("nuevaDireccion");
	    usuarioExistente.setTelefono("123456789");

	    Usuario usuarioActualizado = usuarioService.save(usuarioExistente);

	    assertNotNull(usuarioActualizado);
	    assertEquals("nuevoApellido", usuarioActualizado.getApellido());
	    assertEquals("nuevaDireccion", usuarioActualizado.getDireccion());
	    assertEquals("123456789", usuarioActualizado.getTelefono());
	}

    
}