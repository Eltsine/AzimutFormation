package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Specialite;
import com.mycompany.myapp.repository.SpecialiteRepository;
import com.mycompany.myapp.service.dto.SpecialiteDTO;
import com.mycompany.myapp.service.mapper.SpecialiteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Specialite}.
 */
@Service
@Transactional
public class SpecialiteService {

    private final Logger log = LoggerFactory.getLogger(SpecialiteService.class);

    private final SpecialiteRepository specialiteRepository;

    private final SpecialiteMapper specialiteMapper;

    public SpecialiteService(SpecialiteRepository specialiteRepository, SpecialiteMapper specialiteMapper) {
        this.specialiteRepository = specialiteRepository;
        this.specialiteMapper = specialiteMapper;
    }

    /**
     * Save a specialite.
     *
     * @param specialiteDTO the entity to save.
     * @return the persisted entity.
     */
    public SpecialiteDTO save(SpecialiteDTO specialiteDTO) {
        log.debug("Request to save Specialite : {}", specialiteDTO);
        Specialite specialite = specialiteMapper.toEntity(specialiteDTO);
        specialite = specialiteRepository.save(specialite);
        return specialiteMapper.toDto(specialite);
    }

    /**
     * Partially update a specialite.
     *
     * @param specialiteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SpecialiteDTO> partialUpdate(SpecialiteDTO specialiteDTO) {
        log.debug("Request to partially update Specialite : {}", specialiteDTO);

        return specialiteRepository
            .findById(specialiteDTO.getId())
            .map(
                existingSpecialite -> {
                    specialiteMapper.partialUpdate(existingSpecialite, specialiteDTO);

                    return existingSpecialite;
                }
            )
            .map(specialiteRepository::save)
            .map(specialiteMapper::toDto);
    }

    /**
     * Get all the specialites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SpecialiteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Specialites");
        return specialiteRepository.findAll(pageable).map(specialiteMapper::toDto);
    }

    /**
     * Get one specialite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SpecialiteDTO> findOne(Long id) {
        log.debug("Request to get Specialite : {}", id);
        return specialiteRepository.findById(id).map(specialiteMapper::toDto);
    }

    /**
     * Delete the specialite by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Specialite : {}", id);
        specialiteRepository.deleteById(id);
    }
}
