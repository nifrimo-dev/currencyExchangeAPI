package pros.excercise.currencychangeapi.domain;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ExchangeRepository implements IExchangeRateRepository{

    private final List<ExchangeRate> exchangeRates = new ArrayList<>();

    @PostConstruct
    public void init()
    {
        ExchangeRate USD_EUR = new ExchangeRate();
        USD_EUR.setSourceCurrency(Currency.USD);
        USD_EUR.setTargetCurrency(Currency.EUR);
        USD_EUR.setEffectiveDate(LocalDate.of(2024,4,7));
        USD_EUR.setRate(0.9233);
        exchangeRates.add(USD_EUR);

        ExchangeRate EUR_KRW = new ExchangeRate();
        EUR_KRW.setSourceCurrency(Currency.EUR);
        EUR_KRW.setTargetCurrency(Currency.KRW);
        EUR_KRW.setEffectiveDate(LocalDate.of(2024,4,7));
        EUR_KRW.setRate(1450.4942);
        exchangeRates.add(EUR_KRW);

        ExchangeRate THB_KRW = new ExchangeRate();
        THB_KRW.setSourceCurrency(Currency.THB);
        THB_KRW.setTargetCurrency(Currency.KRW);
        THB_KRW.setEffectiveDate(LocalDate.of(2024,4,7));
        THB_KRW.setRate(37.29);
        exchangeRates.add(THB_KRW);

    }

    @Override
    public Optional<ExchangeRate> findExchangeRateBySourceTargetCurrencyAndEffectiveDate(Currency sourceCurrency, Currency targetCurrency, LocalDate effectiveDate)
    {
        return exchangeRates.stream()
                .filter(rate -> rate.getTargetCurrency() == targetCurrency
                        && rate.getSourceCurrency() == sourceCurrency
                        && rate.getEffectiveDate().isEqual(effectiveDate))
                .findFirst();
    }

    public Optional<ExchangeRate> findAuxSourceCurrencyByTargetCurrencyAndEffectiveDate(Currency sourceCurrency, Currency targetCurrency, LocalDate effectiveDate)
    {
        return exchangeRates.stream()
                .filter(rate -> rate.getTargetCurrency() == targetCurrency
                        && rate.getSourceCurrency() == sourceCurrency
                        && rate.getEffectiveDate().isEqual(effectiveDate))
                .findFirst();
    }
}
