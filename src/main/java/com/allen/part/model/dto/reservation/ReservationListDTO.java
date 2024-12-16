package com.allen.part.model.dto.reservation;

import com.allen.part.model.entity.Reservation;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReservationListDTO extends Reservation {
    private String userName;
    private String ownerName;
    private String parkingSpacePhoto;
}
