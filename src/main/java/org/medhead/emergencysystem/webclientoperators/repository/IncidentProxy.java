package org.medhead.emergencysystem.webclientoperators.repository;

import lombok.extern.slf4j.Slf4j;
import org.medhead.emergencysystem.webclientoperators.model.Incident;
import org.medhead.emergencysystem.webclientoperators.CustomProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class IncidentProxy {

    @Autowired
    private CustomProperties props;

    /**
     * Get all incidents
     * @return An iterable of all incidents
     */
    public Iterable<Incident> getIncidents() {

        String baseApiUrlIncidents = props.getApiUrlIncidents();
        String getIncidentsUrl = baseApiUrlIncidents + "/incidents";


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Incident>> response = restTemplate.exchange(
                getIncidentsUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Incident>>() {}
        );

        log.debug(("Get Incidents call " + response.getStatusCode().toString()));

        return response.getBody();
    }


    /**
     * Get an incident by the id
     * @param id The id of the incident
     * @return The incident which matches the id
     */
    public Incident getIncident(int id) {
        String baseApiUrlIncidents = props.getApiUrlIncidents();
        String getIncidentUrl = baseApiUrlIncidents + "/incident/" +id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Incident> response = restTemplate.exchange(
                getIncidentUrl,
                HttpMethod.GET,
                null,
                Incident.class
        );

        log.debug("Get Incident call " + response.getStatusCode().toString());

        return response.getBody();
    }

    /**
     * Update an incident - using the PUT HTTP Method.
     * @param i Existing incident to update
     */
    public Incident updateIncident(Incident i) {
        String baseApiUrlIncidents = props.getApiUrlIncidents();
        String updateIncidentUrl = baseApiUrlIncidents + "/incident/" + i.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Incident> request = new HttpEntity<Incident>(i);
        ResponseEntity<Incident> response = restTemplate.exchange(
                updateIncidentUrl,
                HttpMethod.PUT,
                request,
                Incident.class
        );

        log.debug("Update Incident call " + response.getStatusCode().toString());

        return response.getBody();
    }

    /**
     * Delete an incident using exchange method of RestTemplate
     * instead of delete method in order to log the response status code.
     * @param id The incident to delete
     */
    public void deleteIncident(int id) {
        String baseApiUrlIncidents = props.getApiUrlIncidents();
        String deleteIncidentUrl = baseApiUrlIncidents + "/incident/" + id;

        RestTemplate restTemplate =new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(
                deleteIncidentUrl,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        log.debug("Delete Incident call " + response.getStatusCode().toString());
    }
}
