package com.rucreativedeveloper.medicare_api_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="drug")
@Builder
public class Drug {

    @Id
    @Column(name="drugId")
    private String drugId;

    private String drugName;

    private String drugDescription;

    private String brandName;

    private String unitPrice;

    private String stockQty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="inventoryId",referencedColumnName = "inventoryId")
    private DrugInventory inventory;




}
