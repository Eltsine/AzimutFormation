package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Periode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Periode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodeRepository extends JpaRepository<Periode, Long> {}
