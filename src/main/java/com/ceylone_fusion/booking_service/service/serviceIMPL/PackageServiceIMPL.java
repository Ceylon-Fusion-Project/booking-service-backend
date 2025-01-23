package com.ceylone_fusion.booking_service.service.serviceIMPL;

import com.ceylone_fusion.booking_service.dto.PackageDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageSaveRequestDTO;
import com.ceylone_fusion.booking_service.entity.Package;
import com.ceylone_fusion.booking_service.repo.PackageRepo;
import com.ceylone_fusion.booking_service.service.PackageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

            // Save the package to the repository
            packageRepo.save(newPackage);

            // Return the saved Package as a DTO
            return modelMapper.map(newPackage, PackageDTO.class);
        } else {
            throw new RuntimeException("Package with name " + packageSaveRequestDTO.getPackageName() + " already exists");
        }

    }
}
