package io.softserve.goadventures.event.repository;


import io.softserve.goadventures.event.category.Category;
import io.softserve.goadventures.event.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Integer> {
    Event findByTopic(String topic);

    Event findById(int id);

    Page<Event> findByCategoryId(int eventId, Pageable pageable);

    Page<Event> findAllByTopic(Pageable pageable, String topic);

    Page<Event> findAllByCategory(Pageable pageable, Category category);
}
