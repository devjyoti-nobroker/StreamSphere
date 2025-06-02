package com.nobroker.streamSphere.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    private long id;
    private String name;
    private boolean adult;

}
