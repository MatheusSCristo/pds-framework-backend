package com.neo_educ.backend.core.repository;

import com.neo_educ.backend.core.model.AbstractModel;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.List;

@NoRepositoryBean
public interface AbstractClientRepository<T extends AbstractModel> extends AbstractRepository<T> {

    List<T> findAllByOwnerId(Long ownerId);
}