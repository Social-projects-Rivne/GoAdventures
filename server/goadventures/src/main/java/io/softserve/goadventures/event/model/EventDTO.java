package io.softserve.goadventures.event.model;


import io.softserve.goadventures.Gallery.model.Gallery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private int id;

    private String topic;

    private Date startDate;

    private String endDate;

    private String location;

    private String description;

    private int statusId;

    private String categoryName;

    private List<String> galleries;


}
