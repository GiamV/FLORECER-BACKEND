package com.idat.florecer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idat.florecer.dao.IDetalleProveedorDao;
import com.idat.florecer.entity.DetalleProveedor;

@Service
public class IDetalleProveedorServiceImpl implements IDetalleProveedorService {

	@Autowired
	private IDetalleProveedorDao detalleProveedorDao;
	@Transactional(readOnly=true)
	
	@Override
	public List<DetalleProveedor> findAll() {
		return (List<DetalleProveedor>)detalleProveedorDao.findAll();
	}

	@Override
	public void save(DetalleProveedor detalleProveedor) {
		detalleProveedorDao.save(detalleProveedor);

	}

	@Override
	public DetalleProveedor editarDetalleProveedor(Long id) {
		return detalleProveedorDao.findById(id).orElse(null);
	}

	@Override
	public void eliminarDetalleProveedor(Long id) {
		detalleProveedorDao.deleteById(id);

	}

	@Override
	public DetalleProveedor findById(Long id) {
		return detalleProveedorDao.findById(id).orElse(null);
	}

}
