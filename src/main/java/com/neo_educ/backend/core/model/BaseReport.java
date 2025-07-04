package com.neo_educ.backend.core.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseReport {

    private LocalDateTime issueDate;

    public abstract Object generateReport();

}
