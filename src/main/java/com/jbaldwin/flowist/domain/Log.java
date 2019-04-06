package com.jbaldwin.flowist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jbaldwin.flowist.model.AuditModel;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "logs")
public class Log extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Lob
    private String content;
    private String type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flow_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Flow flow;
}
