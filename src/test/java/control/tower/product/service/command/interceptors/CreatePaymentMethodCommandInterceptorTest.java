package control.tower.product.service.command.interceptors;

import control.tower.product.service.command.CreatePaymentMethodCommand;
import control.tower.product.service.core.data.PaymentMethodLookupEntity;
import control.tower.product.service.core.data.PaymentMethodLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class CreatePaymentMethodCommandInterceptorTest {

    private CreatePaymentMethodCommandInterceptor interceptor;
    private PaymentMethodLookupRepository lookupRepository;

    @BeforeEach
    void setUp() {
        lookupRepository = mock(PaymentMethodLookupRepository.class);
        interceptor = new CreatePaymentMethodCommandInterceptor(lookupRepository);
    }

    @Test
    void testHandle_ValidCommand() throws ParseException {
        String dateString = "02/2023";
        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
        Date date = format.parse(dateString);

        CreatePaymentMethodCommand validCommand = CreatePaymentMethodCommand.builder()
                .paymentId("paymentId")
                .userId("userId")
                .cardNumber("cardNumber")
                .expirationDate(date)
                .securityCode("securityCode")
                .build();

        CommandMessage<CreatePaymentMethodCommand> commandMessage = new GenericCommandMessage<>(validCommand);

        BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> result = interceptor.handle(List.of(commandMessage));

        CommandMessage<?> processedCommand = result.apply(0, commandMessage);

        assertEquals(commandMessage, processedCommand);
    }

    @Test
    void testHandle_DuplicateProductId_ThrowsException() throws ParseException {
        String dateString = "02/2023";
        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
        Date date = format.parse(dateString);

        String paymentId = "paymentId";
        String cardNumber = "cardNumber";
        CreatePaymentMethodCommand duplicateCommand = CreatePaymentMethodCommand.builder()
                .paymentId(paymentId)
                .userId("userId")
                .cardNumber(cardNumber)
                .expirationDate(date)
                .securityCode("securityCode")
                .build();

        CommandMessage<CreatePaymentMethodCommand> commandMessage = new GenericCommandMessage<>(duplicateCommand);

        PaymentMethodLookupEntity existingEntity = new PaymentMethodLookupEntity(paymentId, cardNumber);
        when(lookupRepository.findByPaymentIdOrCardNumber(paymentId, cardNumber)).thenReturn(existingEntity);

        BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> result = interceptor.handle(List.of(commandMessage));

        assertThrows(IllegalStateException.class, () -> result.apply(0, commandMessage));

        verify(lookupRepository).findByPaymentIdOrCardNumber(paymentId, cardNumber);
    }
}
