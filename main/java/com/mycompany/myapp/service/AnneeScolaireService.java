package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.AnneeScolaire;
import com.mycompany.myapp.repository.AnneeScolaireRepository;
import com.mycompany.myapp.service.dto.AnneeScolaireDTO;
import com.mycompany.myapp.service.mapper.AnneeScolaireMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnneeScolaire}.
 */
@Service
@Transactional
public class AnneeScolaireService {

    private final Logger log = LoggerFactory.getLogger(AnneeScolaireService.class);

    private final AnneeScolaireRepository anneeScolaireRepository;

    private final AnneeScolaireMapper anneeScolaireMapper;

    public AnneeScolaireService(AnneeScolaireRepository anneeScolaireRepository, AnneeScolaireMapper anneeScolaireMapper) {
        this.anneeScolaireRepository = anneeScolaireRepository;
        this.anneeScolaireMapper = anneeScolaireMapper;
    }

    /**
     * Save a anneeScolaire.
     *
     * @param anneeScolaireDTO the entity to save.
     * @return the persisted entity.
     */
    public AnneeScolaireDTO save(AnneeScolaireDTO anneeScolaireDTO) {
        log.debug("Request to save AnneeScolaire : {}", anneeScolaireDTO);
        AnneeScolaire anneeScolaire = anneeScolaireMapper.toEntity(anneeScolaireDTO);
        anneeScolaire = anneeScolaireRepository.save(anneeScolaire);
        return anneeScolaireMapper.toDto(anneeScolaire);
    }

    /**
     * Partially update a anneeScolaire.
     *
     * @param anneeScolaireDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnneeScolaireDTO> partialUpdate(AnneeScolaireDTO anneeScolaireDTO) {
        log.debug("Request to partially update AnneeScolaire : {}", anneeScolaireDTO);

        return anneeScolaireRepository
            .findById(anneeScolaireDTO.getId())
            .map(
                existingAnneeScolaire -> {
                    anneeScolaireMapper.partialUpdate(existingAnneeScolaire, anneeScolaireDTO);

                    return existingAnneeScolaire;
                }
            )
            .map(anneeScolaireRepository::save)
            .map(anneeScolaireMapper::toDto);
    }

    /**
     * Get all the anneeScolaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnneeScolaireDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnneeScolaires");
        return anneeScolaireRepository.findAll(pageable).map(anneeScolaireMapper::toDto);
    }

    /**
     * Get one anneeScolaire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnneeScolaireDTO> findOne(Long id) {
        log.debug("Request to get AnneeScolaire : {}", id);
        return anneeScolaireRepository.findById(id).map(anneeScolaireMapper::toDto);
    }

    /**
     * Delete the anneeScolaire by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnneeScolaire : {}", id);
        anneeScolaireRepository.deleteById(id);
    }
}
