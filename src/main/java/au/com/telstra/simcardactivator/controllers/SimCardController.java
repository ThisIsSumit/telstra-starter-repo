package au.com.telstra.simcardactivator.controllers;

import au.com.telstra.simcardactivator.components.SimCardActuationHandler;
import au.com.telstra.simcardactivator.models.SimCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class SimCardController {
    private  SimCardActuationHandler simCardActuationHandler;
    @PostMapping("/activate")
    public void activateSim(@RequestBody SimCard simCard) {
        var actutaionResult = simCardActuationHandler.actuate(simCard);
        System.out.println(actutaionResult.getSuccess());
    }
}
