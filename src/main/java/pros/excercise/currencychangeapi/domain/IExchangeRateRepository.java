package pros.excercise.currencychangeapi.domain;

import java.time.LocalDate;
import java.util.List;

public interface IExchangeRateRepository {
    List<ExchangeRate> findExchangeRateBySourceTargetCurrencyAndEffectiveDate(Currency sourceCurrency, Currency targetCurrency, LocalDate effectiveDate);

}
