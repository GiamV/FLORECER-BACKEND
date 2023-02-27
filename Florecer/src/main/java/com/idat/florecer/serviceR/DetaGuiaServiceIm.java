package com.idat.florecer.serviceR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idat.florecer.entity.DetalleGuiaEntrada;

import com.idat.florecer.repository.IDetalleGuiaRepository;


@Service
public class DetaGuiaServiceIm {
	@Autowired
	IDetalleGuiaRepository DetalleGuiaRepo;
	
	public List<DetalleGuiaEntrada> findByCaU(Long codcab) {
		return (List<DetalleGuiaEntrada>) DetalleGuiaRepo.findByCabV(codcab);
	}

}
