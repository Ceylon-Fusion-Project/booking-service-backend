package com.ceylone_fusion.booking_service.service;

import com.ceylone_fusion.booking_service.dto.PackageDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.PackageGetResponseDTO;

import java.util.List;

public interface PackageService {

    PackageDTO savePackage(PackageSaveRequestDTO packageSaveRequestDTO);

    List<PackageGetResponseDTO> getAllPackage();

    List<PackageGetResponseDTO> getPackageById(Long packageId);

    PackageDTO updatePackageDetails(PackageUpdateRequestDTO packageUpdateRequestDTO, Long packageId);

    String deletePackageById(Long packageId);

    List<PackageGetResponseDTO> getAllPackageDetails(String packageName, boolean isPredefined, Double minPrice, Double maxPrice);
}
