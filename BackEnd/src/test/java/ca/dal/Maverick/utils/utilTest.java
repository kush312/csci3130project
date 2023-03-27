package ca.dal.Maverick.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

@SpringBootTest
@ContextConfiguration(classes = {Utils.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class utilTest {

    @Test
    public void isNullReturnTrue() {

        ArrayList<String> temp = new ArrayList<>();

        assertFalse(Utils.isNull(temp), "isNUll is not returning false when object is not null");
    }

    @Test
    public void isNullReturnFalse() {

        assertTrue(Utils.isNull(null), "isNUll is not returning true when object is null");
    }

}
