package com.idat.florecer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.idat.florecer.entity.VentasPorMesCant;

public interface VentasPorMesCantRepo extends JpaRepository<VentasPorMesCant,Long> {
	@Query(value= "{call contarVentasPorMes()}",nativeQuery=true)
	List<VentasPorMesCant> obtenerTotalPorMesCant();

}
