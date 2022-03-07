package uz.pdp.pdp_food_delivery.rest.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import uz.pdp.pdp_food_delivery.rest.entity.base.Auditable;
import uz.pdp.pdp_food_delivery.rest.enums.Type;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "feedback", schema = "feedback")
public class Feedback extends Auditable {

    private String message;

    @OneToOne
    private AuthUser user;

    @Enumerated(EnumType.STRING)
    private Type type;

}
