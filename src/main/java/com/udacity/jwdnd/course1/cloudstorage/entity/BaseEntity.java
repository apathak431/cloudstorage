package com.udacity.jwdnd.course1.cloudstorage.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @Column(insertable = false, updatable = false)
    @GenericGenerator(name="native", strategy="native")
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
