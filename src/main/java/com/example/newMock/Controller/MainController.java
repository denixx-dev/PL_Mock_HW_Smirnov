package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Random;

//@Slf4j
@RestController
public class MainController {
    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String clientId = requestDTO.getClientId();
            String rqUID = requestDTO.getRqUID();
            String account = requestDTO.getAccount();
            String currency;
            char clientIdFirstNum = clientId.toCharArray()[0];
            BigDecimal maxLimit;

            if (clientIdFirstNum == '8'){
                maxLimit = new BigDecimal("2000.00");
                currency = "US";
            }
            else if (clientIdFirstNum == '9'){
                maxLimit = new BigDecimal("1000.00");
                currency = "EU";
            }
            else {
                maxLimit = new BigDecimal("10000.00");
                currency = "RUB";
            }

            ResponseDTO responseDTO = new ResponseDTO();

//            BigDecimal max = new BigDecimal(maxLimit + ".00");
//            BigDecimal randFromDouble = new BigDecimal(Math.random());
//            BigDecimal actualRandomBalance = randFromDouble.divide(max,BigDecimal.ROUND_DOWN);


            responseDTO.setRqUID(rqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(account);
            responseDTO.setCurrency(currency);
//            responseDTO.setBalance(new BigDecimal("234234.00"));
            responseDTO.setBalance(new BigDecimal(getRandomBigDecimal(maxLimit) + ".00"));
            responseDTO.setMaxLimit(maxLimit);

            log.error("*********** RequestDTO *********** " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("*********** ResponseDTO *********** " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public static BigDecimal getRandomBigDecimal(BigDecimal limit) {
        String limitStr = limit.toPlainString();
        int scale = limitStr.length() - limitStr.indexOf('.') - 3;

        Random rand = new Random();
        long randomLong = rand.nextLong(limit.longValue());

        return BigDecimal.valueOf(randomLong, scale);
    }

}
