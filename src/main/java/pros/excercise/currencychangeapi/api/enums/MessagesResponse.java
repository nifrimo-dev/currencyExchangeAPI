package pros.excercise.currencychangeapi.api.enums;

import lombok.Getter;

@Getter
public enum MessagesResponse {
    EXCHANGE_FOUND("Exchange Found"),
    INVALID_DATE("Invalid Date"),
    EXCHANGE_NOT_FOUND("Direct Exchange Not Found");

    private final String value;

    MessagesResponse(String value) {
        this.value = value;
    }

}
