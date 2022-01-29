package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.SalleRepository;
import com.mycompany.myapp.service.SalleService;
import com.mycompany.myapp.service.dto.SalleDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Salle}.
 */
@RestController
@RequestMapping("/api")
public class SalleResource {

    private final Logger log = LoggerFactory.getLogger(SalleResource.class);

    private static final String ENTITY_NAME = "salle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalleService salleService;

    private final SalleRepository salleRepository;

    public SalleResource(SalleService salleService, SalleRepository salleRepository) {
        this.salleService = salleService;
        this.salleRepository = salleRepository;
    }

    /**
     * {@code POST  /salles} : Create a new salle.
     *
     * @param salleDTO the salleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salleDTO, or with status {@code 400 (Bad Request)} if the salle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/salles")
    public ResponseEntity<SalleDTO> createSalle(@Valid @RequestBody SalleDTO salleDTO) throws URISyntaxException {
        log.debug("REST request to save Salle : {}", salleDTO);
        if (salleDTO.getId() != null) {
            throw new BadRequestAlertException("A new salle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalleDTO result = salleService.save(salleDTO);
        return ResponseEntity
            .created(new URI("/api/salles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /salles/:id} : Updates an existing salle.
     *
     * @param id the id of the salleDTO to save.
     * @param salleDTO the salleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salleDTO,
     * or with status {@code 400 (Bad Request)} if the salleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/salles/{id}")
    public ResponseEntity<SalleDTO> updateSalle(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SalleDTO salleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Salle : {}, {}", id, salleDTO);
        if (salleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SalleDTO result = salleService.save(salleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /salles/:id} : Partial updates given fields of an existing salle, field will ignore if it is null
     *
     * @param id the id of the salleDTO to save.
     * @param salleDTO the salleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salleDTO,
     * or with status {@code 400 (Bad Request)} if the salleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the salleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the salleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/salles/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SalleDTO> partialUpdateSalle(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SalleDTO salleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Salle partially : {}, {}", id, salleDTO);
        if (salleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalleDTO> result = salleService.partialUpdate(salleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /salles} : get all the salles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salles in body.
     */
    @GetMapping("/salles")
    public ResponseEntity<List<SalleDTO>> getAllSalles(Pageable pageable) {
        log.debug("REST request to get a page of Salles");
        Page<SalleDTO> page = salleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /salles/:id} : get the "id" salle.
     *
     * @param id the id of the salleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/salles/{id}")
    public ResponseEntity<SalleDTO> getSalle(@PathVariable Long id) {
        log.debug("REST request to get Salle : {}", id);
        Optional<SalleDTO> salleDTO = salleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salleDTO);
    }

    /**
     * {@code DELETE  /salles/:id} : delete the "id" salle.
     *
     * @param id the id of the salleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/salles/{id}")
    public ResponseEntity<Void> deleteSalle(@PathVariable Long id) {
        log.debug("REST request to delete Salle : {}", id);
        salleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
