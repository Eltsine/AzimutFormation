package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.FormationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Formation} and its DTO {@link FormationDTO}.
 */
@Mapper(componentModel = "spring", uses = { RapportMapper.class, InscriptionMapper.class })
public interface FormationMapper extends EntityMapper<FormationDTO, Formation> {
    @Mapping(target = "rapport", source = "rapport", qualifiedByName = "id")
    @Mapping(target = "inscription", source = "inscription", qualifiedByName = "id")
    FormationDTO toDto(Formation s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FormationDTO toDtoId(Formation formation);
}
