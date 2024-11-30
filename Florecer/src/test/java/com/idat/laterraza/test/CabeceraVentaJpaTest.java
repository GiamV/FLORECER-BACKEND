
package com.idat.laterraza.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import com.idat.florecer.dao.ICabeceraVentaDao;
import com.idat.florecer.dao.IRolDao;
import com.idat.florecer.dao.ITipoPagoDao;
import com.idat.florecer.dao.IUsuarioDao;
import com.idat.florecer.entity.CabeceraVenta;
import com.idat.florecer.entity.Rol;
import com.idat.florecer.entity.TipoPago;
import com.idat.florecer.entity.Usuario;

import java.util.Date;
        
@SpringBootTest(classes = LaTerrazaSApplication.class)
public class CabeceraVentaJpaTest {
    
private static CabeceraVenta objcabecera;
    
	@Autowired
	private ICabeceraVentaDao cabeceraService;
	@Autowired
	private IRolDao rolservice;
	@Autowired
	private ITipoPagoDao tipopagoService;
	@Autowired
	private IUsuarioDao usuarioService; 
	
	@BeforeAll
	public static void Inicio(){
	    objcabecera= new CabeceraVenta();
	    System.out.print("BeforeAll --> Inicio");
	}
	
	@AfterAll
	public static void Final(){
	    objcabecera= null;
	    System.out.println("AfterAll --> Final()");
	}
	
	@Test 
	public void Mensaje(){
	    System.out.println("Prueba Inicial");
	    System.out.println("@Test --> PruebaInicial");
	}
	
	@Test 
	public void findAllTest(){
	    Iterable<CabeceraVenta> categoria = cabeceraService.findAll();
	    assertNotNull(categoria);
	}
	
	
	@Test 
	public void findById(){
	    long id=1;
	    Optional<CabeceraVenta> categoria = cabeceraService.findById(id);
	    assertNotNull(categoria);
	}
	
	@Test 
	@Rollback(false)
	public void addTest(){
	    Date fecha=new Date();
	    
	    TipoPago tipopago=new TipoPago();
	    tipopago.setTipoPago("efectivo");
	    tipopagoService.save(tipopago);
	    
	    
	    Rol rol = new Rol();
	    rol.setRol("usuario");
	    rol = rolservice.save(rol);
	    
	    Usuario usuario=new Usuario();
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
	    usuarioService.save(usuario);
	    
	    objcabecera.setBruto(0);
	    objcabecera.setEstado(1);
	    objcabecera.setFechamat(fecha);
	    objcabecera.setIdCabecera(Long.valueOf(1));
	    objcabecera.setIgv(0);
	    objcabecera.setNeto(0);
	    objcabecera.setTipoCabecera("Tipo cabecera");
	    objcabecera.setTipoPago(tipopago);
	    objcabecera.setUsuario(usuario);
	    
	    CabeceraVenta cabe = cabeceraService.save(objcabecera);
	    assertNotNull(cabe);
	}
	
	@Test
	@Rollback(false)
	public void updateTest() {
	    Date fecha = new Date();
	    
	    // Crear y guardar un TipoPago
	    TipoPago tipopago = new TipoPago();
	    tipopago.setTipoPago("efectivo");
	    tipopagoService.save(tipopago);
	    
	    // Crear y guardar un Rol
	    Rol rol = new Rol();
	    rol.setRol("usuario");
	    rol = rolservice.save(rol);
	    
	    // Crear y guardar un Usuario
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
	    usuario = usuarioService.save(usuario);  // Guardamos el usuario
	
	    // Crear y guardar una CabeceraVenta
	    CabeceraVenta cabecera = new CabeceraVenta();
	    cabecera.setBruto(0);
	    cabecera.setEstado(1);
	    cabecera.setFechamat(fecha);
	    cabecera.setIdCabecera(Long.valueOf(1));
	    cabecera.setIgv(0);
	    cabecera.setNeto(0);
	    cabecera.setTipoCabecera("Tipo cabecera");
	    cabecera.setTipoPago(tipopago);
	    cabecera.setUsuario(usuario);
	    cabecera = cabeceraService.save(cabecera);  // Guardamos la cabecera
	    
	
	    
	    cabecera.setTipoCabecera("Nuevo tipo cabecera");
	    cabecera.setBruto(100);
	    
	    // Guardamos las actualizaciones
	    cabecera = cabeceraService.save(cabecera);
	    
	    // Verificar que los datos han sido actualizados
	    Optional<CabeceraVenta> cabeceraActualizadaOpt = cabeceraService.findById(cabecera.getIdCabecera());
	    
	    // Comprobamos que el usuario y la cabecera estén presentes
	    assertTrue(cabeceraActualizadaOpt.isPresent(), "CabeceraVenta no encontrada");
	    
	    // Acceder a los valores de Usuario y CabeceraVenta de los Optional
	    CabeceraVenta cabeceraActualizada = cabeceraActualizadaOpt.get();
	    
	    // Verificamos las actualizaciones
	    
	    assertEquals("Nuevo tipo cabecera", cabeceraActualizada.getTipoCabecera());
	    assertEquals(100, cabeceraActualizada.getBruto());
	}
	
	
	
	@Test
	@Rollback(false)
	public void deleteTest() {
	    // Crear y guardar un TipoPago
	    TipoPago tipopago = new TipoPago();
	    tipopago.setTipoPago("efectivo");
	    tipopagoService.save(tipopago);
	
	    // Crear y guardar un Rol
	    Rol rol = new Rol();
	    rol.setRol("usuario");
	    rol = rolservice.save(rol);
	
	    // Crear y guardar un Usuario
	    Usuario usuario = new Usuario();
	    usuario.setApellido("apellido");
	    usuario.setContrasena("contrasena");
	    usuario.setDireccion("direccion");
	    usuario.setDni("12345678");
	    usuario.setEstado(0);
	    usuario.setNombre("nombre");
	    usuario.setUsuario("usuario");
	    usuario.setRol(rol);
	    usuarioService.save(usuario);
	
	    // Crear y guardar una CabeceraVenta
	    Date fecha = new Date();
	    CabeceraVenta cabecera = new CabeceraVenta();
	    cabecera.setBruto(0);
	    cabecera.setEstado(1);
	    cabecera.setFechamat(fecha);
	    cabecera.setTipoCabecera("Tipo cabecera");
	    cabecera.setTipoPago(tipopago);
	    cabecera.setUsuario(usuario);
	    cabecera = cabeceraService.save(cabecera);  // Guardamos la cabecera
	
	    // Asegurarse de que la cabecera fue guardada
	    assertNotNull(cabecera.getIdCabecera());
	
	    // Eliminar la CabeceraVenta
	    cabeceraService.delete(cabecera);
	
	    // Verificar que la cabecera ya no existe
	    Optional<CabeceraVenta> cabeceraOpt = cabeceraService.findById(cabecera.getIdCabecera());
	    assertFalse(cabeceraOpt.isPresent(), "La CabeceraVenta no fue eliminada correctamente");
	}
    
}
