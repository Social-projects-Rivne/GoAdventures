package io.softserve.goadventures.repositories;

import io.softserve.goadventures.models.EventParticipants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventParticipantsRepository extends JpaRepository<EventParticipants, Integer> {

    EventParticipants findById(int id);

}