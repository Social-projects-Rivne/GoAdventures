package io.softserve.goadventures.gallery.model;

import io.softserve.goadventures.event.model.Event;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "gallery")
@Data
@NoArgsConstructor
@AllArgsConstructor
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