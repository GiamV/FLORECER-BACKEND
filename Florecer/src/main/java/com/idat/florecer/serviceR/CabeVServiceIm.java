package com.idat.florecer.serviceR;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idat.florecer.entity.CabeceraVenta;
import com.idat.florecer.repository.ICabeceraRepository;



@Service
public class CabeVServiceIm {
	@Autowired
	ICabeceraRepository CabeceraRepo;
	
	public CabeceraVenta saveCabecera(Long coduser) {
		return (CabeceraVenta) CabeceraRepo.saveCabe(coduser);
	}
	
	public CabeceraVenta getCabe(Long coduser) {
		return CabeceraRepo.getCabeidUser(coduser);
	}
	
	public void venderCabe(Long codcab,Long xcoduser) {
		CabeceraRepo.venderCabe(codcab,xcoduser);
	}

	
	public List<CabeceraVenta> findByCaU(Long codu) {
		return (List<CabeceraVenta>) CabeceraRepo.findByUV(codu);
	}
	
	public List<CabeceraVenta> ListCabV() {
		return (List<CabeceraVenta>) CabeceraRepo.ListCabV();
	}
	public List<CabeceraVenta> ListCabPend() {
		return (List<CabeceraVenta>) CabeceraRepo.ListCabPendiente();
	}
	public List<CabeceraVenta> ListCabTodos() {
		return (List<CabeceraVenta>) CabeceraRepo.ListCabTodos();
	}
	public List<Object> ConsultaVentas() {
		return (List<Object>) CabeceraRepo.ConsultaVentas();
	}
	
	public List<Object> ContarVentas() {
		return (List<Object>) CabeceraRepo.ContarVentas();
	}

}
