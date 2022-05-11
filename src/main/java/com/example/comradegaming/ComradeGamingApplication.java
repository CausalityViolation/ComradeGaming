package com.example.comradegaming;

import com.example.comradegaming.entities.Clothing;
import com.example.comradegaming.entities.DigitalGame;
import com.example.comradegaming.entities.Movie;
import com.example.comradegaming.entities.User;
import com.example.comradegaming.enums.Company;
import com.example.comradegaming.enums.Garment;
import com.example.comradegaming.enums.Platform;
import com.example.comradegaming.repo.ProductRepo;
import com.example.comradegaming.repo.UserRepo;
import com.example.comradegaming.service.ProductService;
import com.example.comradegaming.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class ComradeGamingApplication {


    public static void main(String[] args) {
        SpringApplication.run(ComradeGamingApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder pwEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner loadDataOnStartup(UserRepo userRepo, ProductRepo productRepo) {

        return (args) -> {

            Iterable<User> foundUsers = userRepo.findAll();
            ArrayList<User> list = new ArrayList<>();

            for (User user : foundUsers) {
                list.add(user);
            }

            if (list.isEmpty()) {

                BCryptPasswordEncoder encoder = pwEncoder();
                User admin = new User("admin", encoder.encode("123"));
                User user1 = new User("dolan", encoder.encode("123"));
                User user2 = new User("gooby", encoder.encode("123"));

                admin.makeAdmin();


                Movie movie1 = new Movie("The Cement", 123, "Watch concrete dry!!!!", Company.MovieFilmAB, "cement.jpg");
                Movie movie2 = new Movie("The Tree", 123, "Witness the tree growing!!1", Company.MovieFilmAB, "tree.gif");

                DigitalGame game1 = new DigitalGame(Platform.NintendoSwitch, Company.Nintendo, Company.Nintendo, "Marios Teatime", 699
                        , "Drink tea with the italian plumber", "mario.png");
                DigitalGame game2 = new DigitalGame(Platform.Playstation5, Company.SonyInteractiveEntertainment, Company.EpicGames, "President Evil", 666
                        , "Live a day as Donald Trump", "mario.png");
                Clothing cloth1 = new Clothing("T-Shirt with weird stain", 5, "You can get this one for cheap!"
                        , Company.ClothingForPeopleInc, Garment.TShirt, "dirrrrty.jpg");
                Clothing cloth2 = new Clothing("Wedding Dress", 50000, "Is she really worth this much?"
                        , Company.ClothingForPeopleInc, Garment.LongSleeve, "bride.png");

                userRepo.save(admin);
                userRepo.save(user1);
                userRepo.save(user2);

                productRepo.save(movie1);
                productRepo.save(movie2);
                productRepo.save(game1);
                productRepo.save(game2);
                productRepo.save(cloth1);
                productRepo.save(cloth2);
            }

        };
    }

}


