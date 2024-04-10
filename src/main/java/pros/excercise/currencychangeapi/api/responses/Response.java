package pros.excercise.currencychangeapi.api.responses;

import java.util.UUID;

public record Response<T>(T data, String message) { }