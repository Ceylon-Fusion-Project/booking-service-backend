package com.ceylone_fusion.booking_service.service.serviceIMPL;

import com.ceylone_fusion.booking_service.dto.AccommodationDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedAccommodationGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.AccommodationGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.Accommodation;
import com.ceylone_fusion.booking_service.entity.enums.AccommodationType;
import com.ceylone_fusion.booking_service.repo.AccommodationRepo;
import com.ceylone_fusion.booking_service.service.AccommodationService;
import com.ceylone_fusion.booking_service.util.mappers.AccommodationMapper;
import com.ceylone_fusion.booking_service.util.specifications.AccommodationSpecifications;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccommodationServiceIMPL implements AccommodationService {

    @Autowired
    private AccommodationRepo accommodationRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccommodationMapper accommodationMapper;

    @Override
    public AccommodationDTO saveAccommodation(AccommodationSaveRequestDTO accommodationSaveRequestDTO) {
        if(!accommodationRepo.existsByAccommodationCodeEqualsIgnoreCase(accommodationSaveRequestDTO.getAccommodationCode())){
            System.out.println("Received DTO: " + accommodationSaveRequestDTO);
            Accommodation accommodation = modelMapper.map(accommodationSaveRequestDTO, Accommodation.class);

            if (accommodationSaveRequestDTO.isAvailable()) {
                accommodation.setAvailable(true);
            }

            accommodationRepo.save(accommodation);
            System.out.println("Mapped Entity: " + accommodation);
            return modelMapper.map(accommodation, AccommodationDTO.class);
        }
        else{
            throw new RuntimeException("Accommodation with code " + accommodationSaveRequestDTO.getAccommodationCode() + " is already exists");
        }
    }

    @Override
    public List<AccommodationGetResponseDTO> getAllAccommodations() {
        List<Accommodation> getAllAccommodations = accommodationRepo.findAll();
        if(!getAllAccommodations.isEmpty()){
            return modelMapper.map(getAllAccommodations, new TypeToken<List<AccommodationGetResponseDTO>>(){}.getType());
        }
        else{
            throw new RuntimeException("No Accommodations Found");
        }
    }

    @Override
    public PaginatedAccommodationGetResponseDTO getAllAccommodationsPaginated(Pageable pageable) {
        // Fetch paginated accommodations
        Page<Accommodation> accommodationsPage = accommodationRepo.findAll(pageable);
        if (accommodationsPage.hasContent()) {
            // Convert Page<Accommodation> to List<AccommodationGetResponseDTO>
            List<AccommodationGetResponseDTO> accommodationGetResponseDTOS = accommodationsPage.getContent().stream()
                    .map(accommodation -> modelMapper.map(accommodation, AccommodationGetResponseDTO.class))
                    .collect(Collectors.toList());
            // Return paginated response
            return new PaginatedAccommodationGetResponseDTO(
                    accommodationGetResponseDTOS,
                    accommodationsPage.getTotalElements()
            );
        } else {
            throw new RuntimeException("No Accommodations Found");
        }
    }

    @Override
    public PaginatedAccommodationGetResponseDTO getAllAccommodationsSorted(boolean isAvailable, Pageable pageable) {
        // Get all accommodations with available status
        Page<Accommodation> accommodations = accommodationRepo.findAllByIsAvailableEquals(isAvailable, pageable);
        if (!accommodations.isEmpty()) {
            // Map Accommodation Entity List to Accommodation DTO List
            List<AccommodationDTO> accommodationDTOS = accommodationMapper.AccommodationEntityListToAccommodationDTOList(accommodations);
            // Map Accommodation DTO List to AccommodationGetResponseDTO List
            List<AccommodationGetResponseDTO> accommodationGetResponseDTOS = accommodationMapper.accommodationDTOListToAccommodationGetResponseDTOList(accommodationDTOS);
            // Return PaginatedAccommodationGetResponseDTO
            return new PaginatedAccommodationGetResponseDTO(
                    accommodationGetResponseDTOS,
                    accommodationRepo.countAccommodationByIsAvailableEquals(isAvailable)
            );
        } else {
            throw new RuntimeException("No Accommodation Found");
        }
    }

    @Override
    public List<AccommodationGetResponseDTO> getAccommodationById(Long accommodationId) {
        List<Accommodation> accommodations = accommodationRepo.findAllByAccommodationIdEquals(accommodationId);
        if(!accommodations.isEmpty()) {
            return modelMapper.map(accommodations, new TypeToken<List<AccommodationGetResponseDTO>>(){}.getType());
        }
        else {
            throw new RuntimeException("No Accommodation Found");
        }
    }

    @Override
    public List<AccommodationGetResponseDTO> getAccommodationByCode(String accommodationCode) {
        List<Accommodation> accommodations = accommodationRepo.findAllByAccommodationCodeEquals(accommodationCode);
        if(!accommodations.isEmpty()) {
            return modelMapper.map(accommodations, new TypeToken<List<AccommodationGetResponseDTO>>(){}.getType());
        }
        else {
            throw new RuntimeException("No Accommodation Found");
        }
    }

    @Transactional
    @Override
//    public AccommodationDTO updateAccommodationDetails(
//            AccommodationUpdateRequestDTO accommodationUpdateRequestDTO,
//            Long accommodationId
//    ) {
//        //Get accommodation by Accommodation ID
//        if (accommodationRepo.existsById(accommodationId)) {
//            // Get Accommodation by Accommodation ID and Map Accommodation Entity to Accommodation DTO
//            Accommodation existingAccommodation = accommodationRepo.getReferenceById(accommodationId);
//            // Update Accommodation name
//            if (accommodationUpdateRequestDTO.getAccommodationName() != null) {
//                existingAccommodation.setAccommodationName(accommodationUpdateRequestDTO.getAccommodationName());
//            }
//            // Update Accommodation Type
//            if (accommodationUpdateRequestDTO.getAccommodationType() != null) {
//                existingAccommodation.setAccommodationType(accommodationUpdateRequestDTO.getAccommodationType());
//            }
//            // Update Accommodation Description
//            if (accommodationUpdateRequestDTO.getAccommodationDescription() != null) {
//                existingAccommodation.setAccommodationDescription(accommodationUpdateRequestDTO.getAccommodationDescription());
//            }
//            // Update Accommodation Location
//            if (accommodationUpdateRequestDTO.getLocation() != null) {
//                existingAccommodation.setLocation(accommodationUpdateRequestDTO.getLocation());
//            }
//            // Update Accommodation Is Available
//            if (accommodationUpdateRequestDTO.isAvailable()) {
//                existingAccommodation.setAvailable(true);
//            }
//            // Save the updated Accommodation
//            accommodationRepo.save(existingAccommodation);
//            return modelMapper.map(existingAccommodation, AccommodationDTO.class);
//        } else {
//            throw new RuntimeException("Accommodation Not Found");
//        }
//    }
    public AccommodationDTO updateAccommodationDetails(
            AccommodationUpdateRequestDTO dto,
            Long accommodationId
    ) {
        if (!accommodationRepo.existsById(accommodationId)) {
            throw new RuntimeException("Accommodation Not Found");
        }

        Accommodation accommodation = accommodationRepo.getReferenceById(accommodationId);

        // Update all fields, not conditionally
        accommodation.setAccommodationName(dto.getAccommodationName());
        accommodation.setAccommodationType(dto.getAccommodationType());
        accommodation.setAccommodationDescription(dto.getAccommodationDescription());
        accommodation.setLocation(dto.getLocation());
        accommodation.setAccommodationMapLink(dto.getAccommodationMapLink());
        accommodation.setAccDemoVideoLink(dto.getAccDemoVideoLink());
//        accommodation.setAvailable(dto.isAvailable()); // ✅ fixed logic

        if (dto.isAvailable()) {
            accommodation.setAvailable(true);
        }

        accommodationRepo.save(accommodation);

        return modelMapper.map(accommodation, AccommodationDTO.class);
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

    @Override
    public PaginatedAccommodationGetResponseDTO getAccommodationByFiltering(
            String accommodationName,
            AccommodationType accommodationType,
            String location, boolean isAvailable,
            Pageable pageable
    ) {
        Specification<Accommodation> specification = Specification.
                where(AccommodationSpecifications.isAvailable(isAvailable))
                .and(AccommodationSpecifications.hasName(accommodationName))
                .and(AccommodationSpecifications.hasType(accommodationType));
        // Get all accommodations with available
        Page<Accommodation> accommodations = accommodationRepo.findAll(specification, pageable);
        if (!accommodations.isEmpty()) {
            // Map Accommodation Entity List to Accommodation DTO List
            List<AccommodationDTO> accommodationDTOS = accommodationMapper.AccommodationEntityListToAccommodationDTOList(accommodations);
            // Map Accommodation DTO List to AccommodationGetResponseDTO List
            List<AccommodationGetResponseDTO> accommodationGetResponseDTOS = accommodationMapper
                    .accommodationDTOListToAccommodationGetResponseDTOList(accommodationDTOS);
            return new PaginatedAccommodationGetResponseDTO(
                    accommodationGetResponseDTOS,
                    accommodationRepo.count(specification)
            );
        } else {
            throw new RuntimeException("No Accommodation Found");
        }
    }

}



