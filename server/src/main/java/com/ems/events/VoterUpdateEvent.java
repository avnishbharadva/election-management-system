package com.ems.events;

import com.ems.entities.Address;
import com.ems.entities.Voter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
@Setter
public class VoterUpdateEvent extends ApplicationEvent {
    private Voter oldVoter;
    private Voter newVoter;
    private List<Address> oldAddress;
    private List<Address> newAddress;

//    public VoterUpdateEvent(){}

    public VoterUpdateEvent(Object source, Voter oldVoter, Voter newVoter, List<Address> oldAddress, List<Address> newAddress) {
        super(source);
        this.oldVoter = oldVoter;
        this.newVoter = newVoter;
        this.oldAddress = oldAddress;
        this.newAddress = newAddress;
    }
}
