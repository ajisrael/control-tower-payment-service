package control.tower.payment.service.command.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class RemovePaymentMethodRequestModel {

    @NotBlank(message = "PaymentId is a required field")
    private String paymentId;
}
