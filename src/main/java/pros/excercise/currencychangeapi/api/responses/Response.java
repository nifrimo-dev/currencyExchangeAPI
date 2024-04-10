package pros.excercise.currencychangeapi.api.responses;

import pros.excercise.currencychangeapi.domain.Currency;

import java.time.LocalDate;

public record Response<T>(T data, String message) { }

