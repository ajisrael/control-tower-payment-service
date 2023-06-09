package control.tower.payment.service.command.interceptors;

import control.tower.payment.service.command.commands.CreatePaymentMethodCommand;
import control.tower.payment.service.core.data.PaymentMethodLookupEntity;
import control.tower.payment.service.core.data.PaymentMethodLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

import static control.tower.payment.service.core.utils.PaymentMethodHasher.createPaymentMethodHash;

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


            if (CreatePaymentMethodCommand.class.equals(command.getPayloadType())) {
                LOGGER.info("Intercepted command: " + command.getPayloadType());

                CreatePaymentMethodCommand createPaymentMethodCommand = (CreatePaymentMethodCommand) command.getPayload();

                PaymentMethodLookupEntity paymentMethodLookupEntity = paymentMethodLookupRepository.findByPaymentId(
                        createPaymentMethodCommand.getPaymentId());

                if (paymentMethodLookupEntity != null) {
                    throw new IllegalStateException(
                            String.format("Payment method with id %s already exists",
                                    createPaymentMethodCommand.getPaymentId())
                    );
                }

                paymentMethodLookupEntity = paymentMethodLookupRepository
                        .findByPaymentMethodHash(createPaymentMethodHash(createPaymentMethodCommand));

                if (paymentMethodLookupEntity != null) {
                    throw new IllegalStateException("This payment method already exists for this user");
                }
            }

            return command;
        };
    }
}
