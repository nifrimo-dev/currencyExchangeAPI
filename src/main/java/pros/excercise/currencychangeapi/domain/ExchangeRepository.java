package pros.excercise.currencychangeapi.domain;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ExchangeRepository implements IExchangeRateRepository{

    private List<ExchangeRate> exchangeRates = new ArrayList<ExchangeRate>();

    @Override
    public List<ExchangeRate> findExchangeRateBySourceTargetCurrencyAndEffectiveDate(Currency sourceCurrency, Currency targetCurrency, LocalDate effectiveDate)
    {
        return exchangeRates.stream()
                .filter(rate -> rate.getSourceCurrency() == sourceCurrency && rate.getTargetCurrency() == targetCurrency && !rate.getEffectiveDate().isAfter(effectiveDate))
                .sorted(Comparator.comparing(ExchangeRate::getEffectiveDate).reversed())
                .collect(Collectors.toList());
    }

    public void addExchangeRate(ExchangeRate exchangeRate) {
        exchangeRates.add(exchangeRate);
    }
}
