package remise;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RemiseException extends Exception {  // ← retour à Exception (checked)

    public RemiseException(String message) {
        super(message);
    }
}