package io.softserve.goadventures.gallery.model;

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

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "gallery")
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