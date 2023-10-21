package com.flipkart.fdsg.planning.ip.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "active_oncall_group")
@NamedQueries({
        @NamedQuery(name = "ActiveOncallGroup.findAll", query = "SELECT aog FROM ActiveOncallGroup aog"),
        @NamedQuery(name = "ActiveOncallGroup.findLatestActiveGroup",
                query = "SELECT aog FROM ActiveOncallGroup aog " +
                        "WHERE aog.startDate = (SELECT MAX(aogInner.startDate) FROM ActiveOncallGroup aogInner)")

})
public class ActiveOncallGroup extends BaseEntity {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "primary_user_id")
    private OncallUser primaryUser;

    @ManyToOne
    @JoinColumn(name = "secondary_user_id")
    private OncallUser secondaryUser;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;
}
