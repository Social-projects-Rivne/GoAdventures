package io.softserve.goadventures.repositories;

import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.Feedback;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Long> {
    Slice<Feedback> findFeedbackByEventId(Event eventId, Pageable feedbackPage);

}
