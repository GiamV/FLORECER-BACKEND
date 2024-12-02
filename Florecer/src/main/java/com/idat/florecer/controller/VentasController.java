package com.idat.florecer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idat.florecer.entity.VentasPorMesCant;
import com.idat.florecer.entity.VentasPorMesDTO;
import com.idat.florecer.serviceR.VentasPorMCant;
import com.idat.florecer.serviceR.VentasPorMesServiceIm;

@CrossOrigin(origins= {"http://localhost/4200"})
@RestController
@RequestMapping("/dash")
public class VentasController {
	
	@Autowired
    private VentasPorMesServiceIm ventasService;
	@Autowired
    private VentasPorMCant ventasCantSenvice ;

    @GetMapping("/ventas/monto")
    public List<VentasPorMesDTO> obtenerVentasPorMes() {
        return ventasService.DashboardVentasDinero();
    }

    @GetMapping("/ventas/cantidad")
    public List<VentasPorMesCant> obtenerVentasPorMesCant() {
        return ventasCantSenvice.DashboardVentasCant();
    }
}
