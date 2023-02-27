package com.idat.florecer.serviceR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idat.florecer.entity.DetalleProveedor;

import com.idat.florecer.repository.DetaProvRepository;


@Service
public class DetaProvServiceIm {
	
	@Autowired
	DetaProvRepository detaProvRepo;
	
	public List<DetalleProveedor> findByIDP(Long xcodprov) {
		return (List<DetalleProveedor>) detaProvRepo.findByIDP(xcodprov);
	}

}
