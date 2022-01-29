package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Modules;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Modules entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModulesRepository extends JpaRepository<Modules, Long> {}
