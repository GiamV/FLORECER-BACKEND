package com.idat.florecer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.florecer.dao.IDetalleGuiaEntradaDao;
import com.idat.florecer.entity.DetalleGuiaEntrada;
import com.idat.florecer.entity.DetalleProveedor;

@Service
public class IDetalleGuiaEntradaServiceImpl implements IDetalleGuiaEntradaService {

	@Autowired
	private IDetalleGuiaEntradaDao detGuiaEntradaDao;
	@Transactional(readOnly=true)
	
	@Override
	public List<DetalleGuiaEntrada> findAll() {
		return (List<DetalleGuiaEntrada>)detGuiaEntradaDao.findAll();
	}

	@Override
	public void save(DetalleGuiaEntrada detalleGuiaEntrada) {
		detGuiaEntradaDao.save(detalleGuiaEntrada);

	}

	@Override
	public DetalleGuiaEntrada editarDetalleGuiaEntrada(Long id) {
		return detGuiaEntradaDao.findById(id).orElse(null);
	}

	@Override
	public void eliminarDetalleGuiaEntrada(Long id) {
		detGuiaEntradaDao.deleteById(id);
	}

	@Override
	public DetalleGuiaEntrada findById(Long id) {
		return detGuiaEntradaDao.findById(id).orElse(null);
	}

}
