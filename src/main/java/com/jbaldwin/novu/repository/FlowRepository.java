package com.jbaldwin.novu.repository;

import com.jbaldwin.novu.domain.BaseFlow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowRepository extends CrudRepository<BaseFlow, Long> {
}
