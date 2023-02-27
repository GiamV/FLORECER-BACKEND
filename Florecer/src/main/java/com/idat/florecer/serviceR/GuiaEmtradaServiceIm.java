package com.idat.florecer.serviceR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.idat.florecer.repository.IGuiaEntradaRepository;

@Service
public class GuiaEmtradaServiceIm {
	@Autowired
	IGuiaEntradaRepository GuiaRepo;
	
	
	public void CompraCabe(Long codcab,Long xcoduser) {
		GuiaRepo.CompraGuia(codcab,xcoduser);
	}

}
