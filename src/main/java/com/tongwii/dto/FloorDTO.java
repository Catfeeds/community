package com.tongwii.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the Floor entity.
 */
@Data
public class FloorDTO implements Serializable {

    private String id;

    private String code;

    private Integer floorNumber;

    private Boolean hasElev;

    private String residenceId;

    private String principalId;
}
