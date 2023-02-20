package com.idat.florecer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.florecer.dao.IDetalleVentaDao;
import com.idat.florecer.entity.DetalleVenta;
@Service
public class IDetalleVentaServiceImpl implements IDetalleVentaService {

	@Autowired
	private IDetalleVentaDao detalleVentaDao;
	
	@Transactional(readOnly=true)
	
	@Override
	public List<DetalleVenta> findAll() {
		
		return (List<DetalleVenta>)detalleVentaDao.findAll();
	}
	@Transactional
	@Override
	public void save(DetalleVenta detalleVenta) {
		detalleVentaDao.save(detalleVenta);
		
	}
	@Transactional
	@Override
	public DetalleVenta editarDetalleVenta(Long id) {
		// TODO Auto-generated method stub
		return detalleVentaDao.findById(id).orElse(null);
	}
	@Transactional
	@Override
	public void eliminarDetalleVenta(Long id) {
		detalleVentaDao.deleteById(id);
		
	}
	@Override
	public DetalleVenta findById(Long id) {
		// TODO Auto-generated method stub
		return detalleVentaDao.findById(id).orElse(null);
	}


}
