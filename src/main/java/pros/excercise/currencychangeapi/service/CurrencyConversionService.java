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
        Response<Optional<ExchangeRate>> rate, auxSourceCurrency;
        LocalDate finalDate;

        do{
            rate = exchangeRateRepository.findExchangeRateBySourceTargetCurrencyAndEffectiveDate(sourceCurrency, targetCurrency, date);
            date = rate.message().equals(MessagesResponse.INVALID_DATE.getValue()) ? date.minusDays(1) : date;

        }while (rate.message().equals(MessagesResponse.INVALID_DATE.getValue()));

        finalDate = date;

        if (rate.message().equals(MessagesResponse.EXCHANGE_FOUND.getValue())) {

            return rate.data().map(r -> {
                double rawResult = amount * r.getRate();
                return Math.round(rawResult * 100.0) / 100.0;

            }).orElse(0.0);

        } else if (rate.message().equals(MessagesResponse.EXCHANGE_NOT_FOUND.getValue())) {

            do{
                auxSourceCurrency = exchangeRateRepository.findAuxSourceCurrencyByTargetCurrencyAndEffectiveDate(sourceCurrency, targetCurrency, finalDate, amount);
                finalDate = auxSourceCurrency.message().equals(MessagesResponse.INVALID_DATE.getValue()) ? finalDate.minusDays(1) : finalDate;
            } while (auxSourceCurrency.message().equals(MessagesResponse.INVALID_DATE.getValue()));

            LocalDate finalDate1 = finalDate;

            if (auxSourceCurrency.message().equals(MessagesResponse.EXCHANGE_FOUND.getValue())) {
                return auxSourceCurrency.data().flatMap(auxSourceRate ->
                            exchangeRateRepository.findExchangeRateBySourceTargetCurrencyAndEffectiveDate(auxSourceRate.getTargetCurrency(), targetCurrency, finalDate1)
                                    .data().map(targetSourceRate -> {
                                        double rawResult = amount * auxSourceRate.getRate() * targetSourceRate.getRate();
                                        return Math.round(rawResult * 100.0) / 100.0;
                                    }))
                    .orElse(0.0);
            }
        }
        return 0;
    }

}
