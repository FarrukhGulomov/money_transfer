package dev.farruh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private String cardNumber;
    private String cardExpDate;
    private String cardCode;
    private Integer balance;
    private Integer deposit;

}
