package com.rucreativedeveloper.medicare_api_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="pharmacist")
@Builder

public class Pharmacist {

    @Id
    @Column(name = "pharmacist_Id")
    private String pharmacistId;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private SystemUser systemUser;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="pharmacy_id",referencedColumnName = "pharmacy_id")
    private Pharmacy pharmacy;


}
