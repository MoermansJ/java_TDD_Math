package be.intecbrussel.mathtest.controller;

import be.intecbrussel.mathtest.service.BasicMathService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/math")
public class MathController {
    //properties
    private BasicMathService basicMathService;


    //constructor
    public MathController(BasicMathService basicMathService) {
        this.basicMathService = basicMathService;
    }


    //custom methods
    @PostMapping("/add")
    public ResponseEntity add(@RequestParam String number1, String number2) {
        try {
            return ResponseEntity.ok(this.basicMathService.add(number1, number2));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        }
    }

    @PostMapping("/subtract")
    public ResponseEntity subtract(@RequestParam String number1, String number2) {
        try {
            return ResponseEntity.ok(this.basicMathService.subtract(number1, number2));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        }
    }

    @PostMapping("/multiply")
    public ResponseEntity multiply(@RequestParam String number1, String number2) {
        try {
            return ResponseEntity.ok(this.basicMathService.multiply(number1, number2));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        }
    }

    @PostMapping("/divide")
    public ResponseEntity divide(@RequestParam String number1, String number2) {
        try {
            return ResponseEntity.ok(this.basicMathService.divide(number1, number2));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).build();
        }
    }
}
