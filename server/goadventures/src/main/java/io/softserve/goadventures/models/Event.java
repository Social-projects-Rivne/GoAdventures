package io.softserve.goadventures.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "status_id")
    private int statusId;

    @JsonManagedReference()
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "eventId")
    @JoinColumn(name = "gallery", referencedColumnName = "id")
    private Gallery gallery;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "event_participants", joinColumns = {
            @JoinColumn(name = "event_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "users_id", referencedColumnName = "id") })
    private Set<User> participants = new HashSet<>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "owner")
    private User owner;

    public Event(String topic, String startDate, String endDate, String location, Double latitude, Double longitude,
            String description, Category category) {
        setTopic(topic);
        setStartDate(startDate);
        setEndDate(endDate);
        setLocation(location);
        setLatitude(latitude);
        setLongitude(longitude);
        setDescription(description);
        setCategory(category);
    }

    @Override
    public String toString() {
        return "\nEvent{" +
                "\n\tid=" + id +
                ", \n\ttopic='" + topic + '\'' +
                ", \n\tstartDate='" + startDate + '\'' +
                ", \n\tendDate='" + endDate + '\'' +
                ", \n\tlocation='" + location + '\'' +
                ", \n\tdescription='" + description + '\'' +
                ", \n\tstatusId=" + statusId + '\'' +
                ", \n\towner=" + owner + '\'' +
                ", \n\tcategory=" + category.getCategoryName() +
                ", \n\tparticipants=" + participants +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Event event = (Event) o;
        return id == event.id && topic.equals(event.topic) && startDate.equals(event.startDate)
                && endDate.equals(event.endDate) && location.equals(event.location) && category.equals(event.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topic, startDate, endDate, location, latitude, longitude, category);
    }
}