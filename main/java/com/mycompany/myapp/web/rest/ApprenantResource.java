package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ApprenantRepository;
import com.mycompany.myapp.service.ApprenantService;
import com.mycompany.myapp.service.dto.ApprenantDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Apprenant}.
 */
@RestController
@RequestMapping("/api")
public class ApprenantResource {

    private final Logger log = LoggerFactory.getLogger(ApprenantResource.class);

    private static final String ENTITY_NAME = "apprenant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApprenantService apprenantService;

    private final ApprenantRepository apprenantRepository;

    public ApprenantResource(ApprenantService apprenantService, ApprenantRepository apprenantRepository) {
        this.apprenantService = apprenantService;
        this.apprenantRepository = apprenantRepository;
    }

    /**
     * {@code POST  /apprenants} : Create a new apprenant.
     *
     * @param apprenantDTO the apprenantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apprenantDTO, or with status {@code 400 (Bad Request)} if the apprenant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/apprenants")
    public ResponseEntity<ApprenantDTO> createApprenant(@Valid @RequestBody ApprenantDTO apprenantDTO) throws URISyntaxException {
        log.debug("REST request to save Apprenant : {}", apprenantDTO);
        if (apprenantDTO.getId() != null) {
            throw new BadRequestAlertException("A new apprenant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApprenantDTO result = apprenantService.save(apprenantDTO);
        return ResponseEntity
            .created(new URI("/api/apprenants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /apprenants/:id} : Updates an existing apprenant.
     *
     * @param id the id of the apprenantDTO to save.
     * @param apprenantDTO the apprenantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apprenantDTO,
     * or with status {@code 400 (Bad Request)} if the apprenantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apprenantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/apprenants/{id}")
    public ResponseEntity<ApprenantDTO> updateApprenant(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApprenantDTO apprenantDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Apprenant : {}, {}", id, apprenantDTO);
        if (apprenantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apprenantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apprenantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApprenantDTO result = apprenantService.save(apprenantDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apprenantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /apprenants/:id} : Partial updates given fields of an existing apprenant, field will ignore if it is null
     *
     * @param id the id of the apprenantDTO to save.
     * @param apprenantDTO the apprenantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apprenantDTO,
     * or with status {@code 400 (Bad Request)} if the apprenantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the apprenantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the apprenantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/apprenants/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ApprenantDTO> partialUpdateApprenant(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApprenantDTO apprenantDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Apprenant partially : {}, {}", id, apprenantDTO);
        if (apprenantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apprenantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apprenantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApprenantDTO> result = apprenantService.partialUpdate(apprenantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apprenantDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /apprenants} : get all the apprenants.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apprenants in body.
     */
    @GetMapping("/apprenants")
    public ResponseEntity<List<ApprenantDTO>> getAllApprenants(Pageable pageable) {
        log.debug("REST request to get a page of Apprenants");
        Page<ApprenantDTO> page = apprenantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /apprenants/:id} : get the "id" apprenant.
     *
     * @param id the id of the apprenantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apprenantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/apprenants/{id}")
    public ResponseEntity<ApprenantDTO> getApprenant(@PathVariable Long id) {
        log.debug("REST request to get Apprenant : {}", id);
        Optional<ApprenantDTO> apprenantDTO = apprenantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apprenantDTO);
    }

    /**
     * {@code DELETE  /apprenants/:id} : delete the "id" apprenant.
     *
     * @param id the id of the apprenantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/apprenants/{id}")
    public ResponseEntity<Void> deleteApprenant(@PathVariable Long id) {
        log.debug("REST request to delete Apprenant : {}", id);
        apprenantService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
