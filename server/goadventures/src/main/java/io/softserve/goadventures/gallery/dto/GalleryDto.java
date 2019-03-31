package io.softserve.goadventures.gallery.dto;
import io.softserve.goadventures.event.model.Event;
import java.util.List;

public class GalleryDto {
  private Event eventId;
  private List<String> imageUrls;
  private Boolean isDeleted;
}
