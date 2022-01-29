package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Formateur;
import com.mycompany.myapp.repository.FormateurRepository;
import com.mycompany.myapp.service.dto.FormateurDTO;
import com.mycompany.myapp.service.mapper.FormateurMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Formateur}.
 */
@Service
@Transactional
public class FormateurService {

    private final Logger log = LoggerFactory.getLogger(FormateurService.class);

    private final FormateurRepository formateurRepository;

    private final FormateurMapper formateurMapper;

    public FormateurService(FormateurRepository formateurRepository, FormateurMapper formateurMapper) {
        this.formateurRepository = formateurRepository;
        this.formateurMapper = formateurMapper;
    }

    /**
     * Save a formateur.
     *
     * @param formateurDTO the entity to save.
     * @return the persisted entity.
     */
    public FormateurDTO save(FormateurDTO formateurDTO) {
        log.debug("Request to save Formateur : {}", formateurDTO);
        Formateur formateur = formateurMapper.toEntity(formateurDTO);
        formateur = formateurRepository.save(formateur);
        return formateurMapper.toDto(formateur);
    }

    /**
     * Partially update a formateur.
     *
     * @param formateurDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FormateurDTO> partialUpdate(FormateurDTO formateurDTO) {
        log.debug("Request to partially update Formateur : {}", formateurDTO);

        return formateurRepository
            .findById(formateurDTO.getId())
            .map(
                existingFormateur -> {
                    formateurMapper.partialUpdate(existingFormateur, formateurDTO);

                    return existingFormateur;
                }
            )
            .map(formateurRepository::save)
            .map(formateurMapper::toDto);
    }

    /**
     * Get all the formateurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FormateurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Formateurs");
        return formateurRepository.findAll(pageable).map(formateurMapper::toDto);
    }

    /**
     * Get one formateur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FormateurDTO> findOne(Long id) {
        log.debug("Request to get Formateur : {}", id);
        return formateurRepository.findById(id).map(formateurMapper::toDto);
    }

    /**
     * Delete the formateur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Formateur : {}", id);
        formateurRepository.deleteById(id);
    }
}
