package pros.excercise.currencychangeapi.domain;

import pros.excercise.currencychangeapi.api.responses.Response;

import java.time.LocalDate;
import java.util.Optional;

public interface IExchangeRateRepository {
    Response<Optional<ExchangeRate>> findExchangeRateBySourceTargetCurrencyAndEffectiveDate(Currency sourceCurrency, Currency targetCurrency, LocalDate effectiveDate);
    Response<Optional<ExchangeRate>> findAuxSourceCurrencyByTargetCurrencyAndEffectiveDate(Currency sourceCurrency, Currency targetCurrency, LocalDate effectiveDate, double amount);
}
