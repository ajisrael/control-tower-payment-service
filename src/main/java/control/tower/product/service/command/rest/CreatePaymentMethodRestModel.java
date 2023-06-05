package control.tower.product.service.command.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@NoArgsConstructor
public class CreatePaymentMethodRestModel {

    @NotBlank(message = "User id is a required field")
    private String userId;
    @NotBlank(message = "Card number is a required field")
    private String cardNumber;
    @NotNull(message = "Expiration date is a required field")
    private Date expirationDate;
    @NotBlank(message = "Security code is a required field")
    private String securityCode;
}
