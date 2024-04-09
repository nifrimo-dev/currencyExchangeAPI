package pros.excercise.currencychangeapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExchangeRate {
    private Currency sourceCurrency;
    private Currency targetCurrency;
    private LocalDate effectiveDate;
    private double rate;
}
