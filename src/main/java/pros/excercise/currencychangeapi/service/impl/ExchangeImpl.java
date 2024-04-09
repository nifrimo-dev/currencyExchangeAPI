package pros.excercise.currencychangeapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pros.excercise.currencychangeapi.model.dao.ExchangeDAO;
import pros.excercise.currencychangeapi.model.entity.Exchange;
import pros.excercise.currencychangeapi.service.IExchange;

@Service
public class ExchangeImpl implements IExchange {

    @Autowired
    private ExchangeDAO exchangeDAO;

    @Transactional
    @Override
    public Exchange save(Exchange exchange) {
        return exchangeDAO.save(exchange);
    }

    @Transactional(readOnly = true)
    @Override
    public Exchange findById(int id) {
        return exchangeDAO.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Exchange exchange) {
        exchangeDAO.delete(exchange);
    }
}
