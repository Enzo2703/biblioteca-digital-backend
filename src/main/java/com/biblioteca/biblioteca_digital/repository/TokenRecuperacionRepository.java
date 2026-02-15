package com.biblioteca.biblioteca_digital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biblioteca.biblioteca_digital.model.TokenRecuperacion;
import com.biblioteca.biblioteca_digital.model.Usuario;

import jakarta.transaction.Transactional;

@Repository
public interface TokenRecuperacionRepository extends JpaRepository<TokenRecuperacion, Long>{
	TokenRecuperacion findByToken(String token);
	 
	 @Transactional
	 @Modifying
	 @Query("UPDATE TokenRecuperacion t SET t.usado = true WHERE t.usuario = :usuario AND t.usado = false")
	 void invalidarTokensActivos(@Param("usuario") Usuario usuario);

}
