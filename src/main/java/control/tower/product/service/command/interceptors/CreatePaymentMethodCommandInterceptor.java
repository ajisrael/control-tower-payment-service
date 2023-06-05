package control.tower.product.service.command.interceptors;

import control.tower.product.service.command.CreatePaymentMethodCommand;
import control.tower.product.service.core.data.PaymentMethodLookupEntity;
import control.tower.product.service.core.data.PaymentMethodLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreatePaymentMethodCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreatePaymentMethodCommandInterceptor.class);

    private final PaymentMethodLookupRepository paymentMethodLookupRepository;

    public CreatePaymentMethodCommandInterceptor(PaymentMethodLookupRepository paymentMethodLookupRepository) {
        this.paymentMethodLookupRepository = paymentMethodLookupRepository;
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {

            LOGGER.info("Intercepted command: " + command.getPayloadType());

            if (CreatePaymentMethodCommand.class.equals(command.getPayloadType())) {

                CreatePaymentMethodCommand createPaymentMethodCommand = (CreatePaymentMethodCommand) command.getPayload();

                PaymentMethodLookupEntity paymentMethodLookupEntity = paymentMethodLookupRepository.findByPaymentIdOrCardNumber(
                        createPaymentMethodCommand.getPaymentId(), createPaymentMethodCommand.getCardNumber());

                if (paymentMethodLookupEntity != null) {
                    throw new IllegalStateException(
                            String.format("Payment method with id %s or card number %s already exists",
                                    createPaymentMethodCommand.getPaymentId(), createPaymentMethodCommand.getCardNumber())
                    );
                }
            }

            return command;
        };
    }
}
