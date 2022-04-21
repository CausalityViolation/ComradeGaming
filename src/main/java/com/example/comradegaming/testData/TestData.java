package com.example.comradegaming.testData;

import com.example.comradegaming.entities.Clothing;
import com.example.comradegaming.entities.DigitalGame;
import com.example.comradegaming.entities.Movie;
import com.example.comradegaming.enums.*;

import java.util.ArrayList;
import java.util.List;


//FIXA SÅ DEN LÄGGER UPP SAKER I DATABASEN VID STARTUP?!?!?!?!!?!?!?
//FIXA ENDPOINTS!
public class TestData {

    public void TestMethod() {
//        Movie movie = new Movie("World of Grisar", 500, "Best movie-film in industry", Company.MOVIEFILM, "www.www.www", Category.Movie);
//        tags.add(Tag.FANTASY);
//        tags.add(Tag.MATURE);
//        movie.addTagList(tags);

        DigitalGame game1 = new DigitalGame(Platform.Playstation5, Company.BUNGIE_INC, Company.BUNGIE_INC,
                "Wat", 500, "Get it on in Japan boy.",
                "www.se.se", Category.DigitalGame);

        DigitalGame game2 = new DigitalGame(Platform.NintendoSwitch, Company.NINTENDO, Company.NINTENDO,
                "Something else", 500, "Please",
                "www.se.se", Category.DigitalGame);

        DigitalGame game3 = new DigitalGame(Platform.XBOXONE, Company.UBISOFT, Company.UBISOFT,
                "Generic Game", 250, "No",
                "www.se.se", Category.DigitalGame);

        Movie movie1 = new Movie("Griseknoa-Landet", 250, "Best movie-film in industry", Company.MOVIEFILM, "www.2.www", Category.Movie);
        Movie movie2 = new Movie("Fiskbulle-Landet", 500, "Also decent movie-film", Company.MOVIEFILM, "www.5.www", Category.Movie);
        Movie movie3 = new Movie("Gnälldala", 150, "Best movie-film of Sweden", Company.MOVIEFILM, "www.1.www", Category.Movie);

        Clothing cloth1 = new Clothing("Shirt of Doom", 25000, "Best clothing shirt in Sweden",
                Company.FROM_SOFTWARE, Garment.Hoodie, "www.fromsoft.se", Category.Clothing);

        Clothing cloth2 = new Clothing("Shirt of Anger", 250, "Decent clothing",
                Company.BUNGIE_INC, Garment.LongSleeve, "www.uffe.se", Category.Clothing);

        Clothing cloth3 = new Clothing("Shirt of Death", 200, "Not so good clothing",
                Company.Microsoft, Garment.Sweater, "www.blizz.se", Category.Clothing);


    }

    List<Tag> tags = new ArrayList<>();

}
