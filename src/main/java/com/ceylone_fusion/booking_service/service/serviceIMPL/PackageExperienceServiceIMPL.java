package com.ceylone_fusion.booking_service.service.serviceIMPL;

import com.ceylone_fusion.booking_service.dto.PackageExperienceDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedPackageExperienceGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageExperienceSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageExperienceUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.PackageExperienceGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.PackageExperience;
import com.ceylone_fusion.booking_service.repo.ExperienceCenterRepo;
import com.ceylone_fusion.booking_service.repo.PackageExperienceRepo;
import com.ceylone_fusion.booking_service.repo.PackageRepo;
import com.ceylone_fusion.booking_service.service.PackageExperienceService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageExperienceServiceIMPL implements PackageExperienceService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PackageExperienceRepo packageExperienceRepo;

    @Autowired
    private PackageRepo packageRepo;

    @Autowired
    private ExperienceCenterRepo experienceCenterRepo;


    @Override
    public PackageExperienceDTO savePackageExperience(PackageExperienceSaveRequestDTO packageExperienceSaveRequestDTO) {
        Long packageId = packageExperienceSaveRequestDTO.getPackageId();
        Long experienceId = packageExperienceSaveRequestDTO.getExperienceId();
        // Validate if the Package and Experience exist
        if (packageRepo.existsById(packageId) && experienceCenterRepo.existsById(experienceId)) {
            // Create a new PackageExperience instance
            PackageExperience packageExperience = new PackageExperience(
                    null, // ID is auto-generated
                    packageExperienceSaveRequestDTO.getQuantity(),
                    packageRepo.findPackagesByPackageIdEquals(packageId), // Find the Package entity
                    experienceCenterRepo.findExperienceByExperienceIdEquals(experienceId) // Find the Experience Center entity
            );
            // Save the new PackageExperience
            packageExperienceRepo.save(packageExperience);
            // Map the entity to a DTO and return it
            return modelMapper.map(packageExperience, PackageExperienceDTO.class);
        } else {
            throw new RuntimeException("Package or Experience Not Found");
        }
    }

    @Override
    public List<PackageExperienceSaveRequestDTO> getAllPackageExperiences() {
        List<PackageExperience> getAllPackageExperiences = packageExperienceRepo.findAll();
        if(!getAllPackageExperiences.isEmpty()){
            return modelMapper.map(getAllPackageExperiences, new TypeToken<List<PackageExperienceGetResponseDTO>>(){}.getType());
        }
        else{
            throw new RuntimeException("No Experience Package Found");
        }
    }

    @Override
    public PaginatedPackageExperienceGetResponseDTO getAllPackageExperiencesPaginated(Pageable pageable) {
        // Fetch paginated package experience
        Page<PackageExperience> packageExperiencesPage = packageExperienceRepo.findAll(pageable);
        if (packageExperiencesPage.hasContent()) {
            // Convert Page<PackageExperience> to List<PackageExperienceGetResponseDTO>
            List<PackageExperienceGetResponseDTO> packageExperienceGetResponseDTOS = packageExperiencesPage.getContent().stream()
                    .map(packageExperience -> modelMapper.map(packageExperience, PackageExperienceGetResponseDTO.class))
                    .toList();
            // Return paginated response
            return new PaginatedPackageExperienceGetResponseDTO(
                    packageExperienceGetResponseDTOS,
                    packageExperiencesPage.getTotalElements()
            );
        } else {
            throw new RuntimeException("No Package Experiences Found");
        }
    }

    @Override
    public List<PackageExperienceGetResponseDTO> getPackageExperienceById(Long packageExperienceId) {
        List<PackageExperience> packageExperiences = packageExperienceRepo.findAllByPackageExperienceIdEquals(packageExperienceId);
        if(!packageExperiences.isEmpty()) {
            return modelMapper.map(packageExperiences, new TypeToken<List<PackageExperienceGetResponseDTO>>(){}.getType());
        }
        else {
            throw new RuntimeException("No Experience Package Found");
        }
    }

    @Override
    public List<PackageExperienceGetResponseDTO> getAllPackageExperienceDetails(
            Long packageId,
            Long experienceId
    ) {
        List<PackageExperience> packageExperiences;
        if (packageId != null && experienceId != null) {
            packageExperiences = packageExperienceRepo.findByPackages_PackageIdAndExperience_ExperienceId(packageId, experienceId);
        } else if (packageId != null) {
            packageExperiences = packageExperienceRepo.findByPackages_PackageId(packageId);
        } else if (experienceId != null) {
            packageExperiences = packageExperienceRepo.findByExperience_ExperienceId(experienceId);
        } else {
            packageExperiences = packageExperienceRepo.findAll();
        }
        // Map entities to DTOs
        return packageExperiences.stream()
                .map(packageExperience -> new PackageExperienceGetResponseDTO(
                        packageExperience.getPackageExperienceId(),
                        packageExperience.getQuantity(),
                        packageExperience.getPackages().getPackageId(),
                        packageExperience.getExperience().getExperienceId()
                ))
                .toList();
    }

    @Override
    public PaginatedPackageExperienceGetResponseDTO getAllPackageExperienceDetailsPaginated(Long packageId, Long experienceId, Pageable pageable) {
        Page<PackageExperience> packageExperiencesPage;
        if (packageId != null && experienceId != null) {
            packageExperiencesPage = packageExperienceRepo.findByPackages_PackageIdAndExperience_ExperienceId(packageId, experienceId, pageable);
        } else if (packageId != null) {
            packageExperiencesPage = packageExperienceRepo.findByPackages_PackageId(packageId, pageable);
        } else if (experienceId != null) {
            packageExperiencesPage = packageExperienceRepo.findByExperience_ExperienceId(experienceId, pageable);
        } else {
            packageExperiencesPage = packageExperienceRepo.findAll(pageable);
        }
        if (packageExperiencesPage.hasContent()) {
            List<PackageExperienceGetResponseDTO> packageExperienceGetResponseDTOS = packageExperiencesPage.getContent().stream()
                    .map(packageExperience -> modelMapper.map(packageExperience, PackageExperienceGetResponseDTO.class))
                    .toList();
            return new PaginatedPackageExperienceGetResponseDTO(
                    packageExperienceGetResponseDTOS,
                    packageExperiencesPage.getTotalElements()
            );
        } else {
            throw new RuntimeException("No Package Experiences Found");
        }
    }

    @Override
    public PackageExperienceDTO updatePackageExperienceDetails(
            PackageExperienceUpdateRequestDTO packageExperienceUpdateRequestDTO,
            Long packageExperienceId
    ) {
        //Get package experience by Package Experience ID
        if (packageExperienceRepo.existsById(packageExperienceId)) {
            // Get Package Experience by Package Experience ID and Map Package Experience Entity to Package Experience DTO
            PackageExperience existingPackageExperience = packageExperienceRepo.getReferenceById(packageExperienceId);
            // Update Package Experience quantity
            if (packageExperienceUpdateRequestDTO.getQuantity() != 0) {
                existingPackageExperience.setQuantity(packageExperienceUpdateRequestDTO.getQuantity());
            }
            // Save the updated Package Experience
            packageExperienceRepo.save(existingPackageExperience);
            return modelMapper.map(existingPackageExperience, PackageExperienceDTO.class);
        } else {
            throw new RuntimeException("Experience Package Not Found");
        }
    }

    @Override
    public String deletePackageExperienceById(Long packageExperienceId) {
        // Get Package Experience by Package Experience ID
        if (packageExperienceRepo.existsById(packageExperienceId)) {
            String response = packageExperienceRepo.getReferenceById(packageExperienceId).getPackageExperienceId() + " Deleted!";
            //delete experience
            packageExperienceRepo.deleteById(packageExperienceId);
            return response;
        } else {
            throw new RuntimeException("Experience Package Not Found");
        }
    }

}
