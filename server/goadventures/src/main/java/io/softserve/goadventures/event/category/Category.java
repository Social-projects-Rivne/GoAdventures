package io.softserve.goadventures.event.category;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.softserve.goadventures.event.model.Event;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "category_name")
    private String categoryName;

    @JsonManagedReference
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Event> events;

    public Category(String categoryName){
        this.categoryName = categoryName;
    }
}
