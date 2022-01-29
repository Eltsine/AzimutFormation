package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PeriodeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Periode} and its DTO {@link PeriodeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PeriodeMapper extends EntityMapper<PeriodeDTO, Periode> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PeriodeDTO toDtoId(Periode periode);
}
