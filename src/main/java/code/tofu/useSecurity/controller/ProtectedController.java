package code.tofu.useSecurity.controller;

import code.tofu.useSecurity.exception.CustomControllerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedController {

    @GetMapping(path="/protected")
    public ResponseEntity<String> mustBeAuthenticated() {
        return ResponseEntity.ok("PROTECTED API OK");
    }

    @GetMapping(path="/write")
    public ResponseEntity<String> mustHaveWriteAuthority() {
        return ResponseEntity.ok("WRITE_AUTHORITY API OK");
    }

    @GetMapping(path="/delete")
    public ResponseEntity<String> mustHaveDeleteAuthority() {
        return ResponseEntity.ok("DELETE_AUTHORITY API OK");
    }

    @GetMapping(path="/except")
    public ResponseEntity<String> triggerException() throws Exception {
        throw new CustomControllerException("Exception Thrown to Be Caught by GlobalExceptionHandler");
    }



}