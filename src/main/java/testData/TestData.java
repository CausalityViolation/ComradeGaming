package testData;

import entities.DigitalGame;
import enums.Company;
import enums.Platform;
import enums.Tag;

import java.util.ArrayList;
import java.util.List;


//FIXA SÅ DEN LÄGGER UPP SAKER I DATABASEN VID STARTUP?!?!?!?!!?!?!?
//FIXA ENDPOINTS!
public class TestData {

    public void TestMethod() {
        DigitalGame marioKart = new DigitalGame(Platform.NINTENDO_SWITCH, Company.NINTENDO, Company.NINTENDO, "Mario Kart 8 Deluxe"
                , 599, "Drive around in karts with mario and his gay friends", "www.comradelel.lel/bildlank");
        tags.add(Tag.FOR_EVERYONE);
        tags.add(Tag.RACING);
        marioKart.addTagList(tags);
    }

    List<Tag> tags = new ArrayList<>();

}
