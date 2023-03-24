package org.medhead.emergencysystem.webclientoperators.repository;

import lombok.extern.slf4j.Slf4j;
import org.medhead.emergencysystem.webclientoperators.CustomProperties;
import org.medhead.emergencysystem.webclientoperators.model.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class OperatorProxy {

    @Autowired
    private CustomProperties props;

    /**
     * Get all operators
     * @return An iterable of all operators
     */
    public Iterable<Operator> getOperators() {

        String baseApiUrlOperators = props.getApiUrlOperators();
        String getOperatorsUrl = baseApiUrlOperators + "/operators";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Operator>> response = restTemplate.exchange(
                getOperatorsUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Operator>>() {}
        );

        log.debug(("Get Operators call " + response.getStatusCode().toString()));

        return response.getBody();
    }

    /**
     * Get an operator by the id
     * @param id The id of the operator
     * @return The operator which matches the id
     */
    public Operator getOperator(int id) {
        String baseApiUrlOperators = props.getApiUrlOperators();
        String getOperatorUrl = baseApiUrlOperators + "/operator/" +id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Operator> response = restTemplate.exchange(
                getOperatorUrl,
                HttpMethod.GET,
                null,
                Operator.class
        );

        log.debug("Get Operator call " + response.getStatusCode().toString());

        return response.getBody();
    }

    /**
     * Add a new operator
     * @param o A new operator (without an id)
     * @return The operator fulfilled (with an id)
     */
    public Operator createOperator(Operator o) {
        String baseApiUrlOperators = props.getApiUrlOperators();
        String createOperatorUrl = baseApiUrlOperators + "/operator";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Operator> request = new HttpEntity<Operator>(o);
        ResponseEntity<Operator> response = restTemplate.exchange(
                createOperatorUrl,
                HttpMethod.POST,
                request,
                Operator.class
        );

        log.debug("Create Operator call " + response.getStatusCode().toString());

        return response.getBody();
    }

    /**
     * Update an operator - using the PUT HTTP Method.
     * @param o Existing operator to update
     */
    public Operator updateOperator(Operator o) {
        String baseApiUrlOperators = props.getApiUrlOperators();
        String updateOperatorUrl = baseApiUrlOperators + "/operator/" + o.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Operator> request = new HttpEntity<Operator>(o);
        ResponseEntity<Operator> response = restTemplate.exchange(
                updateOperatorUrl,
                HttpMethod.PUT,
                request,
                Operator.class
        );

        log.debug("Update Operator call " + response.getStatusCode().toString());

        return response.getBody();
    }

    /**
     * Delete an operator using exchange method of RestTemplate
     * instead of delete method in order to log the response status code.
     * @param id The operator to delete
     */
    public void deleteOperator(int id) {
        String baseApiUrlOperators = props.getApiUrlOperators();
        String deleteOperatorUrl = baseApiUrlOperators + "/operator/" + id;

        RestTemplate restTemplate =new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(
                deleteOperatorUrl,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        log.debug("Delete Operator call " + response.getStatusCode().toString());
    }

}
