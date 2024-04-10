package pros.excercise.currencychangeapi.domain;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import pros.excercise.currencychangeapi.api.enums.MessagesResponse;
import pros.excercise.currencychangeapi.api.responses.Response;

import java.time.LocalDate;
import java.util.*;

@Repository
public class ExchangeRepository implements IExchangeRateRepository{

    private final List<ExchangeRate> exchangeRates = new ArrayList<>();

    @PostConstruct
    public void init()
    {
        ExchangeRate USD_EUR_DATE1 = new ExchangeRate();
        USD_EUR_DATE1.setSourceCurrency(Currency.USD);
        USD_EUR_DATE1.setTargetCurrency(Currency.EUR);
        USD_EUR_DATE1.setEffectiveDate(LocalDate.of(2024,4,7));
        USD_EUR_DATE1.setRate(0.92);
        exchangeRates.add(USD_EUR_DATE1);

        ExchangeRate EUR_KRW_DATE1 = new ExchangeRate();
        EUR_KRW_DATE1.setSourceCurrency(Currency.EUR);
        EUR_KRW_DATE1.setTargetCurrency(Currency.KRW);
        EUR_KRW_DATE1.setEffectiveDate(LocalDate.of(2024,4,7));
        EUR_KRW_DATE1.setRate(1463.45);
        exchangeRates.add(EUR_KRW_DATE1);

        ExchangeRate THB_KRW_DATE1 = new ExchangeRate();
        THB_KRW_DATE1.setSourceCurrency(Currency.THB);
        THB_KRW_DATE1.setTargetCurrency(Currency.KRW);
        THB_KRW_DATE1.setEffectiveDate(LocalDate.of(2024,4,7));
        THB_KRW_DATE1.setRate(37.14);
        exchangeRates.add(THB_KRW_DATE1);

        ExchangeRate USD_EUR_DATE2 = new ExchangeRate();
        USD_EUR_DATE2.setSourceCurrency(Currency.USD);
        USD_EUR_DATE2.setTargetCurrency(Currency.EUR);
        USD_EUR_DATE2.setEffectiveDate(LocalDate.of(2024,4,10));
        USD_EUR_DATE2.setRate(0.98);
        exchangeRates.add(USD_EUR_DATE2);

        ExchangeRate EUR_KRW_DATE2 = new ExchangeRate();
        EUR_KRW_DATE2.setSourceCurrency(Currency.EUR);
        EUR_KRW_DATE2.setTargetCurrency(Currency.KRW);
        EUR_KRW_DATE2.setEffectiveDate(LocalDate.of(2024,4,10));
        EUR_KRW_DATE2.setRate(1465.45);
        exchangeRates.add(EUR_KRW_DATE2);

        ExchangeRate THB_KRW_DATE2 = new ExchangeRate();
        THB_KRW_DATE2.setSourceCurrency(Currency.THB);
        THB_KRW_DATE2.setTargetCurrency(Currency.KRW);
        THB_KRW_DATE2.setEffectiveDate(LocalDate.of(2024,4,10));
        THB_KRW_DATE2.setRate(38.14);
        exchangeRates.add(THB_KRW_DATE2);

    }

    @Override
    public Response<Optional<ExchangeRate>> findExchangeRateBySourceTargetCurrencyAndEffectiveDate(Currency sourceCurrency, Currency targetCurrency, LocalDate effectiveDate)
    {
        var directExchangeRate = exchangeRates.stream()
                .filter(rate -> rate.getTargetCurrency() == targetCurrency
                        && rate.getSourceCurrency() == sourceCurrency)
                .toList();

        if(!directExchangeRate.isEmpty()){
            var validDateExchangeRate = directExchangeRate.stream()
                    .filter(rate -> rate.getEffectiveDate().isEqual(effectiveDate))
                    .findFirst();

            if(validDateExchangeRate.isPresent()){
                return new Response<>(validDateExchangeRate, MessagesResponse.EXCHANGE_FOUND.getValue());
            }else{
                return new Response<>(Optional.empty(), MessagesResponse.INVALID_DATE.getValue());
            }
        }

        return new Response<>(Optional.empty(), MessagesResponse.EXCHANGE_NOT_FOUND.getValue());
    }

    public Response<Optional<ExchangeRate>> findAuxSourceCurrencyByTargetCurrencyAndEffectiveDate(Currency sourceCurrency, Currency targetCurrency, LocalDate effectiveDate, double amount)
    {
        var sourceCurrencyList = exchangeRates.stream()
                .filter(rate -> rate.getTargetCurrency() == targetCurrency)
                .toList();

        if(!sourceCurrencyList.isEmpty()){
            var sourceCurrencyItem =sourceCurrencyList.stream()
                    .filter(rate -> rate.getEffectiveDate().isEqual(effectiveDate))
                    .findFirst();

            if(sourceCurrencyItem.isPresent()){
                var auxExchangeRate = sourceCurrencyItem.stream()
                        .flatMap(rate -> findExchangeRateBySourceTargetCurrencyAndEffectiveDate(sourceCurrency, rate.getSourceCurrency(), effectiveDate).data().stream())
                        .findFirst();

                if(auxExchangeRate.isPresent())
                    return new Response<>(auxExchangeRate, MessagesResponse.EXCHANGE_FOUND.getValue());

            }else{
                return new Response<>(Optional.empty(), MessagesResponse.INVALID_DATE.getValue());
            }

        }
        return new Response<>(Optional.empty(), MessagesResponse.EXCHANGE_NOT_FOUND.getValue());
    }
}
