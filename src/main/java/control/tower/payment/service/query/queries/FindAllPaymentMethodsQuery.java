package control.tower.payment.service.query.queries;

import control.tower.core.query.queries.PageableQuery;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FindAllPaymentMethodsQuery extends PageableQuery {
}
