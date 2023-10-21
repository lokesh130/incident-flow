package com.flipkart.fdsg.planning.ip.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base entity. Use this <b>only</b> if you need <code>created_at</code> and <code>updated_at</code> fields.
 * <p>
 * This class is used in reflective access. So, don't change path.
 */
@MappedSuperclass
@Data
@TypeDef(name = "json", typeClass = JsonStringType.class)
public abstract class BaseEntity implements Serializable {
    @JsonIgnore
    @Column(name = "created_at", insertable = false, updatable = false)
    protected LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at", insertable = false, updatable = false)
    protected LocalDateTime updatedAt;
}

