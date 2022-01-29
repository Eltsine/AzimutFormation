package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RapportMapperTest {

    private RapportMapper rapportMapper;

    @BeforeEach
    public void setUp() {
        rapportMapper = new RapportMapperImpl();
    }
}
