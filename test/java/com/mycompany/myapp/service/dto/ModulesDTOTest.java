package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModulesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModulesDTO.class);
        ModulesDTO modulesDTO1 = new ModulesDTO();
        modulesDTO1.setId(1L);
        ModulesDTO modulesDTO2 = new ModulesDTO();
        assertThat(modulesDTO1).isNotEqualTo(modulesDTO2);
        modulesDTO2.setId(modulesDTO1.getId());
        assertThat(modulesDTO1).isEqualTo(modulesDTO2);
        modulesDTO2.setId(2L);
        assertThat(modulesDTO1).isNotEqualTo(modulesDTO2);
        modulesDTO1.setId(null);
        assertThat(modulesDTO1).isNotEqualTo(modulesDTO2);
    }
}
