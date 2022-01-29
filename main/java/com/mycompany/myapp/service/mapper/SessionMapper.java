package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SessionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Session} and its DTO {@link SessionDTO}.
 */
@Mapper(componentModel = "spring", uses = { ModulesMapper.class })
public interface SessionMapper extends EntityMapper<SessionDTO, Session> {
    @Mapping(target = "modules", source = "modules", qualifiedByName = "id")
    SessionDTO toDto(Session s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SessionDTO toDtoId(Session session);
}
