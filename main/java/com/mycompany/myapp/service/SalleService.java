package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Salle;
import com.mycompany.myapp.repository.SalleRepository;
import com.mycompany.myapp.service.dto.SalleDTO;
import com.mycompany.myapp.service.mapper.SalleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Salle}.
 */
@Service
@Transactional
public class SalleService {

    private final Logger log = LoggerFactory.getLogger(SalleService.class);

    private final SalleRepository salleRepository;

    private final SalleMapper salleMapper;

    public SalleService(SalleRepository salleRepository, SalleMapper salleMapper) {
        this.salleRepository = salleRepository;
        this.salleMapper = salleMapper;
    }

    /**
     * Save a salle.
     *
     * @param salleDTO the entity to save.
     * @return the persisted entity.
     */
    public SalleDTO save(SalleDTO salleDTO) {
        log.debug("Request to save Salle : {}", salleDTO);
        Salle salle = salleMapper.toEntity(salleDTO);
        salle = salleRepository.save(salle);
        return salleMapper.toDto(salle);
    }

    /**
     * Partially update a salle.
     *
     * @param salleDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SalleDTO> partialUpdate(SalleDTO salleDTO) {
        log.debug("Request to partially update Salle : {}", salleDTO);

        return salleRepository
            .findById(salleDTO.getId())
            .map(
                existingSalle -> {
                    salleMapper.partialUpdate(existingSalle, salleDTO);

                    return existingSalle;
                }
            )
            .map(salleRepository::save)
            .map(salleMapper::toDto);
    }

    /**
     * Get all the salles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SalleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Salles");
        return salleRepository.findAll(pageable).map(salleMapper::toDto);
    }

    /**
     * Get one salle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalleDTO> findOne(Long id) {
        log.debug("Request to get Salle : {}", id);
        return salleRepository.findById(id).map(salleMapper::toDto);
    }

    /**
     * Delete the salle by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Salle : {}", id);
        salleRepository.deleteById(id);
    }
}
