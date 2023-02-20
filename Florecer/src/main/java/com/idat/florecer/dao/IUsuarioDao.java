package com.idat.florecer.dao;

import org.springframework.data.repository.CrudRepository;

import com.idat.florecer.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long  > {

}
