package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InscriptionMapperTest {

    private InscriptionMapper inscriptionMapper;

    @BeforeEach
    public void setUp() {
        inscriptionMapper = new InscriptionMapperImpl();
    }
}
