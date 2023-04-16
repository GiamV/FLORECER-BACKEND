package com.idat.florecer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.idat.florecer.entity.GuiaEntrada;

public interface IGuiaEntradaRepository extends JpaRepository<GuiaEntrada, Long> {
	
	// @Query(value= "{call registrar_cab(:xcoduser)}",nativeQuery=true)
	// CabeceraVenta saveCabe(@Param("xcoduser")Long xcoduser);
	
	@Query(value= "{call consultaGuiavalidar(:xcoduser)}",nativeQuery=true)
	GuiaEntrada getCabeidUser(@Param("xcoduser")Long xcoduser);
	
	 @Query(value="{call GuiaHecha (:xcodguia,:xcoduser)}",nativeQuery=true)
	 void CompraGuia(@Param("xcodguia")Long xcodguia,@Param("xcoduser")Long xcoduser);
	
	@Query(value= "{call consultarguiacompra()}",nativeQuery=true)
	List<GuiaEntrada> guiaEntradaPendiente();

	//@Query(value= "{call consultacabv()}",nativeQuery=true)
	// List<CabeceraVenta> ListCabV();

}

