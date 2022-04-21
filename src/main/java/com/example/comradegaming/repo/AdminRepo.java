package com.example.comradegaming.repo;

import com.example.comradegaming.entities.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends CrudRepository<Admin, String> {

}
