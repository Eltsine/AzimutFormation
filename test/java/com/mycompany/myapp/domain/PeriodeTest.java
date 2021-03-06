package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PeriodeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Periode.class);
        Periode periode1 = new Periode();
        periode1.setId(1L);
        Periode periode2 = new Periode();
        periode2.setId(periode1.getId());
        assertThat(periode1).isEqualTo(periode2);
        periode2.setId(2L);
        assertThat(periode1).isNotEqualTo(periode2);
        periode1.setId(null);
        assertThat(periode1).isNotEqualTo(periode2);
    }
}
