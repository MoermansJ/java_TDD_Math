package be.intecbrussel.mathtest.service;

import be.intecbrussel.mathtest.model.*;
import be.intecbrussel.mathtest.repository.OperationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    //properties
    private OperationRepository operationRepo;


    //constructors
    public HistoryServiceImpl(OperationRepository operationRepo) {
        this.operationRepo = operationRepo;
    }


    //custom methods
    @Override
    public Operation addNewCalculation(OperationType operationType, String result, String... input) {
        if (!(input.length == 2)) {
            throw new IllegalArgumentException("Invalid amount of arguments provided. Input.length must be 2.");
        }


        Operation operationToSave = new Operation().setData(input[0], input[1], result, operationType);
        return this.operationRepo.save(operationToSave);
    }

    @Override
    public List<Operation> retrieveOperations() {
        return this.operationRepo.findAll();
    }

    @Override
    public List<Operation> retrieveLastOperation(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid argument provided for: amount. Must be greater than 0.");
        }
        return this.operationRepo.findTopNOperations(amount);
    }
}
