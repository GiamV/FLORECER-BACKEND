package com.idat.florecer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.florecer.dao.IGuiaEntradaDao;
import com.idat.florecer.entity.GuiaEntrada;


@Service
public class IGuiaEntradaServiceImpl implements IGuiaEntradaService {
	
	@Autowired
	private IGuiaEntradaDao guiaEntradaDao;
	@Transactional(readOnly=true)
	
	@Override
	public List<GuiaEntrada> findAll() {
		return (List<GuiaEntrada>)guiaEntradaDao.findAll();
	}

	@Override
	public void save(GuiaEntrada guiaEntrada) {
		guiaEntradaDao.save(guiaEntrada);
	}

	@Override
	public GuiaEntrada editarGuiaEntrada(Long id) {
		return guiaEntradaDao.findById(id).orElse(null);
	}

	@Override
	public void eliminarGuiaEntrada(Long id) {
		guiaEntradaDao.deleteById(id);
	}

	@Override
	public GuiaEntrada findById(Long id) {
		return guiaEntradaDao.findById(id).orElse(null);
	}

}
