package com.ems.events;

import com.ems.entities.Voter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class VoterUpdateEvent extends ApplicationEvent {
    private Voter oldVoter;
    private Voter newVoter;

    public VoterUpdateEvent(Object source, Voter oldVoter, Voter newVoter) {
        super(source);
        this.oldVoter = oldVoter;
        this.newVoter = newVoter;
    }
}
