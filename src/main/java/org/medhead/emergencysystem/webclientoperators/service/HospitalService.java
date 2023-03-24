package org.medhead.emergencysystem.webclientoperators.service;

import org.medhead.emergencysystem.webclientoperators.model.Incident;
import org.medhead.emergencysystem.webclientoperators.model.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.medhead.emergencysystem.webclientoperators.repository.HospitalProxy;
import org.medhead.emergencysystem.webclientoperators.model.Hospital;

import lombok.Data;

@Data
@Service
public class HospitalService {

    @Autowired
    private HospitalProxy hospitalProxy;

    public Hospital getHospital(final int id) { return  hospitalProxy.getHospital(id); }

    public Iterable<Hospital> getHospitals() { return hospitalProxy.getHospitals(); }

    public void deleteHospital(final int id) { hospitalProxy.deleteHospital(id); }

    public Hospital saveHospital(Hospital hospital) {
        Hospital savedHospital;

        // Functional rule : name must be capitalized.
        hospital.setName(hospital.getName().toUpperCase());

        if(hospital.getId() == null) {
            //if id is null then it's a new hospital
            savedHospital = hospitalProxy.createHospital(hospital);
        } else {
            savedHospital = hospitalProxy.updateHospital(hospital);
        }

        return savedHospital;
    }

    public Hospital lookForHospital(Incident incident) {
        System.out.println("=================================================\n\tL'incident '" + incident.getId() + "' nécessite des soins de '" + incident.getSpecialityNeeded() + "'");
        Iterable<Hospital> hospitals = hospitalProxy.getHospitals();
        for (Hospital hospital : hospitals) {
            if (hospital.getSpeciality().contains(incident.getSpecialityNeeded())) {
                System.out.println("\tL'hopital choisi est '" + hospital.getId()+ "' car il dispose d'une spécialité médicale de '" + hospital.getSpeciality() + "'");
                return hospital;
            }
        }
        return null;
    }

    public Hospital attributedIncident(Hospital hospital, Incident incident) {
        System.out.println("\tL'incident '" + incident.getId() + "' est attribué à '" + hospital.getName() + "'");
        hospital.setIncidentId(incident.getId().toString());
        this.saveHospital(hospital);
        return hospital;
    }

    public Hospital attributedOperator(Hospital hospital, Operator operator) {
        System.out.println("\tL'incident '" + operator.getIncidentId() + "' a été traité par '" + operator.getName() + "'");
        hospital.setAttributedBy(operator.getId().toString());
        this.saveHospital(hospital);
        return hospital;
    }
}
