package io.softserve.goadventures.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

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
    private int id;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "events_id", unique = true)
    private Event eventId;

    //TODO Need to check. This field should have the name of the image. And the url should be built by the app
    @ElementCollection()
    @Column(name = "image_urls")
    private Set<String> imageUrls;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}