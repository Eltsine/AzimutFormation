package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.AnneeScolaireDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnneeScolaire} and its DTO {@link AnneeScolaireDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnneeScolaireMapper extends EntityMapper<AnneeScolaireDTO, AnneeScolaire> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AnneeScolaireDTO toDtoId(AnneeScolaire anneeScolaire);
}
