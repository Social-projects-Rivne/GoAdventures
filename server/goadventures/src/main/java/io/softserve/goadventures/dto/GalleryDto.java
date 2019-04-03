package io.softserve.goadventures.dto;
import io.softserve.goadventures.models.Event;
import lombok.*;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GalleryDto {
    private long id;
    private int eventId;
    private HashSet<String> imageUrls;
    private Boolean isDeleted;
}
