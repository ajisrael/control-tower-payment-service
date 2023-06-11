package control.tower.payment.service.core.constants;

import static control.tower.core.constants.ExceptionMessages.*;
import static control.tower.payment.service.core.constants.DomainConstants.PAYMENT_METHOD;

public class ExceptionMessages {

    private ExceptionMessages() {
        throw new IllegalStateException("Constants class");
    }

    public static final String PAYMENT_ID_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "paymentId");
    public static final String USER_ID_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "userId");
    public static final String CARD_NUMBER_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "cardNumber");
    public static final String EXPIRATION_DATE_CANNOT_BE_NULL = String.format(PARAMETER_CANNOT_BE_NULL, "expirationDate");
    public static final String SECURITY_CODE_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "securityCode");

    public static final String PAYMENT_METHOD_WITH_ID_ALREADY_EXISTS = String.format(ENTITY_WITH_ID_ALREADY_EXISTS, PAYMENT_METHOD, "%s");
    public static final String PAYMENT_METHOD_WITH_ID_DOES_NOT_EXIST = String.format(ENTITY_WITH_ID_DOES_NOT_EXIST, PAYMENT_METHOD, "%s");
    public static final String PAYMENT_METHOD_ALREADY_EXISTS_FOR_USER = String.format(ENTITY_ALREADY_EXISTS_FOR_USER, PAYMENT_METHOD);

}
