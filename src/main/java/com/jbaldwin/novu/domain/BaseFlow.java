package com.jbaldwin.novu.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BaseFlow {
    private String id;
    private String type;
    private String title;
    private String body;
    private String extraInfo;
    private FlowStatus flowStatus;
}
