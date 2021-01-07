package com.ensa.repositories;

import com.ensa.beans.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte,Integer> {
}
