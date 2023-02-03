package org.example.domain;

import lombok.*;

import java.util.Date;
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Flight {
    private String departureCity;

    private Date departureTime;

}
