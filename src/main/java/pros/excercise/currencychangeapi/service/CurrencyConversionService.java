package pros.excercise.currencychangeapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pros.excercise.currencychangeapi.api.ExchangeResult;
import pros.excercise.currencychangeapi.domain.Currency;
import pros.excercise.currencychangeapi.domain.ExchangeRate;
import pros.excercise.currencychangeapi.domain.ExchangeRepository;
import pros.excercise.currencychangeapi.domain.IExchangeRateRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyConversionService {
    private final IExchangeRateRepository exchangeRateRepository;

    @Autowired
    public CurrencyConversionService(ExchangeRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public double convert(Currency sourceCurrency, Currency targetCurrency, LocalDate date, double amount) {
        Optional<ExchangeRate> rate = exchangeRateRepository.findExchangeRateBySourceTargetCurrencyAndEffectiveDate(sourceCurrency, targetCurrency, date);

        return rate.map(r -> {
                    double rawResult = amount * r.getRate();
                    return Math.round(rawResult * 100.0) / 100.0;
                })
                .orElseGet(() -> {
                    Optional<ExchangeRate> auxSourceCurrency = exchangeRateRepository.findAuxSourceCurrencyByTargetCurrencyAndEffectiveDate(sourceCurrency, targetCurrency, date, amount);

                    return auxSourceCurrency.flatMap(auxSourceRate ->
                                    exchangeRateRepository.findExchangeRateBySourceTargetCurrencyAndEffectiveDate(auxSourceRate.getTargetCurrency(), targetCurrency, date)
                                            .map(targetSourceRate -> {
                                                double rawResult = amount * auxSourceRate.getRate() * targetSourceRate.getRate();
                                                return Math.round(rawResult * 100.0) / 100.0;
                                            }))
                            .orElse(0.0);
                });
    }

}
