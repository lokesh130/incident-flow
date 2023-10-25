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
@Table(name = "alerts")
@NamedQueries({
        @NamedQuery(
                name = "Alert.find.all",
                query = "SELECT a FROM Alert a"
        ),
        @NamedQuery(
                name = "Alert.find.by.id",
                query = "SELECT a FROM Alert a WHERE a.id = :alertId"
        )
})
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String message;

    @Enumerated(EnumType.STRING)
    private Priority priority;
}
