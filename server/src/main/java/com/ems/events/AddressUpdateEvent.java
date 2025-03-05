package com.ems.events;

import com.ems.entities.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressUpdateEvent {

    private Address address;

    public AddressUpdateEvent(){}
    
    public AddressUpdateEvent(Address address) {
        this.address = address;
    }
}
