package com.ceylone_fusion.booking_service.service.serviceIMPL;

import com.ceylone_fusion.booking_service.dto.AccommodationDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.AccommodationGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.Accommodation;
import com.ceylone_fusion.booking_service.repo.AccommodationRepo;
import com.ceylone_fusion.booking_service.service.AccommodationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationServiceIMPL implements AccommodationService {

    @Autowired
    private AccommodationRepo accommodationRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AccommodationDTO saveAccommodation(AccommodationSaveRequestDTO accommodationSaveRequestDTO) {

        if(!accommodationRepo.existsByAccommodationCodeEqualsIgnoreCase(accommodationSaveRequestDTO.getAccommodationCode())){
            Accommodation accommodation = modelMapper.map(accommodationSaveRequestDTO, Accommodation.class);
            accommodationRepo.save(accommodation);
            return modelMapper.map(accommodation, AccommodationDTO.class);
        }
        else{
            throw new RuntimeException("Accommodation with code " + accommodationSaveRequestDTO.getAccommodationCode() + " is already exists");
        }
    }


    @Override
    public List<AccommodationGetResponseDTO> getAllAccommodations() {
        List<Accommodation> getAllAccommodations = accommodationRepo.findAll();
        if(getAllAccommodations.size() > 0){
            List<AccommodationGetResponseDTO> accommodationGetResponseDTOS = modelMapper.map(getAllAccommodations, new TypeToken<List<AccommodationGetResponseDTO>>(){}.getType());
            return accommodationGetResponseDTOS;
        }
        else{
            throw new RuntimeException("No Accommodation Found");
        }
    }

    @Override
    public List<AccommodationGetResponseDTO> getAccommodationById(Long accommodationId) {
        List<Accommodation> accommodations = accommodationRepo.findAllByAccommodationIdEquals(accommodationId);
        if(accommodations.size() > 0) {
            List<AccommodationGetResponseDTO> accommodationGetResponseDTOS = modelMapper.map(accommodations, new TypeToken<List<AccommodationGetResponseDTO>>(){}.getType());
            return accommodationGetResponseDTOS;
        }
        else {
            throw new RuntimeException("No Accommodation Found");
        }
    }

    @Override
    public List<AccommodationGetResponseDTO> getAccommodationByCode(String accommodationCode) {
        List<Accommodation> accommodations = accommodationRepo.findAllByAccommodationCodeEquals(accommodationCode);
        if(accommodations.size() > 0) {
            List<AccommodationGetResponseDTO> accommodationGetResponseDTOS = modelMapper.map(accommodations, new TypeToken<List<AccommodationGetResponseDTO>>(){}.getType());
            return accommodationGetResponseDTOS;
        }
        else {
            throw new RuntimeException("No Accommodation Found");
        }
    }

    @Override
    public AccommodationDTO updateAccommodationDetails(
            AccommodationUpdateRequestDTO accommodationUpdateRequestDTO,
            Long accommodationId
    ) {
        //Get accommodation by Accommodation ID
        if (accommodationRepo.existsById(accommodationId)) {
            // Get Accommodation by Accommodation ID and Map Accommodation Entity to Accommodation DTO
            Accommodation existingAccommodation = accommodationRepo.getReferenceById(accommodationId);

            // Update Accommodation name
            if (accommodationUpdateRequestDTO.getAccommodationName() != null) {
                existingAccommodation.setAccommodationName(accommodationUpdateRequestDTO.getAccommodationName());
            }

            // Update Accommodation Type
            if (accommodationUpdateRequestDTO.getAccommodationType() != null) {
                existingAccommodation.setAccommodationType(accommodationUpdateRequestDTO.getAccommodationType());
            }

            // Update Accommodation Description
            if (accommodationUpdateRequestDTO.getAccommodationDescription() != null) {
                existingAccommodation.setAccommodationDescription(accommodationUpdateRequestDTO.getAccommodationDescription());
            }

            // Update Accommodation Location
            if (accommodationUpdateRequestDTO.getLocation() != null) {
                existingAccommodation.setLocation(accommodationUpdateRequestDTO.getLocation());
            }

            // Save the updated Accommodation
            accommodationRepo.save(existingAccommodation);

            return modelMapper.map(existingAccommodation, AccommodationDTO.class);
        } else {
            throw new RuntimeException("Accommodation Not Found");
        }
    }

    @Override
    public String deleteAccommodationById(Long accommodationId) {
        // Get Accommodation by Accommodation ID
        if (accommodationRepo.existsById(accommodationId)) {
            String response = accommodationRepo.getReferenceById(accommodationId).getAccommodationName() + " Deleted!";

            //delete accommodation
            accommodationRepo.deleteById(accommodationId);
            return response;
        } else {
            throw new RuntimeException("Accommodation Not Found");
        }
    }

}



