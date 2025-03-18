package com.ems.events;

import com.ems.entities.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressUpdateEvent {

    private String voterId;
    private Address address;

    public AddressUpdateEvent(){}
    
    public AddressUpdateEvent(String voterId, Address address) {
        this.voterId = voterId;
        this.address = address;
    }
}
