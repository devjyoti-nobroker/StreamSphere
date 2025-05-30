package com.nobroker.streamSphere.dtos;

import co.elastic.clients.util.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProfileResponseDTO {

    private long id;
    private String name;
    private boolean adult;
    private LocalDateTime created;

}
