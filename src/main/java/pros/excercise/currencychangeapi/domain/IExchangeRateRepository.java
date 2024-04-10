package pros.excercise.currencychangeapi.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IExchangeRateRepository {
    Optional<ExchangeRate> findExchangeRateBySourceTargetCurrencyAndEffectiveDate(Currency sourceCurrency, Currency targetCurrency, LocalDate effectiveDate);

}
