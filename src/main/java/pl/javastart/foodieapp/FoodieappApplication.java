package pl.javastart.foodieapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class FoodieappApplication {

    public static void main(String[] args)
    {
       ConfigurableApplicationContext ctx = SpringApplication.run(FoodieappApplication.class, args);
    }

}
