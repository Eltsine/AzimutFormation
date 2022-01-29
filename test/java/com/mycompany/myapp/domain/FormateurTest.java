package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Formateur.class);
        Formateur formateur1 = new Formateur();
        formateur1.setId(1L);
        Formateur formateur2 = new Formateur();
        formateur2.setId(formateur1.getId());
        assertThat(formateur1).isEqualTo(formateur2);
        formateur2.setId(2L);
        assertThat(formateur1).isNotEqualTo(formateur2);
        formateur1.setId(null);
        assertThat(formateur1).isNotEqualTo(formateur2);
    }
}
