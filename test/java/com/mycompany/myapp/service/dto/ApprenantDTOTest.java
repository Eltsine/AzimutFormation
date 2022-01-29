package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApprenantDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApprenantDTO.class);
        ApprenantDTO apprenantDTO1 = new ApprenantDTO();
        apprenantDTO1.setId(1L);
        ApprenantDTO apprenantDTO2 = new ApprenantDTO();
        assertThat(apprenantDTO1).isNotEqualTo(apprenantDTO2);
        apprenantDTO2.setId(apprenantDTO1.getId());
        assertThat(apprenantDTO1).isEqualTo(apprenantDTO2);
        apprenantDTO2.setId(2L);
        assertThat(apprenantDTO1).isNotEqualTo(apprenantDTO2);
        apprenantDTO1.setId(null);
        assertThat(apprenantDTO1).isNotEqualTo(apprenantDTO2);
    }
}
