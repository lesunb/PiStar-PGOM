package br.com.unb;

import br.com.unb.comptest.BaseTest;
import br.com.unb.domain.PistarServiceFactory;
import br.com.unb.service.PistarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PistarModelFactoryApplicationTests extends BaseTest {

    private final static List<String> ERRORS = List.of(
            "{\"message\":\"Invalid Model File\",\"status\":400}",
            "{\"message\":\"Invalid HDDL File\",\"status\":400}",
            "{\"message\":\"Invalid Configuration File\",\"status\":400}",
            "{\"message\":\"Invalid World Knowledge File\",\"status\":400}",
            "Invalid property selected in vertex G1:Emergencyisdetected"
    );

    @Autowired
    private PistarService service;

    @Test
    @DisplayName("Execute Mutrose with success")
    void executePistarFactoryMutrose() {
        PistarServiceFactory pistarFactory = getResourceAsClass("src/test/resources/MutRoSe/PistarFactoryMutrose.json", PistarServiceFactory.class);
        String resultMutRoSe = getResourceAsString("src/test/resources/MutRoSe/PistarFactoryMutroseResult.txt");
        Object result = this.service.executePistarFactoryService(pistarFactory);
        assertEquals(format(result), format(resultMutRoSe));
    }

    @Test
    @DisplayName("Execute Mutrose with fail: Invalid Model File")
    void executePistarFactoryMutroseNotSendModelFile() {
        PistarServiceFactory pistarFactory = getResourceAsClass("src/test/resources/MutRoSe/PistarFactoryMutrose1.json", PistarServiceFactory.class);
        Exception exception = assertThrows(Exception.class, () -> {
            Object result = this.service.executePistarFactoryService(pistarFactory);
        });
        assertTrue(exception.getMessage().contains(ERRORS.get(0)));
    }

    @Test
    @DisplayName("Execute Mutrose with fail: Invalid HDDL File ")
    void executePistarFactoryMutroseNotSendHddlFile() {
        PistarServiceFactory pistarFactory = getResourceAsClass("src/test/resources/MutRoSe/PistarFactoryMutrose2.json", PistarServiceFactory.class);
        Exception exception = assertThrows(Exception.class, () -> {
            Object result = this.service.executePistarFactoryService(pistarFactory);
        });
        assertTrue(exception.getMessage().contains(ERRORS.get(1)));
    }

    @Test
    @DisplayName("Execute Mutrose with fail: Invalid Configuration File ")
    void executePistarFactoryMutroseNotSendConfigurationFile() {
        PistarServiceFactory pistarFactory = getResourceAsClass("src/test/resources/MutRoSe/PistarFactoryMutrose3.json", PistarServiceFactory.class);
        Exception exception = assertThrows(Exception.class, () -> {
            Object result = this.service.executePistarFactoryService(pistarFactory);
        });
        assertTrue(exception.getMessage().contains(ERRORS.get(2)));
    }

    @Test
    @DisplayName("Execute Mutrose with fail: Invalid World Knowledge File ")
    void executePistarFactoryMutroseNotSendWorldKnowledgeFile() {
        PistarServiceFactory pistarFactory = getResourceAsClass("src/test/resources/MutRoSe/PistarFactoryMutrose4.json", PistarServiceFactory.class);
        Exception exception = assertThrows(Exception.class, () -> {
            Object result = this.service.executePistarFactoryService(pistarFactory);
        });
        assertTrue(exception.getMessage().contains(ERRORS.get(3)));
    }

    @Test
    @DisplayName("Execute Mutrose with fail: Invalid Property File ")
    void executePistarFactoryMutroseInvalidPropertyModel() {
        PistarServiceFactory pistarFactory = getResourceAsClass("src/test/resources/MutRoSe/PistarFactoryMutrose5.json", PistarServiceFactory.class);
        Exception exception = assertThrows(Exception.class, () -> {
            Object result = this.service.executePistarFactoryService(pistarFactory);
        });
        assertTrue((exception.getMessage()).contains((ERRORS.get(4))));
    }

    @Test
    @DisplayName("Execute GODA MDP with succes ")
    void executePistarFactoryGODA() {
        PistarServiceFactory pistarFactory = getResourceAsClass("src/test/resources/GODA/PistarFactoryGODA.json", PistarServiceFactory.class);
        String resultMutRoSe = getResourceAsString("src/test/resources/GODA/PistarFactoryGodaResult.txt");
        Object result = this.service.executePistarFactoryService(pistarFactory);
//        assertEquals(format(result), format(resultMutRoSe));
        assertNotNull(result);
    }

//    @Test
//    void executePistarFactoryGODA2() {
//        PistarServiceFactory pistarFactory = getResourceAsClass("src/test/resources/GODA/PistarFactoryGodaMaster.json", PistarServiceFactory.class);
//        String resultMutRoSe = getResourceAsString("src/test/resources/GODA/PistarFactoryGodaResult.txt");
//        Object result = this.service.executePistarFactoryService(pistarFactory);
//        assertEquals(format(result), format(resultMutRoSe));
//    }
}
