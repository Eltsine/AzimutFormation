package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnneeScolaireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnneeScolaire.class);
        AnneeScolaire anneeScolaire1 = new AnneeScolaire();
        anneeScolaire1.setId(1L);
        AnneeScolaire anneeScolaire2 = new AnneeScolaire();
        anneeScolaire2.setId(anneeScolaire1.getId());
        assertThat(anneeScolaire1).isEqualTo(anneeScolaire2);
        anneeScolaire2.setId(2L);
        assertThat(anneeScolaire1).isNotEqualTo(anneeScolaire2);
        anneeScolaire1.setId(null);
        assertThat(anneeScolaire1).isNotEqualTo(anneeScolaire2);
    }
}
