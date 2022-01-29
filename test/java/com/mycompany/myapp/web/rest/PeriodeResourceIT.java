package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Periode;
import com.mycompany.myapp.repository.PeriodeRepository;
import com.mycompany.myapp.service.dto.PeriodeDTO;
import com.mycompany.myapp.service.mapper.PeriodeMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link PeriodeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PeriodeResourceIT {

    private static final String DEFAULT_DUREE = "AAAAAAAAAA";
    private static final String UPDATED_DUREE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_DEBUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DEBUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_FIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_FIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/periodes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PeriodeRepository periodeRepository;

    @Autowired
    private PeriodeMapper periodeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeriodeMockMvc;

    private Periode periode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Periode createEntity(EntityManager em) {
        Periode periode = new Periode().duree(DEFAULT_DUREE).dateDebut(DEFAULT_DATE_DEBUT).dateFin(DEFAULT_DATE_FIN);
        return periode;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Periode createUpdatedEntity(EntityManager em) {
        Periode periode = new Periode().duree(UPDATED_DUREE).dateDebut(UPDATED_DATE_DEBUT).dateFin(UPDATED_DATE_FIN);
        return periode;
    }

    @BeforeEach
    public void initTest() {
        periode = createEntity(em);
    }

    @Test
    @Transactional
    void createPeriode() throws Exception {
        int databaseSizeBeforeCreate = periodeRepository.findAll().size();
        // Create the Periode
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);
        restPeriodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isCreated());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeCreate + 1);
        Periode testPeriode = periodeList.get(periodeList.size() - 1);
        assertThat(testPeriode.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testPeriode.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testPeriode.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    void createPeriodeWithExistingId() throws Exception {
        // Create the Periode with an existing ID
        periode.setId(1L);
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        int databaseSizeBeforeCreate = periodeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodeRepository.findAll().size();
        // set the field null
        periode.setDateDebut(null);

        // Create the Periode, which fails.
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        restPeriodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isBadRequest());

        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodeRepository.findAll().size();
        // set the field null
        periode.setDateFin(null);

        // Create the Periode, which fails.
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        restPeriodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isBadRequest());

        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPeriodes() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        // Get all the periodeList
        restPeriodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periode.getId().intValue())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(sameInstant(DEFAULT_DATE_DEBUT))))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(sameInstant(DEFAULT_DATE_FIN))));
    }

    @Test
    @Transactional
    void getPeriode() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        // Get the periode
        restPeriodeMockMvc
            .perform(get(ENTITY_API_URL_ID, periode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(periode.getId().intValue()))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE))
            .andExpect(jsonPath("$.dateDebut").value(sameInstant(DEFAULT_DATE_DEBUT)))
            .andExpect(jsonPath("$.dateFin").value(sameInstant(DEFAULT_DATE_FIN)));
    }

    @Test
    @Transactional
    void getNonExistingPeriode() throws Exception {
        // Get the periode
        restPeriodeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPeriode() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        int databaseSizeBeforeUpdate = periodeRepository.findAll().size();

        // Update the periode
        Periode updatedPeriode = periodeRepository.findById(periode.getId()).get();
        // Disconnect from session so that the updates on updatedPeriode are not directly saved in db
        em.detach(updatedPeriode);
        updatedPeriode.duree(UPDATED_DUREE).dateDebut(UPDATED_DATE_DEBUT).dateFin(UPDATED_DATE_FIN);
        PeriodeDTO periodeDTO = periodeMapper.toDto(updatedPeriode);

        restPeriodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, periodeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeUpdate);
        Periode testPeriode = periodeList.get(periodeList.size() - 1);
        assertThat(testPeriode.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testPeriode.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testPeriode.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void putNonExistingPeriode() throws Exception {
        int databaseSizeBeforeUpdate = periodeRepository.findAll().size();
        periode.setId(count.incrementAndGet());

        // Create the Periode
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, periodeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPeriode() throws Exception {
        int databaseSizeBeforeUpdate = periodeRepository.findAll().size();
        periode.setId(count.incrementAndGet());

        // Create the Periode
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPeriode() throws Exception {
        int databaseSizeBeforeUpdate = periodeRepository.findAll().size();
        periode.setId(count.incrementAndGet());

        // Create the Periode
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePeriodeWithPatch() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        int databaseSizeBeforeUpdate = periodeRepository.findAll().size();

        // Update the periode using partial update
        Periode partialUpdatedPeriode = new Periode();
        partialUpdatedPeriode.setId(periode.getId());

        partialUpdatedPeriode.duree(UPDATED_DUREE);

        restPeriodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriode.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPeriode))
            )
            .andExpect(status().isOk());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeUpdate);
        Periode testPeriode = periodeList.get(periodeList.size() - 1);
        assertThat(testPeriode.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testPeriode.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testPeriode.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    void fullUpdatePeriodeWithPatch() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        int databaseSizeBeforeUpdate = periodeRepository.findAll().size();

        // Update the periode using partial update
        Periode partialUpdatedPeriode = new Periode();
        partialUpdatedPeriode.setId(periode.getId());

        partialUpdatedPeriode.duree(UPDATED_DUREE).dateDebut(UPDATED_DATE_DEBUT).dateFin(UPDATED_DATE_FIN);

        restPeriodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriode.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPeriode))
            )
            .andExpect(status().isOk());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeUpdate);
        Periode testPeriode = periodeList.get(periodeList.size() - 1);
        assertThat(testPeriode.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testPeriode.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testPeriode.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void patchNonExistingPeriode() throws Exception {
        int databaseSizeBeforeUpdate = periodeRepository.findAll().size();
        periode.setId(count.incrementAndGet());

        // Create the Periode
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, periodeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(periodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPeriode() throws Exception {
        int databaseSizeBeforeUpdate = periodeRepository.findAll().size();
        periode.setId(count.incrementAndGet());

        // Create the Periode
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(periodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPeriode() throws Exception {
        int databaseSizeBeforeUpdate = periodeRepository.findAll().size();
        periode.setId(count.incrementAndGet());

        // Create the Periode
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(periodeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePeriode() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        int databaseSizeBeforeDelete = periodeRepository.findAll().size();

        // Delete the periode
        restPeriodeMockMvc
            .perform(delete(ENTITY_API_URL_ID, periode.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
