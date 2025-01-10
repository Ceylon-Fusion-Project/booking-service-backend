package com.ceylone_fusion.booking_service.service.serviceIMPL;

import com.ceylone_fusion.booking_service.dto.AccommodationDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationSaveRequestDTO;
import com.ceylone_fusion.booking_service.entity.Accommodation;
import com.ceylone_fusion.booking_service.repo.AccommodationRepo;
import com.ceylone_fusion.booking_service.service.AccommodationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}


