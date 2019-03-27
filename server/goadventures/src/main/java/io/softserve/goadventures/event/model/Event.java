package io.softserve.goadventures.event.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.softserve.goadventures.event.category.Category;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "description", columnDefinition="TEXT")
    private String description;

    @Column(name = "status_id")
    private int statusId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    public Event(String topic, String startDate, String endDate, String location, Double latitude, Double longitude, String description, Category category) {
        setTopic(topic);
        setStartDate(startDate);
        setEndDate(endDate);
        setLocation(location);
        setLatitude(latitude);
        setLongitude(longitude);
        setDescription(description);
        setCategory(category);
    }
}
