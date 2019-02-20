package com.jbaldwin.novu.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.UUID;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BaseFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String activity;
    private String title;
    private String content;
    private ArrayList<String> tags;
    private String created;
    private FlowStatus flowStatus;
}
