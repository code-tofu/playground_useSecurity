package code.tofu.useSecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedController {

    @GetMapping(path="/protected")
    public ResponseEntity<String> mustBeAuthenticated() {
        return ResponseEntity.ok("PROTECTED API OK");
    }
}