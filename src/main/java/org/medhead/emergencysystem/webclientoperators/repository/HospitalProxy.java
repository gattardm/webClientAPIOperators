package org.medhead.emergencysystem.webclientoperators.repository;

import lombok.extern.slf4j.Slf4j;
import org.medhead.emergencysystem.webclientoperators.CustomProperties;
import org.medhead.emergencysystem.webclientoperators.model.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class HospitalProxy {

    @Autowired
    private CustomProperties props;

    /**
     * Get all employees
     * @return An iterable of all hospitals
     */
    public Iterable<Hospital> getHospitals() {

        String baseApiUrlHospitals = props.getApiUrlHospitals();
        String getHospitalsUrl = baseApiUrlHospitals + "/hospitals";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Hospital>> response = restTemplate.exchange(
                getHospitalsUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Hospital>>() {}
        );

        log.debug(("Get Hospitals call " + response.getStatusCode().toString()));

        return response.getBody();
    }

    /**
     * Get a hospital by the id
     * @param id The id of the hospital
     * @return The hospital which matches the id
     */
    public Hospital getHospital(int id) {
        String baseApiUrlHospitals = props.getApiUrlHospitals();
        String getHospitalUrl = baseApiUrlHospitals + "/hospital/" +id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Hospital> response = restTemplate.exchange(
                getHospitalUrl,
                HttpMethod.GET,
                null,
                Hospital.class
        );

        log.debug("Get Hospital call " + response.getStatusCode().toString());

        return response.getBody();
    }

    /**
     * Add a new hospital
     * @param h A new hospital (without an id)
     * @return The employee fulfilled (with an id)
     */
    public Hospital createHospital(Hospital h) {
        String baseApiUrlHospitals = props.getApiUrlHospitals();
        String createHospitalUrl = baseApiUrlHospitals + "/hospital";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Hospital> request = new HttpEntity<Hospital>(h);
        ResponseEntity<Hospital> response = restTemplate.exchange(
                createHospitalUrl,
                HttpMethod.POST,
                request,
                Hospital.class
        );

        log.debug("Create Hospital call " + response.getStatusCode().toString());

        return response.getBody();
    }

    /**
     * Update a hospital - using the PUT HTTP Method.
     * @param h Existing hospital to update
     */
    public Hospital updateHospital(Hospital h) {
        String baseApiUrlHospitals = props.getApiUrlHospitals();
        String updateHospitalUrl = baseApiUrlHospitals + "/hospital/" + h.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Hospital> request = new HttpEntity<Hospital>(h);
        ResponseEntity<Hospital> response = restTemplate.exchange(
                updateHospitalUrl,
                HttpMethod.PUT,
                request,
                Hospital.class
        );

        log.debug("Update Hospital call " + response.getStatusCode().toString());

        return response.getBody();
    }

    /**
     * Delete a hospital using exchange method of RestTemplate
     * instead of delete method in order to log the response status code.
     * @param id The hospital to delete
     */
    public void deleteHospital(int id) {
        String baseApiUrlHospitals = props.getApiUrlHospitals();
        String deleteHospitalUrl = baseApiUrlHospitals + "/hospital/" + id;

        RestTemplate restTemplate =new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(
                deleteHospitalUrl,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        log.debug("Delete Hospital call " + response.getStatusCode().toString());
    }

}

