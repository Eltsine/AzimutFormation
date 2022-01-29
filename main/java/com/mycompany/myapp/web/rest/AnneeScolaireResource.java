package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.AnneeScolaireRepository;
import com.mycompany.myapp.service.AnneeScolaireService;
import com.mycompany.myapp.service.dto.AnneeScolaireDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.AnneeScolaire}.
 */
@RestController
@RequestMapping("/api")
public class AnneeScolaireResource {

    private final Logger log = LoggerFactory.getLogger(AnneeScolaireResource.class);

    private static final String ENTITY_NAME = "anneeScolaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnneeScolaireService anneeScolaireService;

    private final AnneeScolaireRepository anneeScolaireRepository;

    public AnneeScolaireResource(AnneeScolaireService anneeScolaireService, AnneeScolaireRepository anneeScolaireRepository) {
        this.anneeScolaireService = anneeScolaireService;
        this.anneeScolaireRepository = anneeScolaireRepository;
    }

    /**
     * {@code POST  /annee-scolaires} : Create a new anneeScolaire.
     *
     * @param anneeScolaireDTO the anneeScolaireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anneeScolaireDTO, or with status {@code 400 (Bad Request)} if the anneeScolaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/annee-scolaires")
    public ResponseEntity<AnneeScolaireDTO> createAnneeScolaire(@RequestBody AnneeScolaireDTO anneeScolaireDTO) throws URISyntaxException {
        log.debug("REST request to save AnneeScolaire : {}", anneeScolaireDTO);
        if (anneeScolaireDTO.getId() != null) {
            throw new BadRequestAlertException("A new anneeScolaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnneeScolaireDTO result = anneeScolaireService.save(anneeScolaireDTO);
        return ResponseEntity
            .created(new URI("/api/annee-scolaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /annee-scolaires/:id} : Updates an existing anneeScolaire.
     *
     * @param id the id of the anneeScolaireDTO to save.
     * @param anneeScolaireDTO the anneeScolaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anneeScolaireDTO,
     * or with status {@code 400 (Bad Request)} if the anneeScolaireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anneeScolaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/annee-scolaires/{id}")
    public ResponseEntity<AnneeScolaireDTO> updateAnneeScolaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnneeScolaireDTO anneeScolaireDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AnneeScolaire : {}, {}", id, anneeScolaireDTO);
        if (anneeScolaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anneeScolaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anneeScolaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnneeScolaireDTO result = anneeScolaireService.save(anneeScolaireDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anneeScolaireDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /annee-scolaires/:id} : Partial updates given fields of an existing anneeScolaire, field will ignore if it is null
     *
     * @param id the id of the anneeScolaireDTO to save.
     * @param anneeScolaireDTO the anneeScolaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anneeScolaireDTO,
     * or with status {@code 400 (Bad Request)} if the anneeScolaireDTO is not valid,
     * or with status {@code 404 (Not Found)} if the anneeScolaireDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the anneeScolaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/annee-scolaires/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AnneeScolaireDTO> partialUpdateAnneeScolaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnneeScolaireDTO anneeScolaireDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnneeScolaire partially : {}, {}", id, anneeScolaireDTO);
        if (anneeScolaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anneeScolaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anneeScolaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnneeScolaireDTO> result = anneeScolaireService.partialUpdate(anneeScolaireDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anneeScolaireDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /annee-scolaires} : get all the anneeScolaires.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anneeScolaires in body.
     */
    @GetMapping("/annee-scolaires")
    public ResponseEntity<List<AnneeScolaireDTO>> getAllAnneeScolaires(Pageable pageable) {
        log.debug("REST request to get a page of AnneeScolaires");
        Page<AnneeScolaireDTO> page = anneeScolaireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /annee-scolaires/:id} : get the "id" anneeScolaire.
     *
     * @param id the id of the anneeScolaireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anneeScolaireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/annee-scolaires/{id}")
    public ResponseEntity<AnneeScolaireDTO> getAnneeScolaire(@PathVariable Long id) {
        log.debug("REST request to get AnneeScolaire : {}", id);
        Optional<AnneeScolaireDTO> anneeScolaireDTO = anneeScolaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anneeScolaireDTO);
    }

    /**
     * {@code DELETE  /annee-scolaires/:id} : delete the "id" anneeScolaire.
     *
     * @param id the id of the anneeScolaireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/annee-scolaires/{id}")
    public ResponseEntity<Void> deleteAnneeScolaire(@PathVariable Long id) {
        log.debug("REST request to delete AnneeScolaire : {}", id);
        anneeScolaireService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
