package org.medhead.emergencysystem.webclientoperators.service;

import org.medhead.emergencysystem.webclientoperators.model.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.medhead.emergencysystem.webclientoperators.repository.OperatorProxy;
import org.medhead.emergencysystem.webclientoperators.model.Operator;

import lombok.Data;

@Data
@Service
public class OperatorService {


    @Autowired
    private OperatorProxy operatorProxy;

    public Operator getOperator(final int id) {
        return operatorProxy.getOperator(id);
    }

    public Iterable<Operator> getOperators() {
        return operatorProxy.getOperators();
    }

    public void deleteOperator(final int id) {
        operatorProxy.deleteOperator(id);
    }

    public Operator saveOperator(Operator operator) {
        Operator savedOperator;

        // Functional rule : name must be capitalized.
        operator.setName(operator.getName().toUpperCase());

        if (operator.getId() == null) {
            //if id is null then it's a new operator
            savedOperator = operatorProxy.createOperator(operator);
        } else {
            savedOperator = operatorProxy.updateOperator(operator);
        }

        return savedOperator;
    }

    public Operator freeOperator(Operator operator) {
        System.out.println("\tL'opérateur '" + operator.getId() + "' est à nouveau DISPONIBLE");
        operator.setAvailable("true");
        this.saveOperator(operator);
        return operator;
    }

    public Operator attributedHospital(Hospital hospital, Operator operator) {
        System.out.println("\tL'opérateur '" + operator.getId() + "' a attribué l'hopital '" + hospital.getName() + "' à l'incident '" + operator.getIncidentId() );
        operator.setHospitalId(hospital.getId().toString());
        this.saveOperator(operator);
        return operator;
    }
}