package pros.excercise.currencychangeapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pros.excercise.currencychangeapi.api.enums.MessagesResponse;
import pros.excercise.currencychangeapi.api.responses.Response;
import pros.excercise.currencychangeapi.domain.Currency;
import pros.excercise.currencychangeapi.domain.ExchangeRate;
import pros.excercise.currencychangeapi.domain.ExchangeRepository;
import pros.excercise.currencychangeapi.domain.IExchangeRateRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CurrencyConversionService {
    private final IExchangeRateRepository exchangeRateRepository;

    @Autowired
    public CurrencyConversionService(ExchangeRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public double convert(Currency sourceCurrency, Currency targetCurrency, LocalDate date, double amount) {
        LocalDate finalDate = findValidDate(sourceCurrency, targetCurrency, date);
        Response<Optional<ExchangeRate>> rate = exchangeRateRepository.findExchangeRateBySourceTargetCurrencyAndEffectiveDate(sourceCurrency, targetCurrency, finalDate);

        if (rate.message().equals(MessagesResponse.EXCHANGE_FOUND.getValue())) {
            return calculateExchange(rate, amount);
        } else if (rate.message().equals(MessagesResponse.EXCHANGE_NOT_FOUND.getValue())) {
            return calculateAuxiliaryExchange(sourceCurrency, targetCurrency, finalDate, amount);
        }
        return 0;
    }

    private LocalDate findValidDate(Currency sourceCurrency, Currency targetCurrency, LocalDate date) {
        Response<Optional<ExchangeRate>> rate;
        do {
            rate = exchangeRateRepository.findExchangeRateBySourceTargetCurrencyAndEffectiveDate(sourceCurrency, targetCurrency, date);
            if (rate.message().equals(MessagesResponse.INVALID_DATE.getValue())) {
                date = date.minusDays(1);
            }
        } while (rate.message().equals(MessagesResponse.INVALID_DATE.getValue()));
        return date;
    }

    private double calculateExchange(Response<Optional<ExchangeRate>> rate, double amount) {
        return rate.data().map(r -> {
            double rawResult = amount * r.getRate();
            return Math.round(rawResult * 100.0) / 100.0;
        }).orElse(0.0);
    }

    private double calculateAuxiliaryExchange(Currency sourceCurrency, Currency targetCurrency, LocalDate finalDate, double amount) {
        Response<Optional<ExchangeRate>> auxSourceCurrency;
        do {
            auxSourceCurrency = exchangeRateRepository.findAuxSourceCurrencyByTargetCurrencyAndEffectiveDate(sourceCurrency, targetCurrency, finalDate, amount);
            if (auxSourceCurrency.message().equals(MessagesResponse.INVALID_DATE.getValue())) {
                finalDate = finalDate.minusDays(1);
            }
        } while (auxSourceCurrency.message().equals(MessagesResponse.INVALID_DATE.getValue()));

        if (auxSourceCurrency.message().equals(MessagesResponse.EXCHANGE_FOUND.getValue())) {
            LocalDate finalDate1 = finalDate;
            return auxSourceCurrency.data().flatMap(auxSourceRate ->
                            exchangeRateRepository.findExchangeRateBySourceTargetCurrencyAndEffectiveDate(auxSourceRate.getTargetCurrency(), targetCurrency, finalDate1)
                                    .data().map(targetSourceRate -> {
                                        double rawResult = amount * auxSourceRate.getRate() * targetSourceRate.getRate();
                                        return Math.round(rawResult * 100.0) / 100.0;
                                    }))
                    .orElse(0.0);
        }
        return 0;
    }
}
