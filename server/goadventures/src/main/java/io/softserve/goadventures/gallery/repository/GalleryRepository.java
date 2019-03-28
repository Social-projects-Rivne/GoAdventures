package io.softserve.goadventures.gallery.repository;

import io.softserve.goadventures.gallery.model.Gallery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GalleryRepository extends CrudRepository<Gallery, Integer> {
    Gallery findById(int id);
    Gallery findByEventId(int eventId);
}
