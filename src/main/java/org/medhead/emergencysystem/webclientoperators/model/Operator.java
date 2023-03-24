package org.medhead.emergencysystem.webclientoperators.model;

import lombok.Data;

@Data
public class Operator {
    private Integer id;

    private String name;

    private String available;

    private String incidentId;

    private String hospitalId;
}
