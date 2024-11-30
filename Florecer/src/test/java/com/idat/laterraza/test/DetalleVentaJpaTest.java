
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
import com.idat.florecer.dao.ICabeceraVentaDao;
import com.idat.florecer.dao.ICategoriaDao;
import com.idat.florecer.dao.IDetalleVentaDao;
import com.idat.florecer.dao.IProductoDao;
import com.idat.florecer.dao.IRolDao;
import com.idat.florecer.dao.ITipoPagoDao;
import com.idat.florecer.dao.IUsuarioDao;
import com.idat.florecer.entity.CabeceraVenta;
import com.idat.florecer.entity.Categoria;
import com.idat.florecer.entity.DetalleVenta;
import com.idat.florecer.entity.Producto;
import com.idat.florecer.entity.Rol;
import com.idat.florecer.entity.TipoPago;
import com.idat.florecer.entity.Usuario;

import java.util.Date;

@SpringBootTest(classes = LaTerrazaSApplication.class) // Especifica la clase principal de la aplicación
public class DetalleVentaJpaTest {
    
private static DetalleVenta objdetalle;
    
    @Autowired
    private IDetalleVentaDao detalleService; 
    @Autowired
    private IRolDao rolservice;
    @Autowired
    private ITipoPagoDao tipopagoService;
    @Autowired
    private IUsuarioDao usuarioService; 
    @Autowired
    private ICabeceraVentaDao cabeceraService;
    @Autowired
    private IProductoDao productoService;
    @Autowired
    private ICategoriaDao categoriaService;
    
    @BeforeAll
    public static void Inicio(){
        objdetalle= new DetalleVenta();
        System.out.print("BeforeAll --> Inicio");
    }
    
    @AfterAll
    public static void Final(){
        objdetalle= null;
        System.out.println("AfterAll --> Final()");
    }
    
    @Test 
    public void Mensaje(){
        System.out.println("Prueba Inicial");
        System.out.println("@Test --> PruebaInicial");
    }
    
    @Test 
    public void findAllTest(){
        Iterable<DetalleVenta> detalle = detalleService.findAll();
        assertNotNull(detalle);
    }
    
    
    @Test 
    public void findById(){
        long id=1;
        Optional<DetalleVenta> detalle = detalleService.findById(id);
        assertNotNull(detalle);
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
        
        CabeceraVenta objcabecera=new CabeceraVenta();
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
        
        Categoria cate =new Categoria();
        cate.setCategoria("categoria");
        cate.setEstado(0);
        cate=categoriaService.save(cate);
        
        Producto prod=new Producto();
        prod.setCategoria(cate);
        prod.setDescripcion("descripcion");
        prod.setEstado(0);
        prod.setIdProducto(Long.valueOf(1));
        prod.setImagen("imagen");
        prod.setPrecio(0);
        prod.setProducto("producto");
        prod=productoService.save(prod);
        
        objdetalle.setCabecera(cabe);
        objdetalle.setCantidad(0);
        objdetalle.setEstado(0);
        objdetalle.setIdDetalleVenta(Long.valueOf(1));
        objdetalle.setPrecio(0);
        objdetalle.setProducto(prod);
        
        
        DetalleVenta detalle = detalleService.save(objdetalle);
        assertNotNull(detalle);
    }
    
