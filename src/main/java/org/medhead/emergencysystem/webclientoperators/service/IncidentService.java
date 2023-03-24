package org.medhead.emergencysystem.webclientoperators.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.medhead.emergencysystem.webclientoperators.repository.IncidentProxy;
import org.medhead.emergencysystem.webclientoperators.model.Incident;

import lombok.Data;

@Data
@Service
public class IncidentService {

    @Autowired
    private IncidentProxy incidentProxy;

    public Incident getIncident(final int id) { return  incidentProxy.getIncident(id); }

    public Iterable<Incident> getIncidents() { return incidentProxy.getIncidents(); }

    public void deleteIncident(final int id) { incidentProxy.deleteIncident(id); }

    public Incident saveIncident(Incident incident) {
        Incident savedIncident;
        savedIncident = incidentProxy.updateIncident(incident);
        return savedIncident;
    }

    public Incident treatedIncident(Incident incident) {
        System.out.println("\tL'incident " + incident.getId() + "' est désormais TRAITE");
        incident.setTraitement("true");
        this.saveIncident(incident);
        return incident;
    }
}
