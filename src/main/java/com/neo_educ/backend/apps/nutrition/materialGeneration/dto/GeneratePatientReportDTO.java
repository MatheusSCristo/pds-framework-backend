package com.neo_educ.backend.apps.nutrition.materialGeneration.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * DTO para solicitar a geração de um relatório de progresso do paciente.
 *
 * @param patientId O ID do paciente sobre o qual o relatório será gerado.
 * @param startDate A data de início do período do relatório.
 * @param endDate A data de fim do período do relatório.
 */
public record GeneratePatientReportDTO(
    @NotNull(message = "O ID do paciente não pode ser nulo.")
    Long patientId,

    @NotNull(message = "A data de início não pode ser nula.")
    LocalDate startDate,

    @NotNull(message = "A data de fim não pode ser nula.")
    LocalDate endDate
) {}