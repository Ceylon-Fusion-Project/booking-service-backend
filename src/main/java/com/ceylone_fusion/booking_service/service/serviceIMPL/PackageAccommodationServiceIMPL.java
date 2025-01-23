package com.ceylone_fusion.booking_service.service.serviceIMPL;

import com.ceylone_fusion.booking_service.dto.PackageAccommodationDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageAccommodationSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageAccommodationUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.PackageAccommodationGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.PackageAccommodation;
import com.ceylone_fusion.booking_service.repo.AccommodationRepo;
import com.ceylone_fusion.booking_service.repo.PackageAccommodationRepo;
import com.ceylone_fusion.booking_service.repo.PackageRepo;
import com.ceylone_fusion.booking_service.service.PackageAccommodationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageAccommodationServiceIMPL implements PackageAccommodationService {

    @Autowired
    private PackageAccommodationRepo packageAccommodationRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PackageRepo packageRepo;

    @Autowired
    private AccommodationRepo accommodationRepo;

    @Override
    public PackageAccommodationDTO savePackageAccommodation(PackageAccommodationSaveRequestDTO packageAccommodationSaveRequestDTO) {
        Long packageId = packageAccommodationSaveRequestDTO.getPackageId();
        Long accommodationId = packageAccommodationSaveRequestDTO.getAccommodationId();

        // Validate if the Package and Accommodation exist
        if (packageRepo.existsById(packageId) && accommodationRepo.existsById(accommodationId)) {
            // Create a new PackageAccommodation instance
            PackageAccommodation packageAccommodation = new PackageAccommodation(
                    null, // ID is auto-generated
                    packageAccommodationSaveRequestDTO.getQuantity(),
                    packageRepo.findPackageByPackageId(packageId), // Find the Package entity
                    accommodationRepo.findAccommodationByAccommodationIdEquals(accommodationId) // Find the Accommodation entity
            );

            // Save the new PackageAccommodation
            packageAccommodationRepo.save(packageAccommodation);

            // Map the entity to a DTO and return it
            return modelMapper.map(packageAccommodation, PackageAccommodationDTO.class);
        } else {
            throw new RuntimeException("Package or Accommodation Not Found");
        }
    }

    @Override
    public List<PackageAccommodationSaveRequestDTO> getAllPackageAccommodations() {
        List<PackageAccommodation> getAllPackageAccommodations = packageAccommodationRepo.findAll();
        if(!getAllPackageAccommodations.isEmpty()){
            return modelMapper.map(getAllPackageAccommodations, new TypeToken<List<PackageAccommodationGetResponseDTO>>(){}.getType());
        }
        else{
            throw new RuntimeException("No Accommodation Package Found");
        }
    }

    @Override
    public List<PackageAccommodationGetResponseDTO> getPackageAccommodationById(Long packageAccommodationId) {
        List<PackageAccommodation> packageAccommodations = packageAccommodationRepo.findAllByPackageAccommodationIdEquals(packageAccommodationId);
        if(!packageAccommodations.isEmpty()) {
            return modelMapper.map(packageAccommodations, new TypeToken<List<PackageAccommodationGetResponseDTO>>(){}.getType());
        }
        else {
            throw new RuntimeException("No Accommodation Package Found");
        }
    }

    @Override
    public List<PackageAccommodationGetResponseDTO> getAllPackageAccommodationDetails(
            Long packageId,
            Long accommodationId
    ) {
        List<PackageAccommodation> packageAccommodations;
        if (packageId != null && accommodationId != null) {
            packageAccommodations = packageAccommodationRepo.findByPackages_PackageIdAndAccommodation_AccommodationId(packageId, accommodationId);
        } else if (packageId != null) {
            packageAccommodations = packageAccommodationRepo.findByPackages_PackageId(packageId);
        } else if (accommodationId != null) {
            packageAccommodations = packageAccommodationRepo.findByAccommodation_AccommodationId(accommodationId);
        } else {
            throw new RuntimeException("Accommodation Package Not Found");
        }

        // Map entities to DTOs
        return packageAccommodations.stream()
                .map(packageAccommodation -> new PackageAccommodationGetResponseDTO(
                        packageAccommodation.getPackageAccommodationId(),
                        packageAccommodation.getQuantity(),
                        packageAccommodation.getPackages().getPackageId(),
                        packageAccommodation.getAccommodation().getAccommodationId()
                ))
                .toList();

    }

    @Override
    public PackageAccommodationDTO updatePackageAccommodationDetails(
            PackageAccommodationUpdateRequestDTO packageAccommodationUpdateRequestDTO,
            Long packageAccommodationId
    ) {
        //Get package accommodation by Package Accommodation ID
        if (packageAccommodationRepo.existsById(packageAccommodationId)) {
            // Get Package Accommodation by Package Accommodation ID and Map Package Accommodation Entity to Package Accommodation DTO
            PackageAccommodation existingPackageAccommodation = packageAccommodationRepo.getReferenceById(packageAccommodationId);

            // Update Package Accommodation quantity
            if (packageAccommodationUpdateRequestDTO.getQuantity() != 0) {
                existingPackageAccommodation.setQuantity(packageAccommodationUpdateRequestDTO.getQuantity());
            }

            // Save the updated Package Accommodation
            packageAccommodationRepo.save(existingPackageAccommodation);

            return modelMapper.map(existingPackageAccommodation, PackageAccommodationDTO.class);
        } else {
            throw new RuntimeException("Accommodation Package Not Found");
        }
    }

    @Override
    public String deletePackageAccommodationById(Long packageAccommodationId) {
        // Get Package Accommodation by Package Accommodation ID
        if (packageAccommodationRepo.existsById(packageAccommodationId)) {
            String response = packageAccommodationRepo.getReferenceById(packageAccommodationId).getPackageAccommodationId() + " Deleted!";

            //delete accommodation
            packageAccommodationRepo.deleteById(packageAccommodationId);
            return response;
        } else {
            throw new RuntimeException("Accommodation Package Not Found");
        }
    }


}
