package com.ceylone_fusion.booking_service.service;

import com.ceylone_fusion.booking_service.dto.PackageDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageSaveRequestDTO;

public interface PackageService {

    PackageDTO savePackage(PackageSaveRequestDTO packageSaveRequestDTO);
}
