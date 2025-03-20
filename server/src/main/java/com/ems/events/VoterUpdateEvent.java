package com.ems.events;

import com.ems.entities.Address;
import com.ems.entities.Voter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
@Setter
public class VoterUpdateEvent {
    private Voter oldVoter;
    private Voter newVoter;
    private List<Address> oldAddress;
    private List<Address> newAddress;

<<<<<<< HEAD
    public VoterUpdateEvent(){};
=======
    public VoterUpdateEvent(){}
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511

    public VoterUpdateEvent(Voter oldVoter, Voter newVoter, List<Address> oldAddress, List<Address> newAddress) {
        this.oldVoter = oldVoter;
        this.newVoter = newVoter;
        this.oldAddress = oldAddress;
        this.newAddress = newAddress;
    }
}
