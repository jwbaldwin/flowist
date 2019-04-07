package com.jbaldwin.flowist.repository;

import com.jbaldwin.flowist.domain.Log;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, UUID> {

    List<Log> findByFlowId(UUID flowId);

    Optional<Log> findByIdAndFlowId(UUID id, UUID flowId);
}
