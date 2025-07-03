package com.neo_educ.backend.core.service;

import java.util.Map;

public abstract class MaterialGeneratorServiceAbstract {
    public abstract String generateMainActivity(Map<String, Object> infos);
    public abstract String generateReport(Map<String, Object> infos);
    public abstract String generateAncillaryContent(Map<String, Object> infos);

    protected boolean validateInfos(Map<String, Object> infos) {
        return infos != null && !infos.isEmpty();
    }
}
