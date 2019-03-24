package io.softserve.goadventures.event.repository;

import java.awt.print.Pageable;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import sun.jvm.hotspot.debugger.Page;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Integer> {
    Event findByTopic(String topic);

    Event findById(int id);

    Page<Event> findByCategoryId(int eventId, Pageable pageable);

    Page<Event> findAllByTopic(Pageable pageable, String topic);
    // Optional<Event> findByIdAndCategoryId(int id, int eventId);
}
