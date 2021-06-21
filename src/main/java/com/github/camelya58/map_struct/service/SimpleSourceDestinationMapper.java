package com.github.camelya58.map_struct.service;

import com.github.camelya58.map_struct.model.SimpleDestination;
import com.github.camelya58.map_struct.model.SimpleSource;
import org.mapstruct.Mapper;

/**
 * In case when naming the same fields names.
 */
@Mapper
public interface SimpleSourceDestinationMapper {

    SimpleDestination sourceToDestination(SimpleSource source);
    SimpleSource destinationToSource(SimpleDestination destination);
}
