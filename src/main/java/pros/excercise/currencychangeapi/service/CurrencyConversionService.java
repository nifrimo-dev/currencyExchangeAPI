package pros.excercise.currencychangeapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pros.excercise.currencychangeapi.domain.Currency;
import pros.excercise.currencychangeapi.domain.ExchangeRate;
import pros.excercise.currencychangeapi.domain.ExchangeRepository;
import pros.excercise.currencychangeapi.domain.IExchangeRateRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class CurrencyConversionService {
    private final IExchangeRateRepository exchangeRateRepository;

    @Autowired
    public CurrencyConversionService(ExchangeRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public double convert(Currency sourceCurrency, Currency targetCurrency, LocalDate date, double amount) {
        List<ExchangeRate> rates = exchangeRateRepository.findExchangeRateBySourceTargetCurrencyAndEffectiveDate(sourceCurrency, targetCurrency, date);
        if (!rates.isEmpty()) {
            return amount * rates.get(0).getRate();
        } else {
            // handle the case where there is no direct exchange rate available
            // this is left as an exercise for the reader
            throw new UnsupportedOperationException("Conversion not supported");
        }
    }

}