    @Test
    @Rollback(false)
    public void updateTest() {
        // Crear y guardar los datos necesarios para la entidad principal
        Date fecha = new Date();

        // TipoPago
        TipoPago tipopago = new TipoPago();
        tipopago.setTipoPago("efectivo");
        tipopago = tipopagoService.save(tipopago);

        // Rol
        Rol rol = new Rol();
        rol.setRol("usuario");
        rol = rolservice.save(rol);

        // Usuario
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

        // CabeceraVenta
        CabeceraVenta objcabecera = new CabeceraVenta();
        objcabecera.setBruto(0);
        objcabecera.setEstado(1);
        objcabecera.setFechamat(fecha);
        objcabecera.setIgv(0);
        objcabecera.setNeto(0);
        objcabecera.setTipoCabecera("Tipo cabecera");
        objcabecera.setTipoPago(tipopago);
        objcabecera.setUsuario(usuario);
        objcabecera = cabeceraService.save(objcabecera);

        // Categoria
        Categoria cate = new Categoria();
        cate.setCategoria("categoria");
        cate.setEstado(0);
        cate = categoriaService.save(cate);

        // Producto
        Producto prod = new Producto();
        prod.setCategoria(cate);
        prod.setDescripcion("descripcion");
        prod.setEstado(0);
        prod.setImagen("imagen");
        prod.setPrecio(0);
        prod.setProducto("producto");
        prod = productoService.save(prod);

        // DetalleVenta
        DetalleVenta objdetalle = new DetalleVenta();
        objdetalle.setCabecera(objcabecera);
        objdetalle.setCantidad(1);
        objdetalle.setEstado(0);
        objdetalle.setPrecio(100);
        objdetalle.setProducto(prod);
        objdetalle = detalleService.save(objdetalle);

        // Actualizar el DetalleVenta
        objdetalle.setCantidad(5);  // Cambiar cantidad
        objdetalle.setPrecio(150); // Cambiar precio
        objdetalle.setEstado(1);   // Cambiar estado
        DetalleVenta updatedDetalle = detalleService.save(objdetalle);

        // Verificar los cambios
        assertNotNull(updatedDetalle);
        assertEquals(5, updatedDetalle.getCantidad());
        assertEquals(150, updatedDetalle.getPrecio());
        assertEquals(1, updatedDetalle.getEstado());
    }

    
    @Test
    @Rollback(false)
    public void deleteTest() {
        // Crear y guardar los datos necesarios para la entidad principal
        Date fecha = new Date();

        // TipoPago
        TipoPago tipopago = new TipoPago();
        tipopago.setTipoPago("efectivo");
        tipopago = tipopagoService.save(tipopago);

        // Rol
        Rol rol = new Rol();
        rol.setRol("usuario");
        rol = rolservice.save(rol);

        // Usuario
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

        // CabeceraVenta
        CabeceraVenta objcabecera = new CabeceraVenta();
        objcabecera.setBruto(0);
        objcabecera.setEstado(1);
        objcabecera.setFechamat(fecha);
        objcabecera.setIgv(0);
        objcabecera.setNeto(0);
        objcabecera.setTipoCabecera("Tipo cabecera");
        objcabecera.setTipoPago(tipopago);
        objcabecera.setUsuario(usuario);
        objcabecera = cabeceraService.save(objcabecera);

        // Categoria
        Categoria cate = new Categoria();
        cate.setCategoria("categoria");
        cate.setEstado(0);
        cate = categoriaService.save(cate);

        // Producto
        Producto prod = new Producto();
        prod.setCategoria(cate);
        prod.setDescripcion("descripcion");
        prod.setEstado(0);
        prod.setImagen("imagen");
        prod.setPrecio(0);
        prod.setProducto("producto");
        prod = productoService.save(prod);

        // DetalleVenta
        DetalleVenta objdetalle = new DetalleVenta();
        objdetalle.setCabecera(objcabecera);
        objdetalle.setCantidad(1);
        objdetalle.setEstado(0);
        objdetalle.setPrecio(100);
        objdetalle.setProducto(prod);
        objdetalle = detalleService.save(objdetalle);

        // Eliminar el DetalleVenta
        detalleService.delete(objdetalle);

        // Verificar que fue eliminado
        Optional<DetalleVenta> deletedDetalle = detalleService.findById(objdetalle.getIdDetalleVenta());
        assertFalse(deletedDetalle.isPresent(), "El DetalleVenta no fue eliminado correctamente");
    }

    
}
