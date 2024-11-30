
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
import com.idat.florecer.dao.ITipoPagoDao;
import com.idat.florecer.entity.TipoPago;
 
@SpringBootTest(classes = LaTerrazaSApplication.class) // Especifica la clase principal de la aplicación
public class TipoPagoJpaTest {

    private static TipoPago objtipopago;
    
    @Autowired
    private ITipoPagoDao tipopagoService;  
    
    @BeforeAll
    public static void Inicio(){
        objtipopago= new TipoPago();
        System.out.print("BeforeAll --> Inicio");
    }
    
    @AfterAll
    public static void Final(){
        objtipopago= null;
        System.out.println("AfterAll --> Final()");
    }
    
    @Test 
    public void Mensaje(){
        System.out.println("Prueba Inicial");
        System.out.println("@Test --> PruebaInicial");
    }
    
    @Test 
    public void findAllTest(){
        Iterable<TipoPago> tipopago = tipopagoService.findAll();
        assertNotNull(tipopago);
    }
    
    
    @Test 
    public void findById(){
        long id=1;
        Optional<TipoPago> tipopago = tipopagoService.findById(id);
        assertNotNull(tipopago);
    }
    
    @Test 
    @Rollback(false)
    public void addTest(){  
        
        objtipopago.setIdTipoPago(Long.valueOf(1));
        objtipopago.setTipoPago("efectivo");

        TipoPago detalle = tipopagoService.save(objtipopago);
        assertNotNull(detalle);
    }
    
    @Test
    @Rollback(false)
    public void updateTest() {
        TipoPago tipoPago = new TipoPago();
        tipoPago.setTipoPago("efectivo");
        tipoPago = tipopagoService.save(tipoPago);
        assertNotNull(tipoPago);

        Optional<TipoPago> optionalTipoPago = tipopagoService.findById(tipoPago.getIdTipoPago());
        assertTrue(optionalTipoPago.isPresent());

        TipoPago tipoPagoExistente = optionalTipoPago.get();

        tipoPagoExistente.setTipoPago("tarjeta");

        TipoPago tipoPagoActualizado = tipopagoService.save(tipoPagoExistente);

        assertNotNull(tipoPagoActualizado);
        assertEquals("tarjeta", tipoPagoActualizado.getTipoPago());
    }


    @Test
    @Rollback(false)
    public void deleteTest() {
        // Paso 1: Crear y guardar un TipoPago en la base de datos
        TipoPago tipoPago = new TipoPago();
        tipoPago.setTipoPago("efectivo");
        tipoPago = tipopagoService.save(tipoPago);
        assertNotNull(tipoPago);

        // Paso 2: Eliminar el TipoPago
        tipopagoService.deleteById(tipoPago.getIdTipoPago());

        // Paso 3: Verificar que el TipoPago fue eliminado
        Optional<TipoPago> optionalTipoPago = tipopagoService.findById(tipoPago.getIdTipoPago());
        assertFalse(optionalTipoPago.isPresent());
    }
 
}
