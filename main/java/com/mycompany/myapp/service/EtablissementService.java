package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Etablissement;
import com.mycompany.myapp.repository.EtablissementRepository;
import com.mycompany.myapp.service.dto.EtablissementDTO;
import com.mycompany.myapp.service.mapper.EtablissementMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Etablissement}.
 */
@Service
@Transactional
public class EtablissementService {

    private final Logger log = LoggerFactory.getLogger(EtablissementService.class);

    private final EtablissementRepository etablissementRepository;

    private final EtablissementMapper etablissementMapper;

    public EtablissementService(EtablissementRepository etablissementRepository, EtablissementMapper etablissementMapper) {
        this.etablissementRepository = etablissementRepository;
        this.etablissementMapper = etablissementMapper;
    }

    /**
     * Save a etablissement.
     *
     * @param etablissementDTO the entity to save.
     * @return the persisted entity.
     */
    public EtablissementDTO save(EtablissementDTO etablissementDTO) {
        log.debug("Request to save Etablissement : {}", etablissementDTO);
        Etablissement etablissement = etablissementMapper.toEntity(etablissementDTO);
        etablissement = etablissementRepository.save(etablissement);
        return etablissementMapper.toDto(etablissement);
    }

    /**
     * Partially update a etablissement.
     *
     * @param etablissementDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EtablissementDTO> partialUpdate(EtablissementDTO etablissementDTO) {
        log.debug("Request to partially update Etablissement : {}", etablissementDTO);

        return etablissementRepository
            .findById(etablissementDTO.getId())
            .map(
                existingEtablissement -> {
                    etablissementMapper.partialUpdate(existingEtablissement, etablissementDTO);

                    return existingEtablissement;
                }
            )
            .map(etablissementRepository::save)
            .map(etablissementMapper::toDto);
    }

    /**
     * Get all the etablissements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EtablissementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Etablissements");
        return etablissementRepository.findAll(pageable).map(etablissementMapper::toDto);
    }

    /**
     * Get one etablissement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EtablissementDTO> findOne(Long id) {
        log.debug("Request to get Etablissement : {}", id);
        return etablissementRepository.findById(id).map(etablissementMapper::toDto);
    }

    /**
     * Delete the etablissement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Etablissement : {}", id);
        etablissementRepository.deleteById(id);
    }
}
