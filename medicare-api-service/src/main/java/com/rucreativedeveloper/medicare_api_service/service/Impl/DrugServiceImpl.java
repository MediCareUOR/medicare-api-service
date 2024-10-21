package com.rucreativedeveloper.medicare_api_service.service.Impl;

import com.rucreativedeveloper.medicare_api_service.dto.paginated.ResponseDrugPaginatedDto;
import com.rucreativedeveloper.medicare_api_service.dto.paginated.ResponsePharmacistPaginatedDto;
import com.rucreativedeveloper.medicare_api_service.dto.request.RequestDrugDto;
import com.rucreativedeveloper.medicare_api_service.dto.response.ResponseDrugDto;
import com.rucreativedeveloper.medicare_api_service.dto.response.ResponseDrugInventoryDto;
import com.rucreativedeveloper.medicare_api_service.dto.response.ResponsePharmacyDto;
import com.rucreativedeveloper.medicare_api_service.dto.response.ResponseSystemUserDto;
import com.rucreativedeveloper.medicare_api_service.entity.*;
import com.rucreativedeveloper.medicare_api_service.exception.DuplicateEntryException;
import com.rucreativedeveloper.medicare_api_service.exception.EntryNotFoundException;
import com.rucreativedeveloper.medicare_api_service.repository.DrugInventoryRepo;
import com.rucreativedeveloper.medicare_api_service.repository.DrugRepo;
import com.rucreativedeveloper.medicare_api_service.repository.PharmacyRepo;
import com.rucreativedeveloper.medicare_api_service.service.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DrugServiceImpl implements DrugService {

    private final DrugRepo drugRepo;
    private final SystemUserServiceImpl systemUserServiceImpl;
    private final PharmacyRepo pharmacyRepo;
    private final DrugInventoryRepo drugInventoryRepo;

    @Override
    public void createDrug(RequestDrugDto requestDrugDto,String token) {

        SystemUser systemUser=systemUserServiceImpl.getUserByToken(token);

        if(systemUser==null){
            throw new EntryNotFoundException("System User Not Found");
        }

        Optional<Pharmacy> pharmacy=pharmacyRepo.findById(systemUser.getPharmacist().getPharmacy().getPharmacyId());

        if(pharmacy.isEmpty()){
            throw new EntryNotFoundException("Pharmacy Not Found");
        }


        DrugInventory drugInventory=drugInventoryRepo.getReferenceById(pharmacy.get().getDrugInventory().getInventoryId());

        Drug drug= Drug.builder()
                .drugId(UUID.randomUUID().toString())
                .drugName(requestDrugDto.getDrugName())
                .drugDescription(requestDrugDto.getDrugDescription())
                .stockQty(requestDrugDto.getStockQty())
                .inventory(drugInventory)
                .build();

        drugRepo.save(drug);

    }

    @Override
    public void updateDrug(String drugId, RequestDrugDto requestDrugDto) {


        Optional<Drug> selectedDrug=drugRepo.findById(drugId);

        if(selectedDrug.isEmpty()){
            throw new EntryNotFoundException("You have no pharmacist with id: "+drugId);
        }

        Drug drug=selectedDrug.get();

        drug.setDrugName(requestDrugDto.getDrugName());
        drug.setDrugDescription(requestDrugDto.getDrugDescription());
        drug.setStockQty(requestDrugDto.getStockQty());

        drugRepo.save(drug);


    }

    @Override
    public void deleteDrug(String drugId) {
        Optional<Drug> deletedDrug=drugRepo.findById(drugId);

        if(deletedDrug.isEmpty()){
            throw new EntryNotFoundException("You have no drug with id: "+drugId);
        }

        drugRepo.deleteById(drugId);
    }

    @Override
    public ResponseDrugDto findDrug(String drugId,String token) {

        Optional<Drug> foundDrug=drugRepo.findById(drugId);

        if(foundDrug.isEmpty()){
            throw new EntryNotFoundException("Drug not found");
        }

        String DrugInventoryId=foundDrug.get().getInventory().getInventoryId();

        if(Objects.equals(DrugInventoryId, getUserInventoryId(token))){
            Drug drug=foundDrug.get();


            return ResponseDrugDto.builder()
                    .drugId(drug.getDrugId())
                    .drugName(drug.getDrugName())
                    .drugDescription(drug.getDrugDescription())
                    .stockQty(drug.getStockQty())
                    .build();
        }


        return null;
    }

    @Override
    public ResponseDrugPaginatedDto findAllDrugs(String token, String searchText, int page, int size) {


        String drugInventoryId=getUserInventoryId(token);

        if (drugInventoryId==null){
            throw new EntryNotFoundException("You don't have any Inventory Id");
        }

        return ResponseDrugPaginatedDto.builder()
                .dataList(drugRepo.findAllDrugsWithSearchText(searchText, drugInventoryId, PageRequest.of(page, size))
                        .stream().map(this::toResponseDrugDto).toList())
                .count(
                        drugRepo.findAllDrugsCount(searchText,drugInventoryId)
                )
                .build();

    }

    private String getUserInventoryId(String token){

        SystemUser systemUser=systemUserServiceImpl.getUserByToken(token);

        if(systemUser==null){
            throw new EntryNotFoundException("System User Not Found");
        }

        Optional<DrugInventory> userDrugInventory= drugInventoryRepo.findById(systemUser.getPharmacist().getPharmacy().getDrugInventory().getInventoryId());

        if(userDrugInventory.isEmpty()){
            throw new EntryNotFoundException("Pharmacy Inventory Not Found");
        }

        return userDrugInventory.get().getInventoryId();
    }

    private ResponseDrugDto toResponseDrugDto(Drug drug){

        if(drug==null){
            return null;
        }

        return ResponseDrugDto.builder()
                .drugId(drug.getDrugId())
                .drugName(drug.getDrugName())
                .drugDescription(drug.getDrugDescription())
                .stockQty(drug.getDrugDescription())
                .build();
    }

}
