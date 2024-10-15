package com.rucreativedeveloper.medicare_api_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name="pharmacy")
public class Pharmacy {
    @Id
    @Column(name="pharmacy_id")
    private String pharmacyId;

    @Column(name="pharmacy_Name")
    private String pharmacyName;

    private String registerNumber;

    private String contactNumber;

    @Column(name="postal",length = 45)
    private String postal;

    @Column(name="district",length = 45)
    private String district;

    @Column(name="address")
    private String address;

    @Column(name="city",length = 45)
    private String city;

    @Column(name="longitude",length=45)
    private String longitude;

    @Column(name="latitude",length=45)
    private String latitude;

    @OneToOne(mappedBy = "pharmacy",cascade = CascadeType.ALL)
    private Pharmacist pharmacist;

    @OneToOne(mappedBy = "pharmacy",cascade = CascadeType.ALL)
    private DrugInventory drugInventory;



}
