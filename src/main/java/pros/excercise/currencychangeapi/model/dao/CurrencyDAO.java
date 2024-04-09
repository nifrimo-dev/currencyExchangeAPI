package pros.excercise.currencychangeapi.model.dao;

import org.springframework.data.repository.CrudRepository;
import pros.excercise.currencychangeapi.model.entity.Currency;

public interface CurrencyDAO extends CrudRepository<Currency, Integer> {
}
