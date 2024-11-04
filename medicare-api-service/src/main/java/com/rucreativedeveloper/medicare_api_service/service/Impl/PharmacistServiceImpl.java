package com.rucreativedeveloper.medicare_api_service.service.Impl;

import com.rucreativedeveloper.medicare_api_service.dto.paginated.ResponseDrugPaginatedDto;
import com.rucreativedeveloper.medicare_api_service.dto.paginated.ResponsePharmacistPaginatedDto;
import com.rucreativedeveloper.medicare_api_service.dto.request.RequestPharmacistDto;
import com.rucreativedeveloper.medicare_api_service.dto.response.ResponsePharmacistDto;
import com.rucreativedeveloper.medicare_api_service.dto.response.ResponsePharmacistUserDto;
import com.rucreativedeveloper.medicare_api_service.dto.response.ResponsePharmacyDto;
import com.rucreativedeveloper.medicare_api_service.dto.response.ResponseSystemUserDto;
import com.rucreativedeveloper.medicare_api_service.entity.*;
import com.rucreativedeveloper.medicare_api_service.exception.DuplicateEntryException;
import com.rucreativedeveloper.medicare_api_service.exception.EntryNotFoundException;
import com.rucreativedeveloper.medicare_api_service.repository.*;
import com.rucreativedeveloper.medicare_api_service.service.PharmacistService;
import com.rucreativedeveloper.medicare_api_service.service.SystemUserService;
import com.rucreativedeveloper.medicare_api_service.service.process.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PharmacistServiceImpl implements PharmacistService {

    private final UserRoleRepo userRoleRepo;
    private final PharmacistRepo pharmacistRepo;
    private final SystemUserRepo systemUserRepo;
    private final SystemUserService systemUserService;
    private final PharmacyRepo pharmacyRepo;
    private final PasswordEncoder passwordEncoder;
    private final DrugInventoryRepo drugInventoryRepo;
    private final EmailService emailService;

    @Override
    public void registerPharmacist(RequestPharmacistDto requestPharmacistDto) throws IOException {

        SystemUser savedSystemUser=systemUserRepo.findByUsername(requestPharmacistDto.getRequestSystemUser().getUsername());


        if(savedSystemUser!=null){
                throw new DuplicateEntryException("You already registered as a pharmacist");
        }

        Optional<UserRole> selectedUserRoleData = userRoleRepo.findByRoleName("PHARMACIST");

        if(selectedUserRoleData.isPresent()){


            Set<UserRole> roles = new HashSet<>();
            roles.add(selectedUserRoleData.get());

            SystemUser newSystemUser=SystemUser.builder()
                    .userId(UUID.randomUUID().toString())
                    .username(requestPharmacistDto.getRequestSystemUser().getUsername())
                    .password(passwordEncoder.encode(requestPharmacistDto.getRequestSystemUser().getPassword()))
                    .createdDate(new Date())
                    .roles(roles)
                    .isAccountNonExpired(true)
                    .isAccountNonLocked(true)
                    .isCredentialsNonExpired(true)
                    .isEnabled(false)
                    .build();

            systemUserRepo.save(newSystemUser);

            Pharmacy pharmacy=Pharmacy.builder()
                    .pharmacyId(UUID.randomUUID().toString())
                    .pharmacyName(requestPharmacistDto.getRequestPharmacy().getPharmacyName())
                    .registerNumber(requestPharmacistDto.getRequestPharmacy().getRegisterNumber())
                    .contactNumber(requestPharmacistDto.getRequestPharmacy().getContactNumber())
                    .postal(requestPharmacistDto.getRequestPharmacy().getPostal())
                    .district(requestPharmacistDto.getRequestPharmacy().getDistrict())
                    .address(requestPharmacistDto.getRequestPharmacy().getAddress())
                    .city(requestPharmacistDto.getRequestPharmacy().getCity())
                    .longitude(requestPharmacistDto.getRequestPharmacy().getLongitude())
                    .latitude(requestPharmacistDto.getRequestPharmacy().getLatitude())
                    .build();

            pharmacyRepo.save(pharmacy);

            Pharmacist pharmacist=Pharmacist.builder()
                    .pharmacistId(UUID.randomUUID().toString())
                    .firstName(requestPharmacistDto.getFirstName())
                    .lastName(requestPharmacistDto.getLastName())
                    .phoneNumber(requestPharmacistDto.getPhoneNumber())
                    .systemUser(newSystemUser)
                    .pharmacy(pharmacy)
                    .build();

            pharmacistRepo.save(pharmacist);

            DrugInventory drugInventory= DrugInventory.builder()
                    .inventoryId(UUID.randomUUID().toString())
                    .lastUpdateDate(new Date())
                    .pharmacy(pharmacy)
                    .build();

            drugInventoryRepo.save(drugInventory);


            emailService.sendPharmacistWaiting(newSystemUser.getUsername(), "Your Account is Pending Activation – Awaiting Admin Approval");

        }


    }

    @Override
    public void updatePharmacist(String pharmacistId, RequestPharmacistDto requestPharmacistDto) {

        Optional<Pharmacist> selectedPharmacist=pharmacistRepo.findById(pharmacistId);



        if(selectedPharmacist.isEmpty()){
            throw new EntryNotFoundException("You have no pharmacist with id: "+pharmacistId);
        }

        Pharmacist pharmacist=selectedPharmacist.get();

        pharmacist.setFirstName(requestPharmacistDto.getFirstName());
        pharmacist.setLastName(requestPharmacistDto.getLastName());
        pharmacist.setPhoneNumber(requestPharmacistDto.getPhoneNumber());

        pharmacistRepo.save(pharmacist);


        Pharmacy updatedPharmacy=selectedPharmacist.get().getPharmacy();

        updatedPharmacy.setAddress(requestPharmacistDto.getRequestPharmacy().getAddress());
        updatedPharmacy.setCity(requestPharmacistDto.getRequestPharmacy().getCity());
        updatedPharmacy.setLongitude(requestPharmacistDto.getRequestPharmacy().getLongitude());
        updatedPharmacy.setLatitude(requestPharmacistDto.getRequestPharmacy().getLatitude());
        updatedPharmacy.setPharmacyName(requestPharmacistDto.getRequestPharmacy().getPharmacyName());
        updatedPharmacy.setRegisterNumber(requestPharmacistDto.getRequestPharmacy().getRegisterNumber());
        updatedPharmacy.setDistrict(requestPharmacistDto.getRequestPharmacy().getDistrict());
        updatedPharmacy.setPostal(requestPharmacistDto.getRequestPharmacy().getPostal());
        updatedPharmacy.setContactNumber(requestPharmacistDto.getRequestPharmacy().getContactNumber());

        pharmacyRepo.save(updatedPharmacy);

    }

    @Override
    public void deletePharmacist(String pharmacistId) {

        Optional<Pharmacist> deletedPharmacist=pharmacistRepo.findById(pharmacistId);


        if(deletedPharmacist.isEmpty()){
            throw new EntryNotFoundException("You have no pharmacist with id: "+pharmacistId);
        }

        String userId=deletedPharmacist.get().getSystemUser().getUserId();

        systemUserRepo.deleteById(userId);

    }

    @Override
    public ResponsePharmacistDto findPharmacist(String pharmacistId) {

        Optional<Pharmacist> foundPharmacist=pharmacistRepo.findById(pharmacistId);

        if(foundPharmacist.isEmpty()){
            throw new EntryNotFoundException("Pharmacist with id: "+pharmacistId+" not found");
        }

        Pharmacist pharmacist=foundPharmacist.get();

        Optional<Pharmacy> pharmacy=pharmacyRepo.findById(pharmacist.getPharmacy().getPharmacyId());

        if(pharmacy.isEmpty()){
            throw new EntryNotFoundException("Pharmacy with id: "+pharmacistId+" not found");
        }

       ResponsePharmacyDto responsePharmacyDto= ResponsePharmacyDto.builder()
                .pharmacyName(pharmacy.get().getPharmacyName())
                .pharmacyId(pharmacy.get().getPharmacyId())
                .address(pharmacy.get().getAddress())
                .city(pharmacy.get().getCity())
                .district(pharmacy.get().getDistrict())
                .postal(pharmacy.get().getPostal())
                .latitude(pharmacy.get().getLatitude())
                .longitude(pharmacy.get().getLongitude())
                .contactNumber(pharmacy.get().getContactNumber())
                .build();



        return ResponsePharmacistDto.builder()
                .pharmacistId(pharmacistId)
                .pharmacistName(pharmacist.getFirstName()+" "+pharmacist.getLastName())
                .phoneNumber(pharmacist.getPhoneNumber())
                .responsePharmacy(responsePharmacyDto)
                .build();

    }

    @Override
    public ResponsePharmacistPaginatedDto findAllPharmacist(String searchText, int page, int size) {

        return ResponsePharmacistPaginatedDto.builder()
                .dataList(pharmacistRepo.getAllPharmacists(searchText, PageRequest.of(page, size))
                        .stream().map(this:: mapToResponsePaginatedDto).toList())
                .count(
                        pharmacistRepo.getPharmacistCount(searchText)
                )
                .build();
    }

    @Override
    public void verifyPharmacist(String pharmacistId) throws IOException {

        Optional<Pharmacist> foundPharmacist=pharmacistRepo.findById(pharmacistId);

        System.out.println("works 1");
        if(foundPharmacist.isEmpty()){
            throw new EntryNotFoundException("Pharmacist with id: "+pharmacistId+" not found");
        }
        System.out.println("works 2");

        Pharmacist pharmacist=foundPharmacist.get();

        Optional<SystemUser> systemUser=systemUserRepo.findById(pharmacist.getSystemUser().getUserId());

        if(systemUser.isEmpty()){
            throw new EntryNotFoundException("System User not found");
        }
        System.out.println("works 3");

        SystemUser firstSystemUser=systemUser.get();

        firstSystemUser.setEnabled(true);

        systemUserRepo.save(firstSystemUser);


        emailService.sendPharmacistVerified(firstSystemUser.getUsername(), "Your Account Has Been Activated – Access Now");


    }

    @Override
    public long pharmacistCount() {

        return pharmacistRepo.pharmacistTotalCount();
    }

    private ResponsePharmacistDto mapToResponsePaginatedDto(Pharmacist pharmacist) {


        if(pharmacist==null){
            return null;
        }
        return ResponsePharmacistDto.builder()
                .pharmacistId(pharmacist.getPharmacistId())
                .pharmacistName(pharmacist.getFirstName()+" "+pharmacist.getLastName())
                .phoneNumber(pharmacist.getPhoneNumber())
                .responsePharmacistUserDto(ResponsePharmacistUserDto.builder()
                        .userName(pharmacist.getSystemUser().getUsername())
                        .isEnable(pharmacist.getSystemUser().isEnabled())
                        .build())
                .responsePharmacy(
                        ResponsePharmacyDto.builder()
                                .pharmacyId(pharmacist.getPharmacy().getPharmacyId())
                                .pharmacyName(pharmacist.getPharmacy().getPharmacyName())
                                .city(pharmacist.getPharmacy().getCity())
                                .registerNumber(pharmacist.getPharmacy().getRegisterNumber())
                                .address(pharmacist.getPharmacy().getAddress())
                                .contactNumber(pharmacist.getPharmacy().getContactNumber())
                                .district(pharmacist.getPharmacy().getDistrict())
                                .postal(pharmacist.getPharmacy().getPostal())
                                .latitude(pharmacist.getPharmacy().getLatitude())
                                .longitude(pharmacist.getPharmacy().getLongitude())
                                .build()
                )
                .build();
    }


}
