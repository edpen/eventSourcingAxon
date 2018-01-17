package com.example.demo.Commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class CloseAccountCommand {
    @TargetAggregateIdentifier
    public final String id;
    public CloseAccountCommand(String id){
        this.id=id;
    }
}
