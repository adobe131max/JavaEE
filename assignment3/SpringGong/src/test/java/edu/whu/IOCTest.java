package edu.whu;

import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IOCTest {

    @Test
    public void miniApplicationContextTest() {
        assertThrows(AssertionError.class, () -> {
            new MiniApplicationContext("application.xml");
        });
    }
}
