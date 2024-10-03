package friendsofmine.m2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class FriendsOfMineApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendsOfMineApplication.class, args);
    }

}
