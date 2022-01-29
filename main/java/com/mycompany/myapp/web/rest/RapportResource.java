package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.RapportRepository;
import com.mycompany.myapp.service.RapportService;
import com.mycompany.myapp.service.dto.RapportDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Rapport}.
 */
@RestController
@RequestMapping("/api")
public class RapportResource {

    private final Logger log = LoggerFactory.getLogger(RapportResource.class);

    private static final String ENTITY_NAME = "rapport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RapportService rapportService;

    private final RapportRepository rapportRepository;

    public RapportResource(RapportService rapportService, RapportRepository rapportRepository) {
        this.rapportService = rapportService;
        this.rapportRepository = rapportRepository;
    }

    /**
     * {@code POST  /rapports} : Create a new rapport.
     *
     * @param rapportDTO the rapportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rapportDTO, or with status {@code 400 (Bad Request)} if the rapport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rapports")
    public ResponseEntity<RapportDTO> createRapport(@RequestBody RapportDTO rapportDTO) throws URISyntaxException {
        log.debug("REST request to save Rapport : {}", rapportDTO);
        if (rapportDTO.getId() != null) {
            throw new BadRequestAlertException("A new rapport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RapportDTO result = rapportService.save(rapportDTO);
        return ResponseEntity
            .created(new URI("/api/rapports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rapports/:id} : Updates an existing rapport.
     *
     * @param id the id of the rapportDTO to save.
     * @param rapportDTO the rapportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rapportDTO,
     * or with status {@code 400 (Bad Request)} if the rapportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rapportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rapports/{id}")
    public ResponseEntity<RapportDTO> updateRapport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RapportDTO rapportDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Rapport : {}, {}", id, rapportDTO);
        if (rapportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rapportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rapportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RapportDTO result = rapportService.save(rapportDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rapportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rapports/:id} : Partial updates given fields of an existing rapport, field will ignore if it is null
     *
     * @param id the id of the rapportDTO to save.
     * @param rapportDTO the rapportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rapportDTO,
     * or with status {@code 400 (Bad Request)} if the rapportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rapportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rapportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rapports/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RapportDTO> partialUpdateRapport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RapportDTO rapportDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rapport partially : {}, {}", id, rapportDTO);
        if (rapportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rapportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rapportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RapportDTO> result = rapportService.partialUpdate(rapportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rapportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rapports} : get all the rapports.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rapports in body.
     */
    @GetMapping("/rapports")
    public ResponseEntity<List<RapportDTO>> getAllRapports(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("formation-is-null".equals(filter)) {
            log.debug("REST request to get all Rapports where formation is null");
            return new ResponseEntity<>(rapportService.findAllWhereFormationIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Rapports");
        Page<RapportDTO> page = rapportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rapports/:id} : get the "id" rapport.
     *
     * @param id the id of the rapportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rapportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rapports/{id}")
    public ResponseEntity<RapportDTO> getRapport(@PathVariable Long id) {
        log.debug("REST request to get Rapport : {}", id);
        Optional<RapportDTO> rapportDTO = rapportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rapportDTO);
    }

    /**
     * {@code DELETE  /rapports/:id} : delete the "id" rapport.
     *
     * @param id the id of the rapportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rapports/{id}")
    public ResponseEntity<Void> deleteRapport(@PathVariable Long id) {
        log.debug("REST request to delete Rapport : {}", id);
        rapportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
