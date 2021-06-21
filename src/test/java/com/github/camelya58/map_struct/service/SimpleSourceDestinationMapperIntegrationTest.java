package com.github.camelya58.map_struct.service;

import com.github.camelya58.map_struct.model.SimpleDestination;
import com.github.camelya58.map_struct.model.SimpleSource;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleSourceDestinationMapperIntegrationTest {
    private final SimpleSourceDestinationMapper mapper =
            Mappers.getMapper(SimpleSourceDestinationMapper.class);

    @Test
    public void givenSourceToDestination() {
        SimpleSource source = new SimpleSource();
        source.setName("SourceName");
        source.setDescription("SourceDescription");
        SimpleDestination destination = mapper.sourceToDestination(source);

        assertEquals(source.getName(), destination.getName());
        assertEquals(source.getDescription(), destination.getDescription());

    }

    @Test
    public void givenDestinationToSource() {
        SimpleDestination destination = new SimpleDestination();
        destination.setName("DestinationName");
        destination.setDescription("DestinationDescription");
        SimpleSource source = mapper.destinationToSource(destination);

        assertEquals(destination.getName(), source.getName());
        assertEquals(destination.getDescription(),
                source.getDescription());
    }
}
