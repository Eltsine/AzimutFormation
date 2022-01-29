package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PeriodeRepository;
import com.mycompany.myapp.service.PeriodeService;
import com.mycompany.myapp.service.dto.PeriodeDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Periode}.
 */
@RestController
@RequestMapping("/api")
public class PeriodeResource {

    private final Logger log = LoggerFactory.getLogger(PeriodeResource.class);

    private static final String ENTITY_NAME = "periode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeriodeService periodeService;

    private final PeriodeRepository periodeRepository;

    public PeriodeResource(PeriodeService periodeService, PeriodeRepository periodeRepository) {
        this.periodeService = periodeService;
        this.periodeRepository = periodeRepository;
    }

    /**
     * {@code POST  /periodes} : Create a new periode.
     *
     * @param periodeDTO the periodeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new periodeDTO, or with status {@code 400 (Bad Request)} if the periode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/periodes")
    public ResponseEntity<PeriodeDTO> createPeriode(@Valid @RequestBody PeriodeDTO periodeDTO) throws URISyntaxException {
        log.debug("REST request to save Periode : {}", periodeDTO);
        if (periodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new periode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodeDTO result = periodeService.save(periodeDTO);
        return ResponseEntity
            .created(new URI("/api/periodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /periodes/:id} : Updates an existing periode.
     *
     * @param id the id of the periodeDTO to save.
     * @param periodeDTO the periodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodeDTO,
     * or with status {@code 400 (Bad Request)} if the periodeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the periodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/periodes/{id}")
    public ResponseEntity<PeriodeDTO> updatePeriode(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PeriodeDTO periodeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Periode : {}, {}", id, periodeDTO);
        if (periodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periodeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PeriodeDTO result = periodeService.save(periodeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /periodes/:id} : Partial updates given fields of an existing periode, field will ignore if it is null
     *
     * @param id the id of the periodeDTO to save.
     * @param periodeDTO the periodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodeDTO,
     * or with status {@code 400 (Bad Request)} if the periodeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the periodeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the periodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/periodes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PeriodeDTO> partialUpdatePeriode(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PeriodeDTO periodeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Periode partially : {}, {}", id, periodeDTO);
        if (periodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periodeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PeriodeDTO> result = periodeService.partialUpdate(periodeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /periodes} : get all the periodes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of periodes in body.
     */
    @GetMapping("/periodes")
    public ResponseEntity<List<PeriodeDTO>> getAllPeriodes(Pageable pageable) {
        log.debug("REST request to get a page of Periodes");
        Page<PeriodeDTO> page = periodeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /periodes/:id} : get the "id" periode.
     *
     * @param id the id of the periodeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the periodeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/periodes/{id}")
    public ResponseEntity<PeriodeDTO> getPeriode(@PathVariable Long id) {
        log.debug("REST request to get Periode : {}", id);
        Optional<PeriodeDTO> periodeDTO = periodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(periodeDTO);
    }

    /**
     * {@code DELETE  /periodes/:id} : delete the "id" periode.
     *
     * @param id the id of the periodeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/periodes/{id}")
    public ResponseEntity<Void> deletePeriode(@PathVariable Long id) {
        log.debug("REST request to delete Periode : {}", id);
        periodeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
