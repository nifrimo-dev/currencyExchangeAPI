package pros.excercise.currencychangeapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pros.excercise.currencychangeapi.api.responses.ExchangeResponse;
import pros.excercise.currencychangeapi.api.responses.Response;
import pros.excercise.currencychangeapi.domain.Currency;
import pros.excercise.currencychangeapi.service.CurrencyConversionService;

import java.time.LocalDate;

@RestController
public class CurrencyConversionController {

    private final CurrencyConversionService currencyConversionService;

    @Autowired
    public CurrencyConversionController(CurrencyConversionService currencyConversionService) {
        this.currencyConversionService = currencyConversionService;
    }

    @GetMapping("/convert")
    public Response<ExchangeResponse> convert(@RequestParam Currency sourceCurrency, @RequestParam Currency targetCurrency, @RequestParam LocalDate date, @RequestParam double amount) {
        return currencyConversionService.convert(sourceCurrency, targetCurrency, date, amount);
    }
}
