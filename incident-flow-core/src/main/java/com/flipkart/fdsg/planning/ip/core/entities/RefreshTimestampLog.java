package com.flipkart.fdsg.planning.ip.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refresh_timestamp_log")
@NamedQuery(
    name = "RefreshTimestampLog.findLatest",
    query = "SELECT rtl FROM RefreshTimestampLog rtl ORDER BY rtl.createdAt DESC"
)
public class RefreshTimestampLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refresh_timestamp")
    private Long refreshTimestamp;
}
