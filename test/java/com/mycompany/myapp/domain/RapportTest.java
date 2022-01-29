package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RapportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rapport.class);
        Rapport rapport1 = new Rapport();
        rapport1.setId(1L);
        Rapport rapport2 = new Rapport();
        rapport2.setId(rapport1.getId());
        assertThat(rapport1).isEqualTo(rapport2);
        rapport2.setId(2L);
        assertThat(rapport1).isNotEqualTo(rapport2);
        rapport1.setId(null);
        assertThat(rapport1).isNotEqualTo(rapport2);
    }
}
