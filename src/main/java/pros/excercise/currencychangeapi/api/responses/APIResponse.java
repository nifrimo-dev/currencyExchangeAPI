package pros.excercise.currencychangeapi.api.responses;

import java.util.UUID;

public record APIResponse<T>(UUID id, String type, T data, String message) { }
