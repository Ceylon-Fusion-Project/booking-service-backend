package com.ceylone_fusion.booking_service.dto.paginated;

import com.ceylone_fusion.booking_service.dto.response.BookingGetResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaginatedBookingGetResponseDTO {

    private List<BookingGetResponseDTO> bookingGetResponseDTOS;
    private Long total;

}
