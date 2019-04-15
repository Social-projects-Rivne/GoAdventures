package io.softserve.goadventures.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.softserve.goadventures.models.Event;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "events_id")
    private Event eventId;

    //TODO Need to check. This field should have the name of the image. And the url should be built by the app
    @ElementCollection()
    @Column(name = "image_urls")
    private Set<String> imageUrls = new HashSet<>();

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public void setEventId(Event eventId) {
        this.eventId = eventId;
    }
}