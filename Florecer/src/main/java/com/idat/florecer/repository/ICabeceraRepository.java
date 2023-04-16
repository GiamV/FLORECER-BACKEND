package com.idat.florecer.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.idat.florecer.entity.CabeceraVenta;


public interface ICabeceraRepository extends JpaRepository<CabeceraVenta, Long> {
	
	@Query(value= "{call registrar_cab(:xcoduser)}",nativeQuery=true)
	CabeceraVenta saveCabe(@Param("xcoduser")Long xcoduser);
	
	@Query(value= "{call consultar_cab(:xcoduser)}",nativeQuery=true)
	CabeceraVenta getCabeidUser(@Param("xcoduser")Long xcoduser);
	
	@Query(value="{call CompraHecha (:xcodcab,:xcoduser)}",nativeQuery=true)
	void venderCabe(@Param("xcodcab")Long xcodcab,@Param("xcoduser")Long xcoduser);
	
	@Query(value= "{call consultaventa(:xcoduser)}",nativeQuery=true)
	List<CabeceraVenta> findByUV(@Param("xcoduser")Long xcoduser);
	
	@Query(value= "{call consultacabv()}",nativeQuery=true)
	List<CabeceraVenta> ListCabV();
	@Query(value= "{call consultacabpendiente()}",nativeQuery=true)
	List<CabeceraVenta> ListCabPendiente();
	
	@Query(value= "{call consultacabtodos()}",nativeQuery=true)
	List<CabeceraVenta> ListCabTodos();
	
	@Query(value= "{call consultarventames()}",nativeQuery=true)
	List<Object> ConsultaVentas();
	@Query(value= "{call contarventames()}",nativeQuery=true)
	List<Object> ContarVentas();

}


