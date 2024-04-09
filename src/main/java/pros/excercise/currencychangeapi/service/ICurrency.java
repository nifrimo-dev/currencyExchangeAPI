package pros.excercise.currencychangeapi.service;

import pros.excercise.currencychangeapi.model.entity.Currency;

public interface ICurrency {
    Currency save(Currency currency);
    Currency findById(int id);
    void delete(Currency currency);
}
