package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.InscriptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inscription} and its DTO {@link InscriptionDTO}.
 */
@Mapper(componentModel = "spring", uses = { PeriodeMapper.class, EtablissementMapper.class, AnneeScolaireMapper.class })
public interface InscriptionMapper extends EntityMapper<InscriptionDTO, Inscription> {
    @Mapping(target = "periode", source = "periode", qualifiedByName = "id")
    @Mapping(target = "etablissement", source = "etablissement", qualifiedByName = "id")
    @Mapping(target = "anneeScolaire", source = "anneeScolaire", qualifiedByName = "id")
    InscriptionDTO toDto(Inscription s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    InscriptionDTO toDtoId(Inscription inscription);
}
