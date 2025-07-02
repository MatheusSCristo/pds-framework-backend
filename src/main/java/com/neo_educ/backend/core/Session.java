package com.neo_educ.backend.core;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class Session {

    private LocalDateTime date;
    private boolean status;

    public abstract Object schedule();
    public abstract Object cancel();

}
