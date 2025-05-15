package code.tofu.useSecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    @GetMapping(path="/")
    public ResponseEntity<String> publicLanding() {
        return ResponseEntity.ok("PUBLIC API OK");
    }

}