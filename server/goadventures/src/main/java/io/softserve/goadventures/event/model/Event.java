package io.softserve.goadventures.event.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.softserve.goadventures.event.category.Category;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    public Event(String topic, String startDate, String endDate, String location, String description, Category category) {
        setTopic(topic);
        setStartDate(startDate);
        setEndDate(endDate);
        setLocation(location);
        setDescription(description);
        setCategory(category);
    }
}
