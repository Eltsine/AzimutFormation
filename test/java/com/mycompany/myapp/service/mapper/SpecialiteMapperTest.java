package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpecialiteMapperTest {

    private SpecialiteMapper specialiteMapper;

    @BeforeEach
    public void setUp() {
        specialiteMapper = new SpecialiteMapperImpl();
    }
}
