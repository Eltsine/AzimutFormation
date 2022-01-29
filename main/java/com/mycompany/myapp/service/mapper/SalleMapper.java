package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SalleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Salle} and its DTO {@link SalleDTO}.
 */
@Mapper(componentModel = "spring", uses = { SessionMapper.class })
public interface SalleMapper extends EntityMapper<SalleDTO, Salle> {
    @Mapping(target = "session", source = "session", qualifiedByName = "id")
    SalleDTO toDto(Salle s);
}
