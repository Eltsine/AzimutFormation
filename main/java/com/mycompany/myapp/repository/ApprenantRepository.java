package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Apprenant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Apprenant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApprenantRepository extends JpaRepository<Apprenant, Long> {}
