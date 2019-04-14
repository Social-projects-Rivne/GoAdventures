package io.softserve.goadventures.repositories;

import io.softserve.goadventures.models.Feedback;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Long> {
  @Query("from Feedback where events_id = :eventId")
  Set<Feedback> findByEventId(@Param("eventId") int eventId);
}
