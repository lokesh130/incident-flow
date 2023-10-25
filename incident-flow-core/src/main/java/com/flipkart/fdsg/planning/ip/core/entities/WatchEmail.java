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
@Table(name = "watch_emails")
@NamedQueries({
        @NamedQuery(
                name = "WatchEmail.findAll",
                query = "SELECT w FROM WatchEmail w"
        ),
        @NamedQuery(
                name = "WatchEmail.deleteByEmail",
                query = "DELETE FROM WatchEmail w WHERE w.email = :email"
        ),
        @NamedQuery(
                name = "WatchEmail.findByEmail",
                query = "SELECT w FROM WatchEmail w WHERE w.email = :email"
        )
})
public class WatchEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String email;
}
