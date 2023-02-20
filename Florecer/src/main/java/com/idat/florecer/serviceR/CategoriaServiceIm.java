package com.idat.florecer.serviceR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idat.florecer.entity.Producto;
import com.idat.florecer.repository.IProductoRepository;

@Service
public class CategoriaServiceIm {
	@Autowired
	IProductoRepository productoRepo;
	
	public List<Producto> findByCate(Long codcat) {
		return (List<Producto>) productoRepo.findByCat(codcat);
	}

}
