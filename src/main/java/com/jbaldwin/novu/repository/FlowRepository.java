package com.jbaldwin.novu.repository;

import com.jbaldwin.novu.domain.BaseFlow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlowRepository extends CrudRepository<BaseFlow, UUID> {
}
