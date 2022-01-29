package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormateurDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormateurDTO.class);
        FormateurDTO formateurDTO1 = new FormateurDTO();
        formateurDTO1.setId(1L);
        FormateurDTO formateurDTO2 = new FormateurDTO();
        assertThat(formateurDTO1).isNotEqualTo(formateurDTO2);
        formateurDTO2.setId(formateurDTO1.getId());
        assertThat(formateurDTO1).isEqualTo(formateurDTO2);
        formateurDTO2.setId(2L);
        assertThat(formateurDTO1).isNotEqualTo(formateurDTO2);
        formateurDTO1.setId(null);
        assertThat(formateurDTO1).isNotEqualTo(formateurDTO2);
    }
}
