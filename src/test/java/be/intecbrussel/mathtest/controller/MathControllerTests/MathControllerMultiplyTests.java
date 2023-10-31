package be.intecbrussel.mathtest.controller.MathControllerTests;

import be.intecbrussel.mathtest.controller.MathController;
import be.intecbrussel.mathtest.service.BasicMathService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Stream;

public class MathControllerMultiplyTests {
    //properties
    @InjectMocks
    private MathController mathController;

    @Mock
    private BasicMathService basicMathService;


    //preparation
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    //test methods
    @ParameterizedTest
    @MethodSource("mathController_multiply_returnsResponseEntityOk_data")
    public void mathController_multiply_returnsResponseEntityOk(String number1, String number2) {
        //arrange
        String basicMathServiceResult = "OK";
        Mockito.when(basicMathService.multiply(number1, number2)).thenReturn(basicMathServiceResult);

        //act
        ResponseEntity actualResult = mathController.multiply(number1, number2);

        //assert
        Assertions.assertEquals(HttpStatus.OK, actualResult.getStatusCode());
    }

    @ParameterizedTest
    @MethodSource("mathController_multiply_returnsResponseEntityNotOk_data")
    public void setMathController_multiply_returnsResponseEntityNotOk(String number1, String number2) {
        //arrange
        Mockito.when(basicMathService.multiply(number1, number2)).thenThrow(new RuntimeException("Mockito Exception"));

        //act
        ResponseEntity actualResult = mathController.multiply(number1, number2);

        //assert
        Assertions.assertEquals(HttpStatus.PAYMENT_REQUIRED, actualResult.getStatusCode());
        Assertions.assertNotEquals(HttpStatus.OK, actualResult.getStatusCode());
    }


    //data methods
    private static Stream<Arguments> mathController_multiply_returnsResponseEntityOk_data() {
        return Stream.of(
                Arguments.of("1", "2"),
                Arguments.of("-1", "-2"),
                Arguments.of("0", "0"),
                Arguments.of("1000000000000000", "1000000000000000")
        );
    }

    private static Stream<Arguments> mathController_multiply_returnsResponseEntityNotOk_data() {
        return Stream.of(
                Arguments.of("1", "2"),
                Arguments.of("-1", "-2"),
                Arguments.of("0", "0"),
                Arguments.of("1000000000000000", "1000000000000000")
        );
    }
}
