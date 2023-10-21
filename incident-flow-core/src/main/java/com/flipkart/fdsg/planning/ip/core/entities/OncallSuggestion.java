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
@Table(name = "oncall_suggestions")
@NamedQueries({
        @NamedQuery(name = "OncallSuggestions.findAll", query = "SELECT os FROM OncallSuggestion os"),
        @NamedQuery(name = "OncallSuggestions.findByOncallTrackerId",
                query = "SELECT os FROM OncallSuggestion os WHERE os.oncallTracker.id = :oncallTrackerId")

})
public class OncallSuggestion extends BaseEntity {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "oncall_tracker_id")
    private OncallTracker oncallTracker;

    @Column(name = "suggestion")
    private String suggestion;
}
