package com.ceylone_fusion.booking_service.service.serviceIMPL;

import com.ceylone_fusion.booking_service.dto.PackageDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedPackageGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.AccommodationGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.response.PackageGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.Accommodation;
import com.ceylone_fusion.booking_service.entity.Package;
import com.ceylone_fusion.booking_service.repo.PackageRepo;
import com.ceylone_fusion.booking_service.service.PackageService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageServiceIMPL implements PackageService {

    @Autowired
    private PackageRepo packageRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PackageDTO savePackage(PackageSaveRequestDTO packageSaveRequestDTO) {
        if (!packageRepo.existsByPackageNameIgnoreCase(packageSaveRequestDTO.getPackageName())) {
            // Map the PackageSaveRequestDTO to the Package entity
            Package newPackage = modelMapper.map(packageSaveRequestDTO, Package.class);

            // Set isPredefined to true
            newPackage.setPredefined(true);

            // Save the package to the repository
            packageRepo.save(newPackage);
            // Return the saved Package as a DTO
            return modelMapper.map(newPackage, PackageDTO.class);
        } else {
            throw new RuntimeException("Package with name " + packageSaveRequestDTO.getPackageName() + " already exists");
        }
    }

    @Override
    public List<PackageGetResponseDTO> getAllPackage() {
        List<Package> getAllPackages = packageRepo.findAll();
        if(!getAllPackages.isEmpty()){
            return modelMapper.map(getAllPackages, new TypeToken<List<PackageGetResponseDTO>>(){}.getType());
        }
        else{
            throw new RuntimeException("No Packages Found");
        }
    }

    @Override
    public PaginatedPackageGetResponseDTO getAllPackagesPaginated(Pageable pageable) {
        // Fetch paginated package
        Page<Package> packagesPage = packageRepo.findAll(pageable);
        if (packagesPage.hasContent()) {
            // Convert Page<Package> to List<PackageGetResponseDTO>
            List<PackageGetResponseDTO> packageGetResponseDTOS = packagesPage.getContent().stream()
                    .map(packages -> modelMapper.map(packages, PackageGetResponseDTO.class))
                    .toList();
            // Return paginated response
            return new PaginatedPackageGetResponseDTO(
                    packageGetResponseDTOS,
                    packagesPage.getTotalElements()
            );
        } else {
            throw new RuntimeException("No Package s Found");
        }
    }

    @Override
    public List<PackageGetResponseDTO> getPackageById(Long packageId) {
        List<Package> packages = packageRepo.findAllByPackageIdEquals(packageId);
        if(!packages.isEmpty()) {
            return modelMapper.map(packages, new TypeToken<List<PackageGetResponseDTO>>(){}.getType());
        }
        else {
            throw new RuntimeException("No Package Found");
        }
    }

    @Override
    public List<PackageGetResponseDTO> getAllPackageDetails(
            String packageName,
            boolean isPredefined,
            Double minPrice,
            Double maxPrice
    ) {
        List<Package> packages;
        // Apply filters based on input parameters
        if (packageName != null && !packageName.isEmpty()) {
            packages = packageRepo.findByPackageName(packageName);
        } else if (minPrice != null && maxPrice != null) {
            packages = packageRepo.findByPricePerDayBetween(minPrice, maxPrice);
        } else if (minPrice != null) {
            packages = packageRepo.findByPricePerDayGreaterThanEqual(minPrice);
        } else if (maxPrice != null) {
            packages = packageRepo.findByPricePerDayLessThanEqual(maxPrice);
        } else {
            packages = packageRepo.findAll(); // Retrieve all packages if no filters are applied
        }
        // Handle empty or null results
        if (packages == null || packages.isEmpty()) {
            throw new RuntimeException("No packages found matching the criteria.");
        }
        // Map Package entities to DTOs
        return modelMapper.map(packages, new TypeToken<List<PackageGetResponseDTO>>() {}.getType());
    }

    @Override
    public List<PackageGetResponseDTO> getPackageByCode(String packageCode) {
        List<Package> packages = packageRepo.findAllByPackageCodeEquals(packageCode);
        if(!packages.isEmpty()) {
            return modelMapper.map(packages, new TypeToken<List<PackageGetResponseDTO>>(){}.getType());
        }
        else {
            throw new RuntimeException("No Package Found");
        }
    }

    @Override
    public PaginatedPackageGetResponseDTO getAllPackageDetailsPaginated(String packageName, boolean isPredefined, Double minPrice, Double maxPrice, Pageable pageable) {
        Page<Package> packagesPage;
        if (packageName != null && !packageName.isEmpty()) {
            packagesPage = packageRepo.findByPackageName(packageName, pageable);
        } else if (minPrice != null && maxPrice != null) {
            packagesPage = packageRepo.findByPricePerDayBetween(minPrice, maxPrice, pageable);
        } else if (minPrice != null) {
            packagesPage = packageRepo.findByPricePerDayGreaterThanEqual(minPrice, pageable);
        }else if (maxPrice != null) {
            packagesPage = packageRepo.findByPricePerDayLessThanEqual(maxPrice, pageable);
        } else {
            packagesPage = packageRepo.findAll(pageable);
        }
        if (packagesPage.hasContent()) {
            List<PackageGetResponseDTO> packageGetResponseDTOS = packagesPage.getContent().stream()
                    .map(packages -> modelMapper.map(packages, PackageGetResponseDTO.class))
                    .toList();
            return new PaginatedPackageGetResponseDTO(
                    packageGetResponseDTOS,
                    packagesPage.getTotalElements()
            );
        } else {
            throw new RuntimeException("No Packages Found");
        }
    }

    @Override
    public PackageDTO updatePackageDetails(PackageUpdateRequestDTO packageUpdateRequestDTO, Long packageId) {
        //Get package by Package ID
        if (packageRepo.existsById(packageId)) {
            // Get Package by Package ID and Map Package Entity to Package DTO
            Package existingPackage = packageRepo.getReferenceById(packageId);
            // Update Package name
            if (packageUpdateRequestDTO.getPackageName() != null) {
                existingPackage.setPackageName(packageUpdateRequestDTO.getPackageName());
            }
            // Update Package Description
            if (packageUpdateRequestDTO.getDescription() != null) {
                existingPackage.setDescription(packageUpdateRequestDTO.getDescription());
            }
            // Update Package Price
            if (packageUpdateRequestDTO.getPricePerDay() != null) {
                existingPackage.setPricePerDay(packageUpdateRequestDTO.getPricePerDay());
            }
            // Update Package Is Predefined
//            if (packageUpdateRequestDTO.isPredefined()) {
//                existingPackage.setPredefined(true);
//            }

            // Set isPredefined to true
            existingPackage.setPredefined(true);

            // Save the updated Package
            packageRepo.save(existingPackage);
            return modelMapper.map(existingPackage, PackageDTO.class);
        } else {
            throw new RuntimeException("Package Not Found");
        }
    }

    @Override
    public String deletePackageById(Long packageId) {
        // Get Package by Package ID
        if (packageRepo.existsById(packageId)) {
            String response = packageRepo.getReferenceById(packageId).getPackageName() + " Deleted!";
            //delete package
            packageRepo.deleteById(packageId);
            return response;
        } else {
            throw new RuntimeException("Package Not Found");
        }
    }

}
