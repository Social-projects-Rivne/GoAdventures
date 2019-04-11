package io.softserve.goadventures.dto;

import io.softserve.goadventures.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private int id;

    private String topic;

    private String startDate;

    private String endDate;

    private String location;

    private Double latitude;

    private Double longitude;

    private String description;

    private int statusId;

    private String category;

    private GalleryDto gallery;
}