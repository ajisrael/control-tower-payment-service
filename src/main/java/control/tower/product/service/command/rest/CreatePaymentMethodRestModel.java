package control.tower.product.service.command.rest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.databind.DeserializationContext;

@Getter
@NoArgsConstructor
public class CreatePaymentMethodRestModel {

    @NotBlank(message = "User id is a required field")
    private String userId;
    @NotBlank(message = "Card number is a required field")
    private String cardNumber;
    @NotNull(message = "Expiration date is a required field")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date expirationDate;
    @NotBlank(message = "Security code is a required field")
    private String securityCode;

    public static class CustomDateDeserializer extends JsonDeserializer<Date> {
        private static final FormattingConversionService conversionService;

        static {
            DateFormatterRegistrar registrar = new DateFormatterRegistrar();
            registrar.setFormatter(new DateFormatter("MM/yy"));
            conversionService = new DefaultFormattingConversionService();
            registrar.registerFormatters(conversionService);
        }

        @Override
        public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String dateStr = jsonParser.getValueAsString();
            return conversionService.convert(dateStr, Date.class);
        }
    }
}
