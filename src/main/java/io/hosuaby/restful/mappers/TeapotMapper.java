package io.hosuaby.restful.mappers;

import io.hosuaby.restful.domain.Teapot;
import io.hosuaby.restful.mappings.TeapotMapping;
import io.hosuaby.restful.mappings.TeapotState;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper between {@link Teapot} and {@link TeapotMapping}.
 */
@Mapper(imports = TeapotState.class)
public abstract class TeapotMapper {

    /**
     * Creates mapping from teapot.
     *
     * @param teapot    teapot
     *
     * @return  teapot mapping
     */
    @Mapping(
            target = "state",
            expression = "java("
                + "teapot.getIp() != null ? TeapotState.IDLE"
                + "                       : TeapotState.UNAVAILABLE"
            + ")")
    public abstract TeapotMapping toMapping(Teapot teapot);

    /**
     * Creates collection of teapot mappings from the collection of original
     * teapots.
     *
     * @param teapots    collection of teapots
     *
     * @return    collection of teapot mappings
     */
    public abstract Collection<TeapotMapping> toMappings(Collection<Teapot> teapots);

    /**
     * Creates a new teapot from teapot mapping.
     *
     * @param mapping    teapot mapping
     *
     * @return new teapot
     */
    @Mapping(target = "ip", ignore = true)
    public abstract Teapot fromMapping(TeapotMapping mapping);

    /**
     * Updates existing teapot with value from mapping.
     *
     * @param mapping    teapot mapping
     * @param teapot     teapot
     */
    public abstract void fromMapping(TeapotMapping mapping,
            @MappingTarget Teapot teapot);

}
