package uz.pdp.pdp_food_delivery.rest.dto.feedback;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.dto.GenericDto;
import uz.pdp.pdp_food_delivery.rest.entity.AuthUser;
import uz.pdp.pdp_food_delivery.rest.enums.Type;

@Getter
@Setter
public class FeedbackDto extends GenericDto {

    private String message;

    private Long user;

    private Type type;

}
