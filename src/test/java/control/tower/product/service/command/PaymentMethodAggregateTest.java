package control.tower.product.service.command;

import control.tower.product.service.core.events.PaymentMethodCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentMethodAggregateTest {

    private final String PAYMENT_ID = "paymentId";
    private final String USER_ID = "userId";
    private final String CARD_NUMBER = "cardNumber";
    private Date EXPIRATION_DATE = null;
    private final String SECURITY_CODE = "securityCode";

    private FixtureConfiguration<PaymentMethodAggregate> fixture;

    @BeforeEach
    void setup() throws ParseException {
        fixture = new AggregateTestFixture<>(PaymentMethodAggregate.class);

        String dateString = "02/2023";
        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
        EXPIRATION_DATE = format.parse(dateString);
    }

    @Test
    void shouldCreateProductAggregate() {
        fixture.givenNoPriorActivity()
                .when(
                        CreatePaymentMethodCommand.builder()
                                .paymentId(PAYMENT_ID)
                                .userId(USER_ID)
                                .cardNumber(CARD_NUMBER)
                                .expirationDate(EXPIRATION_DATE)
                                .securityCode(SECURITY_CODE)
                                .build())
                .expectEvents(
                        PaymentMethodCreatedEvent.builder()
                                .paymentId(PAYMENT_ID)
                                .userId(USER_ID)
                                .cardNumber(CARD_NUMBER)
                                .expirationDate(EXPIRATION_DATE)
                                .securityCode(SECURITY_CODE)
                                .build())
                .expectState(
                        paymentMethodAggregate -> {
                            assertEquals(PAYMENT_ID, paymentMethodAggregate.getPaymentId());
                            assertEquals(USER_ID, paymentMethodAggregate.getUserId());
                            assertEquals(EXPIRATION_DATE, paymentMethodAggregate.getExpirationDate());
                        }
                );
    }
}
