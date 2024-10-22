package com.rucreativedeveloper.medicare_api_service.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="drug_inventory")
@Builder
public class DrugInventory {

    @Id
    private String inventoryId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_update_date",nullable = false,columnDefinition = "DATETIME")
    private Date lastUpdateDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pharmacy_id",referencedColumnName = "pharmacy_id")
    private Pharmacy pharmacy;

    @OneToMany(mappedBy = "inventory",cascade = CascadeType.ALL)
    private Set<Drug> drugs;



}
