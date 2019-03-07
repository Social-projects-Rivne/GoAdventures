package io.softserve.goadventures.event.model;

import io.softserve.goadventures.Gallery.model.Gallery;
import io.softserve.goadventures.event.category.Category;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude="category")
@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(mappedBy = "event_id")
    private Set<Gallery> event_Id = new HashSet<Gallery>();

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

    @ManyToOne(fetch = FetchType.LAZY)
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

    /*public Category getCategory() {
        return category;
    }
*/
    public void setCategory(Category category) {
        this.category = category;
    }
}
