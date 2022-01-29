package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.RapportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rapport} and its DTO {@link RapportDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RapportMapper extends EntityMapper<RapportDTO, Rapport> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RapportDTO toDtoId(Rapport rapport);
}
