package pros.excercise.currencychangeapi.api.responses;

import pros.excercise.currencychangeapi.domain.Currency;

public record ExchangeResponse(
        Currency sourceCurrency,
        double sourceAmount,
        Currency targetCurrency,
        double targetAmount
) { }
