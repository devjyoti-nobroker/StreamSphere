package com.nobroker.streamSphere.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProfileDTO {

    private String name;
    private boolean adult;

}
