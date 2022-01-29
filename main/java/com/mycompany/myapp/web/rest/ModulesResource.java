package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ModulesRepository;
import com.mycompany.myapp.service.ModulesService;
import com.mycompany.myapp.service.dto.ModulesDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Modules}.
 */
@RestController
@RequestMapping("/api")
public class ModulesResource {

    private final Logger log = LoggerFactory.getLogger(ModulesResource.class);

    private static final String ENTITY_NAME = "modules";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModulesService modulesService;

    private final ModulesRepository modulesRepository;

    public ModulesResource(ModulesService modulesService, ModulesRepository modulesRepository) {
        this.modulesService = modulesService;
        this.modulesRepository = modulesRepository;
    }

    /**
     * {@code POST  /modules} : Create a new modules.
     *
     * @param modulesDTO the modulesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modulesDTO, or with status {@code 400 (Bad Request)} if the modules has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/modules")
    public ResponseEntity<ModulesDTO> createModules(@Valid @RequestBody ModulesDTO modulesDTO) throws URISyntaxException {
        log.debug("REST request to save Modules : {}", modulesDTO);
        if (modulesDTO.getId() != null) {
            throw new BadRequestAlertException("A new modules cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModulesDTO result = modulesService.save(modulesDTO);
        return ResponseEntity
            .created(new URI("/api/modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /modules/:id} : Updates an existing modules.
     *
     * @param id the id of the modulesDTO to save.
     * @param modulesDTO the modulesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modulesDTO,
     * or with status {@code 400 (Bad Request)} if the modulesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modulesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/modules/{id}")
    public ResponseEntity<ModulesDTO> updateModules(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ModulesDTO modulesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Modules : {}, {}", id, modulesDTO);
        if (modulesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modulesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ModulesDTO result = modulesService.save(modulesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modulesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /modules/:id} : Partial updates given fields of an existing modules, field will ignore if it is null
     *
     * @param id the id of the modulesDTO to save.
     * @param modulesDTO the modulesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modulesDTO,
     * or with status {@code 400 (Bad Request)} if the modulesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the modulesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the modulesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/modules/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ModulesDTO> partialUpdateModules(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ModulesDTO modulesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Modules partially : {}, {}", id, modulesDTO);
        if (modulesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modulesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ModulesDTO> result = modulesService.partialUpdate(modulesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modulesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /modules} : get all the modules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modules in body.
     */
    @GetMapping("/modules")
    public ResponseEntity<List<ModulesDTO>> getAllModules(Pageable pageable) {
        log.debug("REST request to get a page of Modules");
        Page<ModulesDTO> page = modulesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /modules/:id} : get the "id" modules.
     *
     * @param id the id of the modulesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modulesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/modules/{id}")
    public ResponseEntity<ModulesDTO> getModules(@PathVariable Long id) {
        log.debug("REST request to get Modules : {}", id);
        Optional<ModulesDTO> modulesDTO = modulesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modulesDTO);
    }

    /**
     * {@code DELETE  /modules/:id} : delete the "id" modules.
     *
     * @param id the id of the modulesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/modules/{id}")
    public ResponseEntity<Void> deleteModules(@PathVariable Long id) {
        log.debug("REST request to delete Modules : {}", id);
        modulesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
