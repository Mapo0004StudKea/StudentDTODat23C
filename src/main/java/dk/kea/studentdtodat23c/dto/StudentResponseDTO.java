package dk.kea.studentdtodat23c.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record StudentResponseDTO(Long id, String name, LocalDate birthDate, LocalTime bornTime) {
}
