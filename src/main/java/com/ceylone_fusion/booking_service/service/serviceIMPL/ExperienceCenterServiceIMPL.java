package com.ceylone_fusion.booking_service.service.serviceIMPL;

import com.ceylone_fusion.booking_service.dto.ExperienceCenterDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedExperienceCenterGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.ExperienceCenterSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.ExperienceCenterUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.ExperienceCenterGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.ExperienceCenter;
import com.ceylone_fusion.booking_service.repo.ExperienceCenterRepo;
import com.ceylone_fusion.booking_service.service.ExperienceCenterService;
import com.ceylone_fusion.booking_service.util.mappers.ExperienceCenterMapper;
import com.ceylone_fusion.booking_service.util.specifications.ExperienceCenterSpecifications;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceCenterServiceIMPL implements ExperienceCenterService {

    @Autowired
    private ExperienceCenterRepo experienceCenterRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ExperienceCenterMapper experienceCenterMapper;

    @Override
    public ExperienceCenterDTO saveExperienceCenter(ExperienceCenterSaveRequestDTO experienceCenterSaveRequestDTO) {
        if(!experienceCenterRepo.existsByExperienceCodeEqualsIgnoreCase(experienceCenterSaveRequestDTO.getExperienceCode())){
            ExperienceCenter experienceCenter = modelMapper.map(experienceCenterSaveRequestDTO, ExperienceCenter.class);
            experienceCenterRepo.save(experienceCenter);
            return modelMapper.map(experienceCenter, ExperienceCenterDTO.class);
        }
        else{
            throw new RuntimeException("Experience with code " + experienceCenterSaveRequestDTO.getExperienceCode() + " is already exists");
        }
    }

    @Override
    public List<ExperienceCenterGetResponseDTO> getAllExperienceCenters() {
        List<ExperienceCenter> getAllExperienceCenters = experienceCenterRepo.findAll();
        if(!getAllExperienceCenters.isEmpty()){
            return modelMapper.map(getAllExperienceCenters, new TypeToken<List<ExperienceCenterGetResponseDTO>>(){}.getType());
        }
        else{
            throw new RuntimeException("No Accommodations Found");
        }
    }


    @Override
    public PaginatedExperienceCenterGetResponseDTO getAllExperienceCentersSorted(boolean isAvailable, Pageable pageable) {
        // Get all experience centers with available status
        Page<ExperienceCenter> experienceCenters = experienceCenterRepo.findAllByIsAvailableEquals(isAvailable, pageable);
        if (!experienceCenters.isEmpty()) {
            // Map Experience Center Entity List to Experience Center DTO List
            List<ExperienceCenterDTO> experienceCenterDTOS = experienceCenterMapper.ExperienceCenterEntityListToExperienceCenterDTOList(experienceCenters);

            // Map Experience Center DTO List to ExperienceCenterGetResponseDTO List
            List<ExperienceCenterGetResponseDTO> experienceCenterGetResponseDTOS = experienceCenterMapper.experienceCenterDTOListToExperienceCenterGetResponseDTOList(experienceCenterDTOS);


            // Return PaginatedExperienceCenterGetResponseDTO
            return new PaginatedExperienceCenterGetResponseDTO(
                    experienceCenterGetResponseDTOS,
                    experienceCenterRepo.countExperienceCenterByIsAvailableEquals(isAvailable)
            );
        } else {
            throw new RuntimeException("No Experience Center Found");
        }
    }

    @Override
    public List<ExperienceCenterGetResponseDTO> getExperienceCenterById(Long experienceId) {
        List<ExperienceCenter> experienceCenters = experienceCenterRepo.findAllByExperienceIdEquals(experienceId);
        if(!experienceCenters.isEmpty()) {
            return modelMapper.map(experienceCenters, new TypeToken<List<ExperienceCenterGetResponseDTO>>(){}.getType());
        }
        else {
            throw new RuntimeException("No Experience Center Found");
        }
    }

    @Override
    public List<ExperienceCenterGetResponseDTO> getExperienceCenterByCode(String experienceCode) {
        List<ExperienceCenter> experienceCenters = experienceCenterRepo.findAllByExperienceCodeEquals(experienceCode);
        if(!experienceCenters.isEmpty()) {
            return modelMapper.map(experienceCenters, new TypeToken<List<ExperienceCenterGetResponseDTO>>(){}.getType());
        }
        else {
            throw new RuntimeException("No Experience Center Found");
        }
    }

    @Override
    public ExperienceCenterDTO updateExperienceCenterDetails(
            ExperienceCenterUpdateRequestDTO experienceCenterUpdateRequestDTO,
            Long experienceId
    ) {
        //Get Experience Center by Experience ID
        if (experienceCenterRepo.existsById(experienceId)) {
            // Get Experience Center by Experience ID and Map Experience Center Entity to Experience Center DTO
            ExperienceCenter existingExperienceCenter = experienceCenterRepo.getReferenceById(experienceId);

            // Update Experience name
            if (experienceCenterUpdateRequestDTO.getExperienceName() != null) {
                existingExperienceCenter.setExperienceName(experienceCenterUpdateRequestDTO.getExperienceName());
            }

            // Update Experience Description
            if (experienceCenterUpdateRequestDTO.getExperienceDescription() != null) {
                existingExperienceCenter.setExperienceDescription(experienceCenterUpdateRequestDTO.getExperienceDescription());
            }

            // Update Experience Location
            if (experienceCenterUpdateRequestDTO.getLocation() != null) {
                existingExperienceCenter.setLocation(experienceCenterUpdateRequestDTO.getLocation());
            }

            // Update Experience Total Cost
            if (experienceCenterUpdateRequestDTO.getTotalPrice() != null) {
                existingExperienceCenter.setTotalPrice(experienceCenterUpdateRequestDTO.getTotalPrice());
            }

            // Update Experience Is Available
            if (experienceCenterUpdateRequestDTO.isAvailable()) {
                existingExperienceCenter.setAvailable(true);
            }

            //Save the updated Experience Center
            experienceCenterRepo.save(existingExperienceCenter);

            return modelMapper.map(existingExperienceCenter, ExperienceCenterDTO.class);
        } else {
            throw new RuntimeException("Experience Center Not Found");
        }
    }

    @Override
    public String deleteExperienceCenterById(Long experienceId) {
        // Get Experience Center by Experience ID
        if (experienceCenterRepo.existsById(experienceId)) {
            String response = experienceCenterRepo.getReferenceById(experienceId).getExperienceName() + " Deleted!";

            //delete experience center
            experienceCenterRepo.deleteById(experienceId);
            return response;
        } else {
            throw new RuntimeException("Experience Center Not Found");
        }
    }


    @Override
    public PaginatedExperienceCenterGetResponseDTO getExperienceCenterByFiltering(
            String experienceName,
            String location,
            boolean isAvailable,
            Pageable pageable
    ) {
        Specification<ExperienceCenter> specification = Specification.
                where(ExperienceCenterSpecifications.isAvailable(isAvailable))
                .and(ExperienceCenterSpecifications.hasName(experienceName))
                .and(ExperienceCenterSpecifications.hasLocation(location));

        // Get all experience center with available
        Page<ExperienceCenter> experienceCenters = experienceCenterRepo.findAll(specification, pageable);
        if (!experienceCenters.isEmpty()) {
            // Map Experience Center Entity List to Experience Center DTO List
            List<ExperienceCenterDTO> experienceCenterDTOS = experienceCenterMapper.ExperienceCenterEntityListToExperienceCenterDTOList(experienceCenters);

            // Map Experience Center DTO List to ExperienceCenterGetResponseDTO List
            List<ExperienceCenterGetResponseDTO> experienceCenterGetResponseDTOS = experienceCenterMapper
                    .experienceCenterDTOListToExperienceCenterGetResponseDTOList(experienceCenterDTOS);

            return new PaginatedExperienceCenterGetResponseDTO(
                    experienceCenterGetResponseDTOS,
                    experienceCenterRepo.count(specification)
            );
        } else {
            throw new RuntimeException("No Experience Center Found");
        }
    }


}
