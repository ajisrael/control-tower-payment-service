package control.tower.payment.service.command.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class RemovePaymentMethodRestModel {

    @NotBlank(message = "Payment Id is a required field")
    private String paymentId;
}
