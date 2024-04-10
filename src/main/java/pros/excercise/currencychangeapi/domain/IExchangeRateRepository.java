package pros.excercise.currencychangeapi.domain;

import pros.excercise.currencychangeapi.api.ExchangeResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IExchangeRateRepository {
    Optional<ExchangeRate> findExchangeRateBySourceTargetCurrencyAndEffectiveDate(Currency sourceCurrency, Currency targetCurrency, LocalDate effectiveDate);
    Optional<ExchangeRate> findAuxSourceCurrencyByTargetCurrencyAndEffectiveDate(Currency sourceCurrency, Currency targetCurrency, LocalDate effectiveDate, double amount);
}
