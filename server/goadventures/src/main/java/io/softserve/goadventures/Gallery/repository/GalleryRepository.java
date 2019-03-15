package io.softserve.goadventures.Gallery.repository;

import io.softserve.goadventures.Gallery.model.Gallery;
import io.softserve.goadventures.event.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Integer> {
    Gallery findById(int id);
    List<Gallery> findByEventId(int eventId);
    // Optional<Gallery> findByIdAndEventId(int id, int eventId);
}
