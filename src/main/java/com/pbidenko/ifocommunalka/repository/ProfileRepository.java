package com.pbidenko.ifocommunalka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pbidenko.ifocommunalka.entity.Usr;
import com.pbidenko.ifocommunalka.entity.UsrProfile;

@Repository 
public interface ProfileRepository extends CrudRepository<UsrProfile, Integer>{

	public UsrProfile findByUsr(Usr usr);

}
