package org.medhead.emergencysystem.webclientoperators.model;

import lombok.Data;

@Data
public class Incident {

    private Integer id;

    private String type;

    private String specialityNeeded;

    private String longitude;

    private String latitude;

    private String traitement;
}
