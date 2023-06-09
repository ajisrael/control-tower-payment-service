package control.tower.payment.service.query.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindPaymentMethodQuery {

    private String paymentId;
}
