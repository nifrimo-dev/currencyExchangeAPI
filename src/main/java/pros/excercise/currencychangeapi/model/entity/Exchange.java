package pros.excercise.currencychangeapi.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "EXCHANGES")
public class Exchange implements Serializable {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "OriginCurrency", referencedColumnName = "Id")
    private Currency OriginCurrency;

    @ManyToOne
    @JoinColumn(name = "DestinyCurrency", referencedColumnName = "Id")
    private Currency DestinyCurrency;

    @Column(name = "ExchangeValue")
    private Float ExchangeValue;

    @Column(name = "ExchangeDate")
    private Date ExchangeDate;
}
