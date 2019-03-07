package io.softserve.goadventures.Gallery.model;

import io.softserve.goadventures.event.model.Event;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "gallery")
@Data
@NoArgsConstructor
public class Gallery {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event_id;

    @Column(name = "image_Url")
    private String imageUrl;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}