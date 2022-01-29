package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApprenantMapperTest {

    private ApprenantMapper apprenantMapper;

    @BeforeEach
    public void setUp() {
        apprenantMapper = new ApprenantMapperImpl();
    }
}
