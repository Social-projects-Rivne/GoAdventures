package io.softserve.goadventures.repositories;

import io.softserve.goadventures.models.Feedback;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Long> {
  Set<Feedback> findByEventId(int eventId);
}
