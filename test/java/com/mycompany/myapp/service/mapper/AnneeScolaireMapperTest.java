package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnneeScolaireMapperTest {

    private AnneeScolaireMapper anneeScolaireMapper;

    @BeforeEach
    public void setUp() {
        anneeScolaireMapper = new AnneeScolaireMapperImpl();
    }
}
