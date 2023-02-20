package com.idat.florecer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.idat.florecer.entity.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
	@Query(value= "{call inicio_s(:xuser, :xpass)}",nativeQuery=true)
	Usuario inicios(@Param("xuser")String xuser,
							@Param("xpass") String xpass);
	@Query(value= "{call buscar_u(:xuser)}",nativeQuery=true)
	Usuario findByUser(@Param("xuser")String xuser);

}
