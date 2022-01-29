package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Modules;
import com.mycompany.myapp.domain.enumeration.NomModules;
import com.mycompany.myapp.repository.ModulesRepository;
import com.mycompany.myapp.service.dto.ModulesDTO;
import com.mycompany.myapp.service.mapper.ModulesMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ModulesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ModulesResourceIT {

    private static final NomModules DEFAULT_NOM_MODULE = NomModules.Math_PC;
    private static final NomModules UPDATED_NOM_MODULE = NomModules.Math_SVT;

    private static final BigDecimal DEFAULT_PRIX = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIX = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/modules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ModulesRepository modulesRepository;

    @Autowired
    private ModulesMapper modulesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModulesMockMvc;

    private Modules modules;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Modules createEntity(EntityManager em) {
        Modules modules = new Modules().nomModule(DEFAULT_NOM_MODULE).prix(DEFAULT_PRIX);
        return modules;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Modules createUpdatedEntity(EntityManager em) {
        Modules modules = new Modules().nomModule(UPDATED_NOM_MODULE).prix(UPDATED_PRIX);
        return modules;
    }

    @BeforeEach
    public void initTest() {
        modules = createEntity(em);
    }

    @Test
    @Transactional
    void createModules() throws Exception {
        int databaseSizeBeforeCreate = modulesRepository.findAll().size();
        // Create the Modules
        ModulesDTO modulesDTO = modulesMapper.toDto(modules);
        restModulesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modulesDTO)))
            .andExpect(status().isCreated());

        // Validate the Modules in the database
        List<Modules> modulesList = modulesRepository.findAll();
        assertThat(modulesList).hasSize(databaseSizeBeforeCreate + 1);
        Modules testModules = modulesList.get(modulesList.size() - 1);
        assertThat(testModules.getNomModule()).isEqualTo(DEFAULT_NOM_MODULE);
        assertThat(testModules.getPrix()).isEqualByComparingTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    void createModulesWithExistingId() throws Exception {
        // Create the Modules with an existing ID
        modules.setId(1L);
        ModulesDTO modulesDTO = modulesMapper.toDto(modules);

        int databaseSizeBeforeCreate = modulesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModulesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modulesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Modules in the database
        List<Modules> modulesList = modulesRepository.findAll();
        assertThat(modulesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomModuleIsRequired() throws Exception {
        int databaseSizeBeforeTest = modulesRepository.findAll().size();
        // set the field null
        modules.setNomModule(null);

        // Create the Modules, which fails.
        ModulesDTO modulesDTO = modulesMapper.toDto(modules);

        restModulesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modulesDTO)))
            .andExpect(status().isBadRequest());

        List<Modules> modulesList = modulesRepository.findAll();
        assertThat(modulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllModules() throws Exception {
        // Initialize the database
        modulesRepository.saveAndFlush(modules);

        // Get all the modulesList
        restModulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modules.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomModule").value(hasItem(DEFAULT_NOM_MODULE.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(sameNumber(DEFAULT_PRIX))));
    }

    @Test
    @Transactional
    void getModules() throws Exception {
        // Initialize the database
        modulesRepository.saveAndFlush(modules);

        // Get the modules
        restModulesMockMvc
            .perform(get(ENTITY_API_URL_ID, modules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modules.getId().intValue()))
            .andExpect(jsonPath("$.nomModule").value(DEFAULT_NOM_MODULE.toString()))
            .andExpect(jsonPath("$.prix").value(sameNumber(DEFAULT_PRIX)));
    }

    @Test
    @Transactional
    void getNonExistingModules() throws Exception {
        // Get the modules
        restModulesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewModules() throws Exception {
        // Initialize the database
        modulesRepository.saveAndFlush(modules);

        int databaseSizeBeforeUpdate = modulesRepository.findAll().size();

        // Update the modules
        Modules updatedModules = modulesRepository.findById(modules.getId()).get();
        // Disconnect from session so that the updates on updatedModules are not directly saved in db
        em.detach(updatedModules);
        updatedModules.nomModule(UPDATED_NOM_MODULE).prix(UPDATED_PRIX);
        ModulesDTO modulesDTO = modulesMapper.toDto(updatedModules);

        restModulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modulesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modulesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Modules in the database
        List<Modules> modulesList = modulesRepository.findAll();
        assertThat(modulesList).hasSize(databaseSizeBeforeUpdate);
        Modules testModules = modulesList.get(modulesList.size() - 1);
        assertThat(testModules.getNomModule()).isEqualTo(UPDATED_NOM_MODULE);
        assertThat(testModules.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    void putNonExistingModules() throws Exception {
        int databaseSizeBeforeUpdate = modulesRepository.findAll().size();
        modules.setId(count.incrementAndGet());

        // Create the Modules
        ModulesDTO modulesDTO = modulesMapper.toDto(modules);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modulesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Modules in the database
        List<Modules> modulesList = modulesRepository.findAll();
        assertThat(modulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchModules() throws Exception {
        int databaseSizeBeforeUpdate = modulesRepository.findAll().size();
        modules.setId(count.incrementAndGet());

        // Create the Modules
        ModulesDTO modulesDTO = modulesMapper.toDto(modules);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Modules in the database
        List<Modules> modulesList = modulesRepository.findAll();
        assertThat(modulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamModules() throws Exception {
        int databaseSizeBeforeUpdate = modulesRepository.findAll().size();
        modules.setId(count.incrementAndGet());

        // Create the Modules
        ModulesDTO modulesDTO = modulesMapper.toDto(modules);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModulesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modulesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Modules in the database
        List<Modules> modulesList = modulesRepository.findAll();
        assertThat(modulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateModulesWithPatch() throws Exception {
        // Initialize the database
        modulesRepository.saveAndFlush(modules);

        int databaseSizeBeforeUpdate = modulesRepository.findAll().size();

        // Update the modules using partial update
        Modules partialUpdatedModules = new Modules();
        partialUpdatedModules.setId(modules.getId());

        partialUpdatedModules.nomModule(UPDATED_NOM_MODULE);

        restModulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModules.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModules))
            )
            .andExpect(status().isOk());

        // Validate the Modules in the database
        List<Modules> modulesList = modulesRepository.findAll();
        assertThat(modulesList).hasSize(databaseSizeBeforeUpdate);
        Modules testModules = modulesList.get(modulesList.size() - 1);
        assertThat(testModules.getNomModule()).isEqualTo(UPDATED_NOM_MODULE);
        assertThat(testModules.getPrix()).isEqualByComparingTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    void fullUpdateModulesWithPatch() throws Exception {
        // Initialize the database
        modulesRepository.saveAndFlush(modules);

        int databaseSizeBeforeUpdate = modulesRepository.findAll().size();

        // Update the modules using partial update
        Modules partialUpdatedModules = new Modules();
        partialUpdatedModules.setId(modules.getId());

        partialUpdatedModules.nomModule(UPDATED_NOM_MODULE).prix(UPDATED_PRIX);

        restModulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModules.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModules))
            )
            .andExpect(status().isOk());

        // Validate the Modules in the database
        List<Modules> modulesList = modulesRepository.findAll();
        assertThat(modulesList).hasSize(databaseSizeBeforeUpdate);
        Modules testModules = modulesList.get(modulesList.size() - 1);
        assertThat(testModules.getNomModule()).isEqualTo(UPDATED_NOM_MODULE);
        assertThat(testModules.getPrix()).isEqualByComparingTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    void patchNonExistingModules() throws Exception {
        int databaseSizeBeforeUpdate = modulesRepository.findAll().size();
        modules.setId(count.incrementAndGet());

        // Create the Modules
        ModulesDTO modulesDTO = modulesMapper.toDto(modules);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, modulesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Modules in the database
        List<Modules> modulesList = modulesRepository.findAll();
        assertThat(modulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchModules() throws Exception {
        int databaseSizeBeforeUpdate = modulesRepository.findAll().size();
        modules.setId(count.incrementAndGet());

        // Create the Modules
        ModulesDTO modulesDTO = modulesMapper.toDto(modules);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Modules in the database
        List<Modules> modulesList = modulesRepository.findAll();
        assertThat(modulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamModules() throws Exception {
        int databaseSizeBeforeUpdate = modulesRepository.findAll().size();
        modules.setId(count.incrementAndGet());

        // Create the Modules
        ModulesDTO modulesDTO = modulesMapper.toDto(modules);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModulesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(modulesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Modules in the database
        List<Modules> modulesList = modulesRepository.findAll();
        assertThat(modulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteModules() throws Exception {
        // Initialize the database
        modulesRepository.saveAndFlush(modules);

        int databaseSizeBeforeDelete = modulesRepository.findAll().size();

        // Delete the modules
        restModulesMockMvc
            .perform(delete(ENTITY_API_URL_ID, modules.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Modules> modulesList = modulesRepository.findAll();
        assertThat(modulesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
