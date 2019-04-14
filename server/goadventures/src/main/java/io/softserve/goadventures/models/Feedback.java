package io.softserve.goadventures.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedback")
public class Feedback {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private long id;

  @JsonBackReference(value = "userId")
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinColumn(name = "users", nullable = false, referencedColumnName = "id")
  private User userId;

  @JsonBackReference(value = "eventId")
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinColumn(name = "events_id", nullable = false, referencedColumnName = "id")
  private Event eventId;

  @Column(name = "comment", nullable = false)
  @Size(max = 300)
  private String comment;

  @CreatedDate
  @Column(name = "created_date")
  private Date createdDate;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Feedback feedback = (Feedback) o;
    return id == feedback.id &&
            userId.equals(feedback.userId) &&
            createdDate.equals(feedback.createdDate) &&
            eventId.equals(feedback.eventId) &&
            comment.equals(feedback.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, comment, createdDate, eventId, userId);
  }
}

