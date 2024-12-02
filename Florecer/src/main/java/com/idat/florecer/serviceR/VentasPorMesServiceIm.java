package com.idat.florecer.serviceR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idat.florecer.entity.CabeceraVenta;
import com.idat.florecer.entity.VentasPorMesCant;
import com.idat.florecer.entity.VentasPorMesDTO;
import com.idat.florecer.repository.IDetalleRepository;
import com.idat.florecer.repository.VentasPorMesRepository;

@Service
public class VentasPorMesServiceIm {
	@Autowired
	VentasPorMesRepository VentasRepo;
	
	public List<VentasPorMesDTO> DashboardVentasDinero() {
		return (List<VentasPorMesDTO>) VentasRepo.obtenerTotalPorMes();
	}
	
	

}
