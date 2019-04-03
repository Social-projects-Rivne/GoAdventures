package io.softserve.goadventures.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.softserve.goadventures.models.Event;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;

@Entity
@Table(name = "gallery")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Gallery {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "events_id")
    private Event eventId;

    @ElementCollection()
    @Column(name = "image_urls")
    private HashSet<String> imageUrls;

    @Column(name = "is_deleted")
    private Boolean isDeleted;


}