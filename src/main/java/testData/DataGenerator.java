package testData;

import entities.DigitalGame;
import enums.Category;
import enums.Company;
import enums.Platform;
import repo.ProductRepo;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class DataGenerator {
    @PersistenceContext
    ProductRepo productRepo;

    @PostConstruct
    public void addData(){
        DigitalGame game1 = new DigitalGame(Platform.PC, Company.BLIZZARD, Company.BLIZZARD,
                "Pornographic Anime Wonderland", 500, "Get it on in Japan boy.",
                "www.se.se", Category.DigitalGame);
        game1.setId(1);
        productRepo.save(game1);
    }
}