package com.idat.florecer.dao;

import org.springframework.data.repository.CrudRepository;

import com.idat.florecer.entity.Categoria;


public interface ICategoriaDao extends CrudRepository<Categoria, Long  > {

}
