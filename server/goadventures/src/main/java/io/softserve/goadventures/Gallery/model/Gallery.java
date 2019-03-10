package io.softserve.goadventures.Gallery.model;

import io.softserve.goadventures.event.model.Event;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "gallery")
@Getter
@Setter
@NoArgsConstructor
public class Gallery {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event eventId;

    @Column(name = "image_Url")
    private String imageUrl;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public void setEventId(Event eventId) {
        this.eventId = eventId;
    }
}