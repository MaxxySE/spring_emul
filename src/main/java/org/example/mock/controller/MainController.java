package org.example.mock.controller;

import org.example.mock.model.RequestDTO;
import org.example.mock.model.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);

    private ObjectMapper mapper = new ObjectMapper();

    private Random random = new Random();

    private BigDecimal getBalance(double maxLimit){
        double randomD = maxLimit * ThreadLocalRandom.current().nextDouble();

        return new BigDecimal(randomD).setScale(2, RoundingMode.HALF_DOWN);
    }

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO) {

        try {

            long sleeptime = random.nextLong(1000, 2000 + 1);
            log.warn("Starting sleep for: " + (double)sleeptime/1000d + "s");

            // I'm not using here created getBalance function cause BigDecimal
            // variable is used for high precision numbers but here in sleep method I can use simpler type as long

            Thread.sleep(sleeptime);

            log.warn("Stopping sleep");

            String clientId = requestDTO.getClientId();

            BigDecimal maxLimit;
            char firstDigit = clientId.charAt(0);

            String rqUID = requestDTO.getRqUID();
            String currency;
            BigDecimal balance;

            switch (firstDigit) {
                case '8':
                    maxLimit = new BigDecimal(2000);
                    currency = "US";
                    break;
                case '9':
                    maxLimit = new BigDecimal(1000);
                    currency = "EU";
                    break;
                default:
                    maxLimit = new BigDecimal(10000);
                    currency = "RUB";
                    break;
            }

//            ResponseDTO responseDTO = new ResponseDTO();

//            responseDTO.setRqUID(rqUID);
//            responseDTO.setClientId(clientId);
//            responseDTO.setAccount(requestDTO.getAccount()); // kak variant
//            responseDTO.setCurrency("US");
//            responseDTO.setBalance(new BigDecimal(777));
//            responseDTO.setMaxLimit(maxLimit);

            // один из двух вариантов

            balance = getBalance(maxLimit.doubleValue());

            ResponseDTO responseDTO = new ResponseDTO(
                    rqUID,
                    clientId,
                    requestDTO.getAccount(),
                    currency,
                    balance,
                    maxLimit
            );

            log.info("********** RequestDTO **********: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.info("********** ResponseDTO **********: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}

