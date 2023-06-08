package control.tower.payment.service.core.events;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentMethodRemovedEvent {

    private String paymentId;
}
