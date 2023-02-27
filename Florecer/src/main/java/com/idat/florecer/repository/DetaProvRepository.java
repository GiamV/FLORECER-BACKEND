package com.idat.florecer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.idat.florecer.entity.DetalleProveedor;

public interface DetaProvRepository extends JpaRepository<DetalleProveedor, Long> {
	@Query(value= "{call buscar_proveedor(:xcodprov)}",nativeQuery=true)
	List<DetalleProveedor> findByIDP(@Param("xcodprov")Long xcodprov);
}
