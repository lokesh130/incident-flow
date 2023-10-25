package com.flipkart.fdsg.planning.ip.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message_configuration")
@NamedQueries({
        @NamedQuery(name = "MessageConfiguration.findAll", query = "SELECT mc FROM MessageConfiguration mc"),
        @NamedQuery(name = "MessageConfiguration.findById", query = "SELECT mc FROM MessageConfiguration mc WHERE mc.id = :id")
})
public class MessageConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_regex")
    private String subjectRegex;

    @Column(name = "frequency")
    private Integer frequency;
}
