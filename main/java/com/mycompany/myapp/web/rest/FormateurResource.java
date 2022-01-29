package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.FormateurRepository;
import com.mycompany.myapp.service.FormateurService;
import com.mycompany.myapp.service.dto.FormateurDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Formateur}.
 */
@RestController
@RequestMapping("/api")
public class FormateurResource {

    private final Logger log = LoggerFactory.getLogger(FormateurResource.class);

    private static final String ENTITY_NAME = "formateur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormateurService formateurService;

    private final FormateurRepository formateurRepository;

    public FormateurResource(FormateurService formateurService, FormateurRepository formateurRepository) {
        this.formateurService = formateurService;
        this.formateurRepository = formateurRepository;
    }

    /**
     * {@code POST  /formateurs} : Create a new formateur.
     *
     * @param formateurDTO the formateurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formateurDTO, or with status {@code 400 (Bad Request)} if the formateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/formateurs")
    public ResponseEntity<FormateurDTO> createFormateur(@Valid @RequestBody FormateurDTO formateurDTO) throws URISyntaxException {
        log.debug("REST request to save Formateur : {}", formateurDTO);
        if (formateurDTO.getId() != null) {
            throw new BadRequestAlertException("A new formateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormateurDTO result = formateurService.save(formateurDTO);
        return ResponseEntity
            .created(new URI("/api/formateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /formateurs/:id} : Updates an existing formateur.
     *
     * @param id the id of the formateurDTO to save.
     * @param formateurDTO the formateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formateurDTO,
     * or with status {@code 400 (Bad Request)} if the formateurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/formateurs/{id}")
    public ResponseEntity<FormateurDTO> updateFormateur(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FormateurDTO formateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Formateur : {}, {}", id, formateurDTO);
        if (formateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FormateurDTO result = formateurService.save(formateurDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formateurDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /formateurs/:id} : Partial updates given fields of an existing formateur, field will ignore if it is null
     *
     * @param id the id of the formateurDTO to save.
     * @param formateurDTO the formateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formateurDTO,
     * or with status {@code 400 (Bad Request)} if the formateurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the formateurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the formateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/formateurs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FormateurDTO> partialUpdateFormateur(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FormateurDTO formateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Formateur partially : {}, {}", id, formateurDTO);
        if (formateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FormateurDTO> result = formateurService.partialUpdate(formateurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formateurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /formateurs} : get all the formateurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formateurs in body.
     */
    @GetMapping("/formateurs")
    public ResponseEntity<List<FormateurDTO>> getAllFormateurs(Pageable pageable) {
        log.debug("REST request to get a page of Formateurs");
        Page<FormateurDTO> page = formateurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /formateurs/:id} : get the "id" formateur.
     *
     * @param id the id of the formateurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formateurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/formateurs/{id}")
    public ResponseEntity<FormateurDTO> getFormateur(@PathVariable Long id) {
        log.debug("REST request to get Formateur : {}", id);
        Optional<FormateurDTO> formateurDTO = formateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formateurDTO);
    }

    /**
     * {@code DELETE  /formateurs/:id} : delete the "id" formateur.
     *
     * @param id the id of the formateurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/formateurs/{id}")
    public ResponseEntity<Void> deleteFormateur(@PathVariable Long id) {
        log.debug("REST request to delete Formateur : {}", id);
        formateurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
