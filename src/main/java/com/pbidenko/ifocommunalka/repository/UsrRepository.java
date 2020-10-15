package com.pbidenko.ifocommunalka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pbidenko.ifocommunalka.entity.Usr;

@Repository
public interface UsrRepository extends CrudRepository<Usr, Integer> {

	Usr findByUsrname(String loginString);

	Usr findByEmail(String loginString);

}
