//package Application.Forum;
//
//import Application.Recipes.Recipe;
//import Application.Recipes.RecipeRepository;
//import Application.users.User;
//import Application.users.UserRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//
//@Configuration
//public class LoadDatabase {
//
//    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
//
//    @Bean
//    public CommandLineRunner initDatabase(ForumRepository repository){
//        return args -> {
//            log.info("Preloading " + repository.save(new ForumThread("AuthorTest", "TitleTest", "ContentTest")));
//            log.info("Preloading " + repository.save(new ForumThread("AnotherAuthor", "AnotherTitle" ,"SomeMoreContent")));
//
//             };
//    }
//
//    @Bean
//    public CommandLineRunner initRecipeDatabase(RecipeRepository reciperepo){
//        return args -> {
//            ArrayList<String> one = new ArrayList<>();
//            one.add("User");
//
//            ArrayList<String> five = new ArrayList<>();
//            for (int i = 0; i < 5; i++) {
//                five.add("user"+i);
//            }
//
//            ArrayList<String> overNineThousand = new ArrayList<>();
//            for (int i = 0; i < 9001; i++) {
//                overNineThousand.add("TestUser" + (i+1));
//            }
//
//
//
//            log.info("Preloading " + reciperepo.save(new Recipe(45, 2, five, one, "Beetje van dit, beetje van dat", "Gewoon lekker koken man, niet zo moeilijk doen.", "Gewoon wat te eten")));
//            log.info("Preloading " + reciperepo.save(new Recipe(2, 1, overNineThousand, new ArrayList<String>(), "2 sneeën brood, 1 plak kaas, 1 plak ham, klodder mayonaise.", "Doe de ham en kaas tussen de sneeën brood.\nStop de boterham in het tosti-ijzer tot het goudbruin gekleurd is.\nServeer met een klodder mayonaise.", "Ham-kaas tosti")));
//        };
//    }
//
//    @Bean
//    public CommandLineRunner initUserDatabase(UserRepository repo){
//        return args -> {
//          log.info ("Preloading: " + repo.save(new User("test", "test", "test@email.com")));
//        };
//    }
//}
