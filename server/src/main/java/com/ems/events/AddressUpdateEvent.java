package com.ems.events;

import com.ems.entities.Address;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class AddressUpdateEvent extends ApplicationEvent {

    private Address address;

    public AddressUpdateEvent(Object source, Address address) {
        super(source);
        this.address = address;
    }
}
