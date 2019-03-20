package io.softserve.goadventures.event.repository;

import io.softserve.goadventures.event.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Integer> {
    Event findByTopic(String topic);
    Event findById(int id);
    Page<Event> findByCategoryId(int eventId, Pageable pageable);
    Page<Event> findAllByTopic(Pageable pageable,String topic);
    //Optional<Event> findByIdAndCategoryId(int id, int eventId);
}

