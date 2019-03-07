package io.softserve.goadventures.event.category;

import io.softserve.goadventures.event.model.Event;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(exclude="events")
@Getter
@Setter
@Entity
@Table(name = "category")
@NoArgsConstructor
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Event> events;

    public Category(String category){
        this.category = category;
    }


    /*public void addEvent(Event event) {
        events.add(event);
        events.setEvents(this);
    }

    public void removeEvent(Event event) {
        events.remove(event);
        events.setEvents(null);
    }*/

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }


}
