package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalleDTO.class);
        SalleDTO salleDTO1 = new SalleDTO();
        salleDTO1.setId(1L);
        SalleDTO salleDTO2 = new SalleDTO();
        assertThat(salleDTO1).isNotEqualTo(salleDTO2);
        salleDTO2.setId(salleDTO1.getId());
        assertThat(salleDTO1).isEqualTo(salleDTO2);
        salleDTO2.setId(2L);
        assertThat(salleDTO1).isNotEqualTo(salleDTO2);
        salleDTO1.setId(null);
        assertThat(salleDTO1).isNotEqualTo(salleDTO2);
    }
}
