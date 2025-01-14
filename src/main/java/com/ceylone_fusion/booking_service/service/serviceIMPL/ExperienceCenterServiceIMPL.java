package com.ceylone_fusion.booking_service.service.serviceIMPL;

import com.ceylone_fusion.booking_service.dto.ExperienceCenterDTO;
import com.ceylone_fusion.booking_service.dto.request.ExperienceCenterSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.ExperienceCenterUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.ExperienceCenterGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.ExperienceCenter;
import com.ceylone_fusion.booking_service.repo.ExperienceCenterRepo;
import com.ceylone_fusion.booking_service.service.ExperienceCenterService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceCenterServiceIMPL implements ExperienceCenterService {

    @Autowired
    private ExperienceCenterRepo experienceCenterRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ExperienceCenterDTO saveExperienceCenter(ExperienceCenterSaveRequestDTO experienceCenterSaveRequestDTO) {
        if(!experienceCenterRepo.existsByExperienceCodeEqualsIgnoreCase(experienceCenterSaveRequestDTO.getExperienceName())){
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
        if(getAllExperienceCenters.size() > 0){
            List<ExperienceCenterGetResponseDTO> experienceCenterGetResponseDTOS = modelMapper.map(getAllExperienceCenters, new TypeToken<List<ExperienceCenterGetResponseDTO>>(){}.getType());
            return experienceCenterGetResponseDTOS;
        }
        else{
            throw new RuntimeException("No Accommodations Found");
        }
    }

    @Override
    public List<ExperienceCenterGetResponseDTO> getExperienceCenterById(Long experienceId) {
        List<ExperienceCenter> experienceCenters = experienceCenterRepo.findAllByExperienceIdEquals(experienceId);
        if(experienceCenters.size() > 0) {
            List<ExperienceCenterGetResponseDTO> experienceCenterGetResponseDTOS = modelMapper.map(experienceCenters, new TypeToken<List<ExperienceCenterGetResponseDTO>>(){}.getType());
            return experienceCenterGetResponseDTOS;
        }
        else {
            throw new RuntimeException("No Experience Center Found");
        }
    }

    @Override
    public List<ExperienceCenterGetResponseDTO> getExperienceCenterByCode(String experienceCode) {
        List<ExperienceCenter> experienceCenters = experienceCenterRepo.findAllByExperienceCodeEquals(experienceCode);
        if(experienceCenters.size() > 0) {
            List<ExperienceCenterGetResponseDTO> experienceCenterGetResponseDTOS = modelMapper.map(experienceCenters, new TypeToken<List<ExperienceCenterGetResponseDTO>>(){}.getType());
            return experienceCenterGetResponseDTOS;
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
            if (experienceCenterUpdateRequestDTO.isAvailable() != false) {
                existingExperienceCenter.setAvailable(experienceCenterUpdateRequestDTO.isAvailable());
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


}
