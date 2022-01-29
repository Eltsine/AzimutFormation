package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormateurMapperTest {

    private FormateurMapper formateurMapper;

    @BeforeEach
    public void setUp() {
        formateurMapper = new FormateurMapperImpl();
    }
}
