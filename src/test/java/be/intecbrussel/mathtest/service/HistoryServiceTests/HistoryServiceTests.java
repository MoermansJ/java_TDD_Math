package be.intecbrussel.mathtest.service.HistoryServiceTests;

import be.intecbrussel.mathtest.model.*;
import be.intecbrussel.mathtest.repository.OperationRepository;
import be.intecbrussel.mathtest.service.HistoryService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.List;


@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class HistoryServiceTests {
    //properties
    @Autowired
    private HistoryService historyService;
    @Mock
    private OperationRepository operationRepository;


    //preparation
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    //test methods
    @ParameterizedTest
    @MethodSource("history_addNewCalculation_data")
    public void historyService_addNewCalculation_returningOperation(OperationType operationType, String result, String... numbers) {
        //arrange
        Operation expectedResult = new Operation().setData(numbers[0], numbers[1], result, operationType);

        //act
        Mockito.when(operationRepository.save(expectedResult)).thenReturn(expectedResult);
        Operation actualResult = this.historyService.addNewCalculation(operationType, result, numbers);

        //assert
        Assertions.assertEquals(expectedResult, actualResult);
//        Mockito.verify(operationRepository).save(expectedResult); //zero interactions every time ...
    }

    @ParameterizedTest
    @MethodSource("history_addNewCalculation_data_exception")
    public void historyService_addNewCalculation_throwingException(OperationType operationType, Class<Exception> result, String... numbers) {
        //assert
        Assertions.assertThrows(result, () -> historyService.addNewCalculation(operationType, "", numbers));
    }

    @ParameterizedTest
    @MethodSource("history_retrieveLastOperation_data")
    public void historyService_retrieveLastOperation_test_returningList(int amount) {
        //arrange
        List<Operation> expectedResult = new ArrayList<>();

        for (int i = amount; i >= 0; i--) {
            expectedResult.add(new Operation().setData(i + "", i + "", i + "", OperationType.ADD));
        }

        for (int j = 0; j <= amount; j++) {
            historyService.addNewCalculation(OperationType.ADD, j + "", j + "", j + "");
        }

        //act
        List<Operation> actualResult = this.historyService.retrieveLastOperation(amount);

        //assert
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("history_retrieveLastOperation_data_exception")
    public void historyService_retrieveLastOperation_test_throwingException(int amount, Class<Exception> result) {
        //assert
        Assertions.assertThrows(result, () -> historyService.retrieveLastOperation(amount));
    }


    @Test
    public void historyService_retrieveOperations_test() {
        //act
        List<Operation> actualResult = this.historyService.retrieveOperations();

        //assert
        Assertions.assertNotNull(actualResult);
    }


    //data methods
    private static Stream<Arguments> history_addNewCalculation_data() {
        return Stream.of(
                Arguments.of(OperationType.ADD, "3", new String[]{"2", "1"}),
                Arguments.of(OperationType.ADD, "-3", new String[]{"-2", "-5"}),
                Arguments.of(OperationType.SUBTRACT, "3", new String[]{"2", "1"}),
                Arguments.of(OperationType.SUBTRACT, "-3", new String[]{"-2", "-1"}),
                Arguments.of(OperationType.MULTIPLY, "1", new String[]{"2", "2"}),
                Arguments.of(OperationType.MULTIPLY, "-1", new String[]{"2", "-2"}),
                Arguments.of(OperationType.DIVIDE, "10", new String[]{"2", "5"}),
                Arguments.of(OperationType.DIVIDE, "-10", new String[]{"2", "-5"})
        );
    }

    private static Stream<Arguments> history_addNewCalculation_data_exception() {
        return Stream.of(
                Arguments.of(OperationType.DIVIDE, Exception.class, new String[]{"2"}),
                Arguments.of(OperationType.DIVIDE, Exception.class, new String[]{"2", "3", "4"})

                //below tests are not necessary, as the BasicMathServiceImpl already checks the Strings?
//                Arguments.of(OperationType.DIVIDE, Exception.class, new String[]{"a", "b"}),
//                Arguments.of(OperationType.DIVIDE, Exception.class, new String[]{"a", "1"}),
//                Arguments.of(OperationType.DIVIDE, Exception.class, new String[]{"1", "b"})
        );
    }

    private static Stream<Arguments> history_retrieveLastOperation_data() {
        return Stream.of(
                Arguments.of(1),
                Arguments.of(2),
                Arguments.of(3),
                Arguments.of(100)
        );
    }

    private static Stream<Arguments> history_retrieveLastOperation_data_exception() {
        return Stream.of(
                Arguments.of(-1, Exception.class),
                Arguments.of(0, Exception.class)
        );
    }
}
