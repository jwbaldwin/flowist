package com.jbaldwin.flowist.repository;

import com.jbaldwin.flowist.domain.Flow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlowRepository extends JpaRepository<Flow, UUID> {

    @Override
    @Query("select f from Flow f where f.owner = ?#{ principal?.username }")
    List<Flow> findAll();

    @Override
    @PostAuthorize("returnObject.owner == principal.username")
    Optional<Flow> findById(UUID id);

    @Override
    @PreFilter("#entity.userId == principal.username")
    <S extends Flow> S save(S flow);

    @Override
    void deleteById(UUID id);
}
