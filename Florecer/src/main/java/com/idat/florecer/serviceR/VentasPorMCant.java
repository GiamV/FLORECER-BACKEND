package com.idat.florecer.serviceR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idat.florecer.entity.VentasPorMesCant;
import com.idat.florecer.repository.VentasPorMesCantRepo;
import com.idat.florecer.repository.VentasPorMesRepository;

@Service
public class VentasPorMCant {
	@Autowired
	VentasPorMesCantRepo VentasRepo;
	
	public List<VentasPorMesCant> DashboardVentasCant() {
		return (List<VentasPorMesCant>) VentasRepo.obtenerTotalPorMesCant();
	}

}
