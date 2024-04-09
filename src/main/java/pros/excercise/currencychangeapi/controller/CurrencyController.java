package pros.excercise.currencychangeapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pros.excercise.currencychangeapi.model.entity.Currency;
import pros.excercise.currencychangeapi.service.ICurrency;

@RestController
@RequestMapping("/api/v1")
public class CurrencyController {

    @Autowired
    private ICurrency currencyService;

    @PostMapping("currency")
    public Currency createCurrency(@RequestBody Currency currency) {
        return currencyService.save(currency);
    }

    @PutMapping("currency")
    public Currency updateCurrency(@RequestBody Currency currency) {
        return currencyService.save(currency);
    }

    @DeleteMapping("currency/{id}")
    public void deleteCurrency(@PathVariable Integer id) {
        Currency currencyToDelete = currencyService.findById((id));
        currencyService.delete(currencyToDelete);
    }

    @GetMapping("currency/{id}")
    public Currency showCurrencyById(@PathVariable Integer id) {
        return currencyService.findById(id);
    }
}
