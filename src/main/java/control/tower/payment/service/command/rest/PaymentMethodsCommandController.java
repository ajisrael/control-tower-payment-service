package control.tower.payment.service.command.rest;

import control.tower.payment.service.command.commands.CreatePaymentMethodCommand;
import control.tower.core.commands.RemovePaymentMethodCommand;
import control.tower.payment.service.command.rest.requests.CreatePaymentMethodRequestModel;
import control.tower.payment.service.command.rest.requests.RemovePaymentMethodRequestModel;
import control.tower.payment.service.command.rest.responses.PaymentMethodCreatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create payment method")
    public PaymentMethodCreatedResponse createPaymentMethod(@Valid @RequestBody CreatePaymentMethodRequestModel createPaymentMethodRequestModel) {
        CreatePaymentMethodCommand createPaymentMethodCommand = CreatePaymentMethodCommand.builder()
                .paymentId(UUID.randomUUID().toString())
                .userId(createPaymentMethodRequestModel.getUserId())
                .cardNumber(createPaymentMethodRequestModel.getCardNumber())
                .expirationDate(createPaymentMethodRequestModel.getExpirationDate())
                .securityCode(createPaymentMethodRequestModel.getSecurityCode())
                .build();

        String paymentId = commandGateway.sendAndWait(createPaymentMethodCommand);

        return PaymentMethodCreatedResponse.builder().paymentId(paymentId).build();
    }

    @DeleteMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove payment method")
    public void removePaymentMethod(@Valid @RequestBody RemovePaymentMethodRequestModel removePaymentMethodRequestModel) {
        RemovePaymentMethodCommand removePaymentMethodCommand = RemovePaymentMethodCommand.builder()
                .paymentId(removePaymentMethodRequestModel.getPaymentId())
                .build();

        commandGateway.sendAndWait(removePaymentMethodCommand);
    }
}


