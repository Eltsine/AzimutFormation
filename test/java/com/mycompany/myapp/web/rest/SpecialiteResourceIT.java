package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Specialite;
import com.mycompany.myapp.repository.SpecialiteRepository;
import com.mycompany.myapp.service.dto.SpecialiteDTO;
import com.mycompany.myapp.service.mapper.SpecialiteMapper;
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
 * Integration tests for the {@link SpecialiteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpecialiteResourceIT {

    private static final String DEFAULT_LIB_SPECIALITE = "AAAAAAAAAA";
    private static final String UPDATED_LIB_SPECIALITE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/specialites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SpecialiteRepository specialiteRepository;

    @Autowired
    private SpecialiteMapper specialiteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpecialiteMockMvc;

    private Specialite specialite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specialite createEntity(EntityManager em) {
        Specialite specialite = new Specialite().libSpecialite(DEFAULT_LIB_SPECIALITE);
        return specialite;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specialite createUpdatedEntity(EntityManager em) {
        Specialite specialite = new Specialite().libSpecialite(UPDATED_LIB_SPECIALITE);
        return specialite;
    }

    @BeforeEach
    public void initTest() {
        specialite = createEntity(em);
    }

    @Test
    @Transactional
    void createSpecialite() throws Exception {
        int databaseSizeBeforeCreate = specialiteRepository.findAll().size();
        // Create the Specialite
        SpecialiteDTO specialiteDTO = specialiteMapper.toDto(specialite);
        restSpecialiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialiteDTO)))
            .andExpect(status().isCreated());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeCreate + 1);
        Specialite testSpecialite = specialiteList.get(specialiteList.size() - 1);
        assertThat(testSpecialite.getLibSpecialite()).isEqualTo(DEFAULT_LIB_SPECIALITE);
    }

    @Test
    @Transactional
    void createSpecialiteWithExistingId() throws Exception {
        // Create the Specialite with an existing ID
        specialite.setId(1L);
        SpecialiteDTO specialiteDTO = specialiteMapper.toDto(specialite);

        int databaseSizeBeforeCreate = specialiteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialiteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLibSpecialiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialiteRepository.findAll().size();
        // set the field null
        specialite.setLibSpecialite(null);

        // Create the Specialite, which fails.
        SpecialiteDTO specialiteDTO = specialiteMapper.toDto(specialite);

        restSpecialiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialiteDTO)))
            .andExpect(status().isBadRequest());

        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSpecialites() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList
        restSpecialiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialite.getId().intValue())))
            .andExpect(jsonPath("$.[*].libSpecialite").value(hasItem(DEFAULT_LIB_SPECIALITE)));
    }

    @Test
    @Transactional
    void getSpecialite() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get the specialite
        restSpecialiteMockMvc
            .perform(get(ENTITY_API_URL_ID, specialite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(specialite.getId().intValue()))
            .andExpect(jsonPath("$.libSpecialite").value(DEFAULT_LIB_SPECIALITE));
    }

    @Test
    @Transactional
    void getNonExistingSpecialite() throws Exception {
        // Get the specialite
        restSpecialiteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSpecialite() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();

        // Update the specialite
        Specialite updatedSpecialite = specialiteRepository.findById(specialite.getId()).get();
        // Disconnect from session so that the updates on updatedSpecialite are not directly saved in db
        em.detach(updatedSpecialite);
        updatedSpecialite.libSpecialite(UPDATED_LIB_SPECIALITE);
        SpecialiteDTO specialiteDTO = specialiteMapper.toDto(updatedSpecialite);

        restSpecialiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, specialiteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specialiteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
        Specialite testSpecialite = specialiteList.get(specialiteList.size() - 1);
        assertThat(testSpecialite.getLibSpecialite()).isEqualTo(UPDATED_LIB_SPECIALITE);
    }

    @Test
    @Transactional
    void putNonExistingSpecialite() throws Exception {
        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();
        specialite.setId(count.incrementAndGet());

        // Create the Specialite
        SpecialiteDTO specialiteDTO = specialiteMapper.toDto(specialite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, specialiteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specialiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpecialite() throws Exception {
        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();
        specialite.setId(count.incrementAndGet());

        // Create the Specialite
        SpecialiteDTO specialiteDTO = specialiteMapper.toDto(specialite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specialiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpecialite() throws Exception {
        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();
        specialite.setId(count.incrementAndGet());

        // Create the Specialite
        SpecialiteDTO specialiteDTO = specialiteMapper.toDto(specialite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialiteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialiteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpecialiteWithPatch() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();

        // Update the specialite using partial update
        Specialite partialUpdatedSpecialite = new Specialite();
        partialUpdatedSpecialite.setId(specialite.getId());

        partialUpdatedSpecialite.libSpecialite(UPDATED_LIB_SPECIALITE);

        restSpecialiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecialite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpecialite))
            )
            .andExpect(status().isOk());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
        Specialite testSpecialite = specialiteList.get(specialiteList.size() - 1);
        assertThat(testSpecialite.getLibSpecialite()).isEqualTo(UPDATED_LIB_SPECIALITE);
    }

    @Test
    @Transactional
    void fullUpdateSpecialiteWithPatch() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();

        // Update the specialite using partial update
        Specialite partialUpdatedSpecialite = new Specialite();
        partialUpdatedSpecialite.setId(specialite.getId());

        partialUpdatedSpecialite.libSpecialite(UPDATED_LIB_SPECIALITE);

        restSpecialiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecialite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpecialite))
            )
            .andExpect(status().isOk());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
        Specialite testSpecialite = specialiteList.get(specialiteList.size() - 1);
        assertThat(testSpecialite.getLibSpecialite()).isEqualTo(UPDATED_LIB_SPECIALITE);
    }

    @Test
    @Transactional
    void patchNonExistingSpecialite() throws Exception {
        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();
        specialite.setId(count.incrementAndGet());

        // Create the Specialite
        SpecialiteDTO specialiteDTO = specialiteMapper.toDto(specialite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, specialiteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specialiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpecialite() throws Exception {
        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();
        specialite.setId(count.incrementAndGet());

        // Create the Specialite
        SpecialiteDTO specialiteDTO = specialiteMapper.toDto(specialite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specialiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpecialite() throws Exception {
        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();
        specialite.setId(count.incrementAndGet());

        // Create the Specialite
        SpecialiteDTO specialiteDTO = specialiteMapper.toDto(specialite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialiteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(specialiteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpecialite() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        int databaseSizeBeforeDelete = specialiteRepository.findAll().size();

        // Delete the specialite
        restSpecialiteMockMvc
            .perform(delete(ENTITY_API_URL_ID, specialite.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
