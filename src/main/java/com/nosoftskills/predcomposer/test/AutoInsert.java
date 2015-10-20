package com.nosoftskills.predcomposer.test;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Ivan St. Ivanov
 */
@Entity
public class AutoInsert implements Serializable {

    private static final long serialVersionUID = -8561755121611760142L;

    @Id
    private Integer id;

    public AutoInsert() {
    }

    public AutoInsert(Integer id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
