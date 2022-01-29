package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SpecialiteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Specialite} and its DTO {@link SpecialiteDTO}.
 */
@Mapper(componentModel = "spring", uses = { FormateurMapper.class })
public interface SpecialiteMapper extends EntityMapper<SpecialiteDTO, Specialite> {
    @Mapping(target = "formateur", source = "formateur", qualifiedByName = "id")
    SpecialiteDTO toDto(Specialite s);
}
