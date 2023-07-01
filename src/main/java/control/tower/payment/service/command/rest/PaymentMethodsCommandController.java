package control.tower.payment.service.command.rest;

import control.tower.payment.service.command.commands.CreatePaymentMethodCommand;
import control.tower.core.commands.RemovePaymentMethodCommand;
import control.tower.payment.service.command.rest.requests.CreatePaymentMethodRequestModel;
import control.tower.payment.service.command.rest.requests.RemovePaymentMethodRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/paymentmethods")
@Tag(name = "Payment Command API")
public class PaymentMethodsCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String createPaymentMethod(@Valid @RequestBody CreatePaymentMethodRequestModel createPaymentMethodRequestModel) {
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create payment method")
        CreatePaymentMethodCommand createPaymentMethodCommand = CreatePaymentMethodCommand.builder()
                .paymentId(UUID.randomUUID().toString())
                .userId(createPaymentMethodRequestModel.getUserId())
                .cardNumber(createPaymentMethodRequestModel.getCardNumber())
                .expirationDate(createPaymentMethodRequestModel.getExpirationDate())
                .securityCode(createPaymentMethodRequestModel.getSecurityCode())
                .build();

        return commandGateway.sendAndWait(createPaymentMethodCommand);
    }

    @DeleteMapping
    public String removePaymentMethod(@Valid @RequestBody RemovePaymentMethodRequestModel removePaymentMethodRequestModel) {
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove payment method")
        RemovePaymentMethodCommand removePaymentMethodCommand = RemovePaymentMethodCommand.builder()
                .paymentId(removePaymentMethodRequestModel.getPaymentId())
                .build();

        return commandGateway.sendAndWait(removePaymentMethodCommand);
    }
}


