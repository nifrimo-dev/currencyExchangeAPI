package pros.excercise.currencychangeapi.model.dao;

import org.springframework.data.repository.CrudRepository;
import pros.excercise.currencychangeapi.model.entity.Exchange;

public interface ExchangeDAO extends CrudRepository<Exchange, Integer> {
}
