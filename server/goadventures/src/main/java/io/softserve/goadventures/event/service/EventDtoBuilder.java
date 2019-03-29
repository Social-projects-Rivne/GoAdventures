package io.softserve.goadventures.event.service;

import io.softserve.goadventures.event.dto.EventDTO;
import io.softserve.goadventures.event.model.Event;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class EventDtoBuilder {
    @Autowired
    private ModelMapper modelMapper;

    public Page<EventDTO> convertToDto(Page<Event> event) {
        return modelMapper.map(event, new TypeToken<Page<EventDTO>>() {}.getType());
    }
}
