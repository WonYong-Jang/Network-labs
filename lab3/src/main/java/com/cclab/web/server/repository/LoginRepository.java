package com.cclab.web.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cclab.web.server.domain.Login;

public interface LoginRepository extends JpaRepository<Login,Long> {

}
