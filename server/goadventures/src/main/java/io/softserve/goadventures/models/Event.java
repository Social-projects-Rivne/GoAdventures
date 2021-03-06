package io.softserve.goadventures.models;

import com.fasterxml.jackson.annotation.*;
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

    @JsonManagedReference // TODO it is better to use dto models for json. You need to separate jpa logic
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE,
            CascadeType.REFRESH }, mappedBy = "eventId", orphanRemoval = true)
    @JoinColumn(name = "gallery", referencedColumnName = "id")
    private Gallery gallery;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "event")
    Set<EventParticipants> participants = new HashSet<>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "owner")
    private User owner;


    public Event(int id)
    {
        setId(id);
    }

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
        return "Event{" +
                "topic='" + topic + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", description='" + description + '\'' +
                ", statusId=" + statusId +
                ", gllr=" + gallery +
                ", ctg=" + category +
                ", prtcpnts=" + participants +
                ", owner=" + owner +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Event event = (Event) o;
        return id == event.id && owner.equals(event.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner);
    }
}
