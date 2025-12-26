package org.example.mock.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class RequestDTO {

    private String rqUID;
    private String clientId;
    private String account;
    private String openDate;
    private String closeDate;

}