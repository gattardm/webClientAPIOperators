package org.medhead.emergencysystem.webclientoperators.controller;

import lombok.Data;
import org.medhead.emergencysystem.webclientoperators.model.Operator;
import org.medhead.emergencysystem.webclientoperators.service.HospitalService;
import org.medhead.emergencysystem.webclientoperators.service.OperatorService;
import org.medhead.emergencysystem.webclientoperators.model.Incident;
import org.medhead.emergencysystem.webclientoperators.model.Hospital;
import org.medhead.emergencysystem.webclientoperators.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Data
@Controller
public class OperatorController {

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private HospitalService hospitalService;


    @GetMapping("/")
    public String home(Model model) {
        Iterable<Operator> listOperators = operatorService.getOperators();


        for (Operator operator : listOperators) {
            if ( ! operator.getIncidentId().contains("n/a") ) {
                Incident incident = incidentService.getIncident(Integer.parseInt(operator.getIncidentId()));

                //Recherche d'un hopital
                Hospital hospital = null;
                while ( hospital == null ) {
                    hospital = hospitalService.lookForHospital(incident);
                }

                //Attribution de l'incident à l'hopital adéquat par l'opérateur
                hospitalService.attributedIncident(hospital, incident);
                hospitalService.modifyBedsAvailable(hospital);
                hospitalService.attributedOperator(hospital, operator);
                operatorService.attributedHospital(hospital, operator);

                //Passage d'un incident du statut "En cours" au statut "Traité"
                incidentService.treatedIncident(incident);

                //Déclaration de l'opérateur comme "Non disponible"
                operatorService.freeOperator(operator);
             }
        }

        listOperators = operatorService.getOperators();
        Iterable<Hospital> listHospitals = hospitalService.getHospitals();
        Iterable<Incident> listIncidents = incidentService.getIncidents();
        model.addAttribute("operators", listOperators);
        model.addAttribute("hospitals", listHospitals);
        model.addAttribute("incidents", listIncidents);

        return "home";
    }

    @GetMapping("/createOperator")
    public String createOperator(Model model) {
        Operator o = new Operator();
        model.addAttribute("operator", o);
        return "formNewOperator";
    }

    @GetMapping("updateOperator/{id}")
    public String updateOperator(@PathVariable("id") final int id, Model model) {
        Operator o = operatorService.getOperator(id);
        model.addAttribute("operator", o);
        return "formUpdateOperator";
    }

    @GetMapping("deleteOperator/{id}")
    public ModelAndView deleteOperator(@PathVariable("id") final int id) {
        operatorService.deleteOperator(id);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/saveOperator")
    public ModelAndView saveOperator(@ModelAttribute Operator operator) {
        if(operator.getId() != null) { Operator current = operatorService.getOperator(operator.getId()); }
        operatorService.saveOperator((operator));
        return new ModelAndView("redirect:/");
    }
}
