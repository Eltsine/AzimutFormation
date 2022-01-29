package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ModulesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Modules} and its DTO {@link ModulesDTO}.
 */
@Mapper(componentModel = "spring", uses = { FormationMapper.class })
public interface ModulesMapper extends EntityMapper<ModulesDTO, Modules> {
    @Mapping(target = "formation", source = "formation", qualifiedByName = "id")
    ModulesDTO toDto(Modules s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModulesDTO toDtoId(Modules modules);
}
