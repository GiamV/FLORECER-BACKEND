package com.idat.florecer.serviceR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idat.florecer.entity.DetalleGuiaEntrada;
import com.idat.florecer.entity.GuiaEntrada;
import com.idat.florecer.repository.IGuiaEntradaRepository;

@Service
public class GuiaEmtradaServiceIm {
	@Autowired
	IGuiaEntradaRepository GuiaRepo;
	
	
	public void CompraCabe(Long codcab,Long xcoduser) {
		GuiaRepo.CompraGuia(codcab,xcoduser);
	}
	public GuiaEntrada FindByUserAndEst(Long xcoduser) {
		return GuiaRepo.getCabeidUser(xcoduser);
	}
	
	public List<GuiaEntrada> listarGuiaPen() {
		return (List<GuiaEntrada>) GuiaRepo.guiaEntradaPendiente();
	}
	

}
