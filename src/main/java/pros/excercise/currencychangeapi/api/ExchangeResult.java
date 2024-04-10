package pros.excercise.currencychangeapi.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pros.excercise.currencychangeapi.domain.ExchangeRate;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExchangeResult {
    private Optional<ExchangeRate> exchangeRate;
    private double rate;
}
