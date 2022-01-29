package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.FormateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Formateur} and its DTO {@link FormateurDTO}.
 */
@Mapper(componentModel = "spring", uses = { EtablissementMapper.class })
public interface FormateurMapper extends EntityMapper<FormateurDTO, Formateur> {
    @Mapping(target = "etablissement", source = "etablissement", qualifiedByName = "id")
    FormateurDTO toDto(Formateur s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FormateurDTO toDtoId(Formateur formateur);
}
