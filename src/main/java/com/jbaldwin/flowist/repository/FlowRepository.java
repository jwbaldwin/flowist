package com.jbaldwin.flowist.repository;

import com.jbaldwin.flowist.domain.BaseFlow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlowRepository extends CrudRepository<BaseFlow, UUID> {
}
