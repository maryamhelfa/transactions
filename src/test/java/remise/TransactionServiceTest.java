package remise;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    void testSaveOK() {
        Transaction t = transactionService.save(2000, 1800, null);
        assertNotNull(t.getId());
    }

    @Test
    void testException() {
        assertThrows(RemiseException.class, () -> {
            transactionService.save(0, 0, null);
        });
    }
}