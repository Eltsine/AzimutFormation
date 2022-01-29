package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.SpecialiteRepository;
import com.mycompany.myapp.service.SpecialiteService;
import com.mycompany.myapp.service.dto.SpecialiteDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Specialite}.
 */
@RestController
@RequestMapping("/api")
public class SpecialiteResource {

    private final Logger log = LoggerFactory.getLogger(SpecialiteResource.class);

    private static final String ENTITY_NAME = "specialite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecialiteService specialiteService;

    private final SpecialiteRepository specialiteRepository;

    public SpecialiteResource(SpecialiteService specialiteService, SpecialiteRepository specialiteRepository) {
        this.specialiteService = specialiteService;
        this.specialiteRepository = specialiteRepository;
    }

    /**
     * {@code POST  /specialites} : Create a new specialite.
     *
     * @param specialiteDTO the specialiteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specialiteDTO, or with status {@code 400 (Bad Request)} if the specialite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/specialites")
    public ResponseEntity<SpecialiteDTO> createSpecialite(@Valid @RequestBody SpecialiteDTO specialiteDTO) throws URISyntaxException {
        log.debug("REST request to save Specialite : {}", specialiteDTO);
        if (specialiteDTO.getId() != null) {
            throw new BadRequestAlertException("A new specialite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpecialiteDTO result = specialiteService.save(specialiteDTO);
        return ResponseEntity
            .created(new URI("/api/specialites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /specialites/:id} : Updates an existing specialite.
     *
     * @param id the id of the specialiteDTO to save.
     * @param specialiteDTO the specialiteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialiteDTO,
     * or with status {@code 400 (Bad Request)} if the specialiteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specialiteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/specialites/{id}")
    public ResponseEntity<SpecialiteDTO> updateSpecialite(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SpecialiteDTO specialiteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Specialite : {}, {}", id, specialiteDTO);
        if (specialiteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specialiteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specialiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SpecialiteDTO result = specialiteService.save(specialiteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specialiteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /specialites/:id} : Partial updates given fields of an existing specialite, field will ignore if it is null
     *
     * @param id the id of the specialiteDTO to save.
     * @param specialiteDTO the specialiteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialiteDTO,
     * or with status {@code 400 (Bad Request)} if the specialiteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the specialiteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the specialiteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/specialites/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SpecialiteDTO> partialUpdateSpecialite(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SpecialiteDTO specialiteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Specialite partially : {}, {}", id, specialiteDTO);
        if (specialiteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specialiteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specialiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SpecialiteDTO> result = specialiteService.partialUpdate(specialiteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specialiteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /specialites} : get all the specialites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specialites in body.
     */
    @GetMapping("/specialites")
    public ResponseEntity<List<SpecialiteDTO>> getAllSpecialites(Pageable pageable) {
        log.debug("REST request to get a page of Specialites");
        Page<SpecialiteDTO> page = specialiteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /specialites/:id} : get the "id" specialite.
     *
     * @param id the id of the specialiteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specialiteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/specialites/{id}")
    public ResponseEntity<SpecialiteDTO> getSpecialite(@PathVariable Long id) {
        log.debug("REST request to get Specialite : {}", id);
        Optional<SpecialiteDTO> specialiteDTO = specialiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specialiteDTO);
    }

    /**
     * {@code DELETE  /specialites/:id} : delete the "id" specialite.
     *
     * @param id the id of the specialiteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/specialites/{id}")
    public ResponseEntity<Void> deleteSpecialite(@PathVariable Long id) {
        log.debug("REST request to delete Specialite : {}", id);
        specialiteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
