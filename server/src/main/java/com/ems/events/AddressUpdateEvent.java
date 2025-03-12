package com.ems.events;

import com.ems.entities.Address;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class AddressUpdateEvent extends ApplicationEvent {

    private String voterId;
    private Address address;

//    public AddressUpdateEvent(){}

    public AddressUpdateEvent(Object source, String voterId, Address address) {
        super(source);
        this.voterId = voterId;
        this.address = address;
    }


//    public AddressUpdateEvent(String voterId, Address address) {
//        this.voterId = voterId;
//        this.address = address;
//    }
}
