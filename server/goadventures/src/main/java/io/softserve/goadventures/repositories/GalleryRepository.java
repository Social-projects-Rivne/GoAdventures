package io.softserve.goadventures.repositories;

import io.softserve.goadventures.models.Gallery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepository extends CrudRepository<Gallery, Long> {
    Gallery findById(long id);
    Gallery findByEventId(int eventId);
}