package com.ultron.acessb.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Document("piq_details")
public class PIQDetails {

    @Id
    private String id;

    private String accountId;
    private int version;

    private Details details;

    private String aiReviewComments;
    private String mentorsReviewComments;

    private Instant createdTS;
    private Instant reviewerUpdateTS;

    @Getter @Setter
    public static class Details {
        private String firstName;
        private String lastName;
        private String address;
        private String dob;
        private String pob;
        private String educationDetails;
        private String familyBackground;
        private String hobbies;
    }
}
