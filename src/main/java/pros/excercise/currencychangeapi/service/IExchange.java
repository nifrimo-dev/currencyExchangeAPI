package pros.excercise.currencychangeapi.service;

import pros.excercise.currencychangeapi.model.entity.Exchange;

public interface IExchange {
    Exchange save(Exchange exchange);
    Exchange findById(int id);
    void delete(Exchange exchange);
}
