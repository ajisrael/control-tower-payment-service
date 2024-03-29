package control.tower.payment.service.command.rest;

import control.tower.payment.service.command.commands.CreatePaymentMethodCommand;
import control.tower.core.commands.RemovePaymentMethodCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/paymentmethods")
public class PaymentMethodsCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String createPaymentMethod(@Valid @RequestBody CreatePaymentMethodRestModel createPaymentMethodRestModel) {
        CreatePaymentMethodCommand createPaymentMethodCommand = CreatePaymentMethodCommand.builder()
                .paymentId(UUID.randomUUID().toString())
                .userId(createPaymentMethodRestModel.getUserId())
                .cardNumber(createPaymentMethodRestModel.getCardNumber())
                .expirationDate(createPaymentMethodRestModel.getExpirationDate())
                .securityCode(createPaymentMethodRestModel.getSecurityCode())
                .build();

        return commandGateway.sendAndWait(createPaymentMethodCommand);
    }

    @DeleteMapping
    public String removePaymentMethod(@Valid @RequestBody RemovePaymentMethodRestModel removePaymentMethodRestModel) {
        RemovePaymentMethodCommand removePaymentMethodCommand = RemovePaymentMethodCommand.builder()
                .paymentId(removePaymentMethodRestModel.getPaymentId())
                .build();

        return commandGateway.sendAndWait(removePaymentMethodCommand);
    }
}


