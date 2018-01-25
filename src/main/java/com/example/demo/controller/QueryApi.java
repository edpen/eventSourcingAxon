package com.example.demo.controller;

import com.example.demo.model.AccountModel;
import com.example.demo.aggreagate.BankAccount;
import com.example.demo.enums.EventType;
import com.example.demo.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/queryAccounts")
@RestController
public class QueryApi {
 @Autowired
 BankAccount bankAccount;
 @GetMapping("/events")
 public @ResponseBody List viewAllEvents(){
     return bankAccount.getList();}
 @GetMapping("/account/{someID}")
 public @ResponseBody
 ResponseEntity<AccountModel> getAccount(@PathVariable String someID){
  AccountModel model=new AccountModel("",0,"");
  for(int i=0;i<bankAccount.getList().size();i++){

   if(bankAccount.getList().get(i).getId().equals(someID)){
      if(bankAccount.getList().get(i).getEventType()==EventType.CREATE){
       QueryCreatedEvent event= (QueryCreatedEvent) bankAccount.getList().get(i);
       model.setId(event.getId());
       model.setBalance(event.getBalance());
       model.setOwner(event.getAccountCreator());
      }
       else if(bankAccount.getList().get(i).getEventType()==EventType.DEPOSIT)
      {
       QueryDepositedEvent event=(QueryDepositedEvent)bankAccount.getList().get(i);
       model.setBalance(model.getBalance()+event.getAmount());
      }
      else if(bankAccount.getList().get(i).getEventType()==EventType.WITHDRAW){
       QueryWithdrawnEvent event=(QueryWithdrawnEvent)bankAccount.getList().get(i);
       model.setBalance(model.getBalance()-event.getAmount());
      }
      else if(bankAccount.getList().get(i).getEventType()==EventType.CLOSE){
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
   }
  }
  return new ResponseEntity<>(model,HttpStatus.OK);
 }

}
