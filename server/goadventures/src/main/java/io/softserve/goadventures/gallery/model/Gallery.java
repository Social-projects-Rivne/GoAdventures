package io.softserve.goadventures.gallery.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.softserve.goadventures.event.model.Event;
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
  @JoinColumn(name = "event_id")
  private Event eventId;

  @ElementCollection()
  @Column(name = "image_urls")
  private Set<String> imageUrls = new HashSet<>();

  @Column(name = "is_deleted")
  private Boolean isDeleted;

  public void setEventId(Event eventId) {
    this.eventId = eventId;
  }
}