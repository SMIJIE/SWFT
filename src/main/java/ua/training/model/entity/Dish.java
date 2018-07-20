package ua.training.model.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import ua.training.model.dao.utility.SortAnnotation;
import ua.training.model.entity.enums.FoodCategory;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "dish")
public class Dish implements EntityObject<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idD", nullable = false)
    private Integer id;
    /**
     * Category of dishes
     */
    @SortAnnotation
    @Column(name = "category")
    private FoodCategory foodCategory;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer weight;
    @SortAnnotation
    @Column(nullable = false)
    private Integer calories;
    @Column(nullable = false)
    private Integer proteins;
    @Column(nullable = false)
    private Integer fats;
    @Column(nullable = false)
    private Integer carbohydrates;
    @ManyToOne
    @JoinColumn(name = "idUsers")
    private User user;
    /**
     * Parameter for basic and custom dishes
     */
    @Column(nullable = false,columnDefinition = "TINYINT(1)")
    private Boolean generalFood;
}
