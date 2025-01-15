package au.com.telstra.simcardactivator.controllers;

import au.com.telstra.simcardactivator.components.SimCardActuationHandler;
import au.com.telstra.simcardactivator.entities.SimCard;
import au.com.telstra.simcardactivator.services.SimCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class SimCardController {
    @Autowired
    private final SimCardService simCardService;


    private final  SimCardActuationHandler simCardActuationHandler;
    @Autowired
    public SimCardController(SimCardService simCardService, SimCardActuationHandler simCardActuationHandler) {
        this.simCardService = simCardService;
        this.simCardActuationHandler = simCardActuationHandler;
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody SimCard simCard) {
        var actutaionResult = simCardActuationHandler.actuate(simCard);
        System.out.println(actutaionResult.getSuccess());
        if (actutaionResult.getSuccess()) {
            simCard.setActive(true);
            simCardService.saveSimCard(simCard);
            return ResponseEntity.ok("SIM activation successful.");
        } else {
            return ResponseEntity.status(500).body("SIM activation failed.");
        }
    }

    @GetMapping("/query")
    public SimCard getSimCardQuery(@RequestParam long simCardId) {
        SimCard simCard = simCardService.getSimCardById(simCardId);
        if (simCard != null) {
            return simCard;
        } else {

            return null;
        }
    }
}
