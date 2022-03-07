package uz.pdp.pdp_food_delivery.rest.dto.feedback;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.dto.BaseDto;
import uz.pdp.pdp_food_delivery.rest.entity.AuthUser;
import uz.pdp.pdp_food_delivery.rest.enums.Type;

@Getter
@Setter
public class FeedbackCreateDto implements BaseDto {

    private String message;

    private Long  user;

    private Type type;

}
