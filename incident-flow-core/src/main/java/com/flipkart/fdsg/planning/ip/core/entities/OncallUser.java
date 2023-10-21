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
@Table(name = "oncall_user")
@NamedQueries({
        @NamedQuery(name = "OncallUser.findAll", query = "SELECT ou FROM OncallUser ou"),
        @NamedQuery(name = "OncallUser.findNextUserIdById",
                query = "SELECT COALESCE(MIN(ou.userId), MAX(ou.userId)) FROM OncallUser ou WHERE ou.id > :currentId")

})
public class OncallUser extends BaseEntity {
    @Id
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "name")
    private String name;
}

