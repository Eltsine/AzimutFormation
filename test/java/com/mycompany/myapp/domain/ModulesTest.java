package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModulesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Modules.class);
        Modules modules1 = new Modules();
        modules1.setId(1L);
        Modules modules2 = new Modules();
        modules2.setId(modules1.getId());
        assertThat(modules1).isEqualTo(modules2);
        modules2.setId(2L);
        assertThat(modules1).isNotEqualTo(modules2);
        modules1.setId(null);
        assertThat(modules1).isNotEqualTo(modules2);
    }
}
