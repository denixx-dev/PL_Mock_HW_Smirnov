package com.example.newMock.Model;

import lombok.*;

import java.math.BigDecimal;

@Data
@ToString
public class ResponseDTO {
    private String rqUID;
    private String clientId;
    private String account;
    private String currency;
    private BigDecimal balance;
    private BigDecimal maxLimit;
}
