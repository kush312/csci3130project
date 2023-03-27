package ca.dal.Maverick.utils;

import ca.dal.Maverick.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {UUIDUtils.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UUIDUtilsTest {

    @Test
    public void generateUUIDReturn() {

        assertNotNull(UUIDUtils.generateUUID(), "generateUUID is returning null when is suppose to return an uuid");
    }

}

