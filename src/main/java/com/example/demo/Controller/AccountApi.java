package com.example.demo.Controller;

import com.example.demo.Aggreagate.BankAccount;
import com.example.demo.Commands.CloseAccountCommand;
import com.example.demo.Commands.CreateAccountCommand;
import com.example.demo.Commands.DepositMoneyCommand;
import com.example.demo.Commands.WithdrawMoneyCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/accounts")
@RestController
public class AccountApi {

    private final CommandGateway commandGateway;
    public AccountApi(CommandGateway commandGateway){
        this.commandGateway=commandGateway;
    }
    @PostMapping
    public CompletableFuture<String> createAccount(@RequestBody AccountOwner user){
        String id= UUID.randomUUID().toString();
        return commandGateway.send(new CreateAccountCommand(id,user.name));
    }
    static class AccountOwner{
        public String name;
    }
    @PutMapping(path="{accountId}/balance")
    public CompletableFuture<String> deposit(@RequestBody double amount,@PathVariable String accountId){
        if(amount>0){
            return commandGateway.send(new DepositMoneyCommand(accountId,amount));
        }
        else{
            return commandGateway.send(new WithdrawMoneyCommand(accountId,-amount));
        }
    }
    @DeleteMapping("{id}")
    public CompletableFuture<String> delete(@PathVariable String id){
        return commandGateway.send(new CloseAccountCommand(id));
    }
    @ExceptionHandler(AggregateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFound(){

    }
    @ExceptionHandler(BankAccount.InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String insufficientBalance(BankAccount.InsufficientBalanceException exception){
        return exception.getMessage();
    }
}
