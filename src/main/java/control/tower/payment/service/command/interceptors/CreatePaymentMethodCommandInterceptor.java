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

import static control.tower.core.constants.LogMessages.INTERCEPTED_COMMAND;
import static control.tower.core.utils.Helper.throwExceptionIfEntityDoesExist;
import static control.tower.payment.service.core.constants.ExceptionMessages.*;
import static control.tower.payment.service.core.utils.PaymentMethodHasher.createPaymentMethodHash;
import static control.tower.core.utils.WebClientService.doesUserExist;

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
                LOGGER.info(String.format(INTERCEPTED_COMMAND, command.getPayloadType()));

                CreatePaymentMethodCommand createPaymentMethodCommand = (CreatePaymentMethodCommand) command.getPayload();

                createPaymentMethodCommand.validate();

                boolean userDoesExist = doesUserExist(createPaymentMethodCommand.getUserId());

                if (!userDoesExist) {
                    throw new IllegalArgumentException(String.format(
                            USER_WIth_ID_DOES_NOT_EXIST_CANNOT_CREATE_PAYMENT_METHOD,
                            createPaymentMethodCommand.getUserId()));
                }

                PaymentMethodLookupEntity paymentMethodLookupEntity = paymentMethodLookupRepository.findByPaymentId(
                        createPaymentMethodCommand.getPaymentId());

                throwExceptionIfEntityDoesExist(paymentMethodLookupEntity,
                        String.format(PAYMENT_METHOD_WITH_ID_ALREADY_EXISTS, createPaymentMethodCommand.getPaymentId()));

                paymentMethodLookupEntity = paymentMethodLookupRepository
                        .findByPaymentMethodHash(createPaymentMethodHash(createPaymentMethodCommand));

                throwExceptionIfEntityDoesExist(paymentMethodLookupEntity, PAYMENT_METHOD_ALREADY_EXISTS_FOR_USER);
            }

            return command;
        };
    }
}
