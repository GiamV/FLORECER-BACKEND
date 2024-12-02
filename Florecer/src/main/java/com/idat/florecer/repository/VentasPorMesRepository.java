package com.idat.florecer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.idat.florecer.entity.CabeceraVenta;
import com.idat.florecer.entity.VentasPorMesCant;
import com.idat.florecer.entity.VentasPorMesDTO;

public interface VentasPorMesRepository extends JpaRepository<VentasPorMesDTO, Long> {
	
	@Query(value= "{call obtenerTotalPorMes()}",nativeQuery=true)
	List<VentasPorMesDTO> obtenerTotalPorMes();
	
	

}