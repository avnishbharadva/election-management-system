package com.ems.events;


import lombok.*;

@Getter
@Setter
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class EmailSendEvent {
    private String receiverMailAddress;
    private String emailSubject;
    private String mailBody;

}
