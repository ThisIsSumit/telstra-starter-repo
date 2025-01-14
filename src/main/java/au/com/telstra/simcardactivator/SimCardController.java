package au.com.telstra.simcardactivator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/sim")
public class SimCardController {

    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody Map<String, String> payload) {
        String iccid = payload.get("iccid");
        String customerEmail = payload.get("customerEmail");

        if (iccid == null || customerEmail == null) {
            return ResponseEntity.badRequest().body("Missing required fields: iccid or customerEmail");
        }

        String actuatorUrl = "http://localhost:8444/actuate";
        RestTemplate restTemplate = new RestTemplate();

        // json for actuator
        Map<String, String> requestBody = Map.of("iccid", iccid);

        // Relay the request to the actuator
        try {
            ResponseEntity<Map> actuatorResponse = restTemplate.postForEntity(actuatorUrl, requestBody, Map.class);
            boolean success = (boolean) actuatorResponse.getBody().get("success");

            return success
                    ? ResponseEntity.ok("SIM activation successful.")
                    : ResponseEntity.status(500).body("SIM activation failed.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error communicating with the actuator: " + e.getMessage());
        }
    }
}
