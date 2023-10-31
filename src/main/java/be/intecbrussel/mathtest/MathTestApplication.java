package be.intecbrussel.mathtest;

import be.intecbrussel.mathtest.service.BasicMathService;
import be.intecbrussel.mathtest.service.BasicMathServiceImpl;
import be.intecbrussel.mathtest.service.HistoryServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MathTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MathTestApplication.class, args);
    }

}
