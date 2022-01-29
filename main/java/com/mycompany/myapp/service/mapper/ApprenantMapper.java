package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ApprenantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Apprenant} and its DTO {@link ApprenantDTO}.
 */
@Mapper(componentModel = "spring", uses = { InscriptionMapper.class, FormateurMapper.class })
public interface ApprenantMapper extends EntityMapper<ApprenantDTO, Apprenant> {
    @Mapping(target = "inscription", source = "inscription", qualifiedByName = "id")
    @Mapping(target = "formateur", source = "formateur", qualifiedByName = "id")
    ApprenantDTO toDto(Apprenant s);
}
