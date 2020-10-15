package com.pbidenko.ifocommunalka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pbidenko.ifocommunalka.entity.LoginToken;

@Repository
public interface TokenRepository extends CrudRepository<LoginToken, Integer> {

	public LoginToken findByToken(String token);
}
