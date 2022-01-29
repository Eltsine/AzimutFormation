package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AnneeScolaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnneeScolaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnneeScolaireRepository extends JpaRepository<AnneeScolaire, Long> {}
