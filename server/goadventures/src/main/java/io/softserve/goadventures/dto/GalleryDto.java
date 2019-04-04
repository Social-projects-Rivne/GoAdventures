package io.softserve.goadventures.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GalleryDto {
    private int eventId;
    private Set<String> imageUrls;
    private Boolean isDeleted;
}
