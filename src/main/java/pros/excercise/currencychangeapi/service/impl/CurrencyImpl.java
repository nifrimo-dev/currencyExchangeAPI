package pros.excercise.currencychangeapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pros.excercise.currencychangeapi.model.dao.CurrencyDAO;
import pros.excercise.currencychangeapi.model.entity.Currency;
import pros.excercise.currencychangeapi.service.ICurrency;

@Service
public class CurrencyImpl implements ICurrency {

    @Autowired
    private CurrencyDAO currencyDAO;

    @Transactional
    @Override
    public Currency save(Currency currency) {
        return currencyDAO.save(currency);
    }

    @Transactional(readOnly = true)
    @Override
    public Currency findById(int id) {
        return currencyDAO.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Currency currency) {
        currencyDAO.delete(currency);
    }
}
