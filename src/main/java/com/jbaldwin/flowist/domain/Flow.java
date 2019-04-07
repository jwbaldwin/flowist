package com.jbaldwin.flowist.domain;

import com.jbaldwin.flowist.model.AuditModel;
import java.util.ArrayList;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flows")
public class Flow extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;
    private String owner;

    @Lob
    private String content;
    private String activity;
    private String title;
    private ArrayList<String> tags;
    private FlowStatus flowStatus;
}
