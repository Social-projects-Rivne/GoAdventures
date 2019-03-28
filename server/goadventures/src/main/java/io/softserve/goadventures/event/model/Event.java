package io.softserve.goadventures.event.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.softserve.goadventures.event.category.Category;
import io.softserve.goadventures.gallery.model.Gallery;
import io.softserve.goadventures.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "status_id")
    private int statusId;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "eventId")
    @JoinColumn(name = "gallery", referencedColumnName = "id")
    private Gallery gallery;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "event_participants",
            joinColumns = { @JoinColumn(name = "event_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "users_id", referencedColumnName = "id") }
    )
    private Set<User> participants = new HashSet<>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "owner")
    private User owner;

    public Event(String topic, String startDate, String endDate, String location, String description, Category category) {
        setTopic(topic);
        setStartDate(startDate);
        setEndDate(endDate);
        setLocation(location);
        setDescription(description);
        setCategory(category);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", statusId=" + statusId + '\'' +
                ", owner=" + owner + '\'' +/*
                ", category=" + category.getCategoryName() +
                ", participants=" + participants +*/
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id &&
                topic.equals(event.topic) &&
                startDate.equals(event.startDate) &&
                endDate.equals(event.endDate) &&
                location.equals(event.location) &&
                category.equals(event.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topic, startDate, endDate, location, category);
    }
}
