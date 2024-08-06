package com.okta.auth.security.practice.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Account implements Serializable {
    @Id
    private String id;

    private String iban;

    private String firstName;
    private String lastName;

//    @ElementCollection
//    @CollectionTable(
//            name = "phone_number",
//            joinColumns = @JoinColumn(name = "account_id")
//    )
//    private List<PhoneNumber> phoneNumbers;

    public Account() {
    }

    public Account(String id, String iban, String firstName, String lastName) {
        this.id = id;
        this.iban = iban;
        this.firstName = firstName;
        this.lastName = lastName;
//        this.phoneNumbers = new ArrayList<>();
    }

//    public void addPhoneNumber(PhoneNumber phoneNumber) {
//        if (!phoneNumbers.contains(phoneNumber)) {
//            this.phoneNumbers.add(phoneNumber);
//        }
//    }

    public String getId() {
        return id;
    }

    public String getIban() {
        return iban;
    }

//    public List<PhoneNumber> getPhoneNumbers() {
//        return phoneNumbers;
//    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
