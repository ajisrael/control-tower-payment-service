package control.tower.payment.service.command.interceptors;

import control.tower.core.commands.RemovePaymentMethodCommand;
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
import static control.tower.core.utils.Helper.throwExceptionIfEntityDoesNotExist;
import static control.tower.payment.service.core.constants.ExceptionMessages.*;

@Component
public class RemovePaymentMethodCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemovePaymentMethodCommandInterceptor.class);

    private final PaymentMethodLookupRepository paymentMethodLookupRepository;

    public RemovePaymentMethodCommandInterceptor(PaymentMethodLookupRepository paymentMethodLookupRepository) {
        this.paymentMethodLookupRepository = paymentMethodLookupRepository;
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {

            if (RemovePaymentMethodCommand.class.equals(command.getPayloadType())) {
                LOGGER.info(String.format(INTERCEPTED_COMMAND, command.getPayloadType()));

                RemovePaymentMethodCommand removePaymentMethodCommand = (RemovePaymentMethodCommand) command.getPayload();

                removePaymentMethodCommand.validate();

                PaymentMethodLookupEntity paymentMethodLookupEntity = paymentMethodLookupRepository.findByPaymentId(
                        removePaymentMethodCommand.getPaymentId());

                throwExceptionIfEntityDoesNotExist(paymentMethodLookupEntity,
                        String.format(PAYMENT_METHOD_WITH_ID_DOES_NOT_EXIST, removePaymentMethodCommand.getPaymentId()));
            }

            return command;
        };
    }
}
