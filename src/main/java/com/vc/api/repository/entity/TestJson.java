package com.vc.api.repository.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestJson implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String json;

    private static final long serialVersionUID = 1L;
}