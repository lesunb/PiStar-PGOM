package br.com.unb;

import br.com.unb.comptest.BaseTest;
import br.com.unb.domain.PistarServiceFactory;
import br.com.unb.service.PistarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PistarModelFactoryApplicationTests extends BaseTest {

    @Autowired
    private PistarService service;

    @Test
    void executePistarFactoryMutrose() {
        PistarServiceFactory pistarFactory = getResourceAsClass("src/test/resources/MutRoSe/PistarFactoryMutrose.json", PistarServiceFactory.class);
        String resultMutRoSe = getResourceAsString("src/test/resources/MutRoSe/PistarFactoryMutroseResult.txt");
        Object result = this.service.executePistarFactoryService(pistarFactory);
        assertEquals(format(result), format(resultMutRoSe));
    }

    @Test
    void executePistarFactoryGODA() {
        PistarServiceFactory pistarFactory = getResourceAsClass("src/test/resources/GODA/PistarFactoryGODA.json", PistarServiceFactory.class);
        String resultMutRoSe = getResourceAsString("src/test/resources/GODA/PistarFactoryGodaResult.txt");
        Object result = this.service.executePistarFactoryService(pistarFactory);
        assertEquals(format(result), format(resultMutRoSe));
    }

//    @Test
//    void executePistarFactoryGODA2() {
//        PistarServiceFactory pistarFactory = getResourceAsClass("src/test/resources/GODA/PistarFactoryGodaMaster.json", PistarServiceFactory.class);
//        String resultMutRoSe = getResourceAsString("src/test/resources/GODA/PistarFactoryGodaResult.txt");
//        Object result = this.service.executePistarFactoryService(pistarFactory);
//        assertEquals(format(result), format(resultMutRoSe));
//    }
}
