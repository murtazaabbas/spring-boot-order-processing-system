package com.melitaltd.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class QueueObjectWrapper implements Serializable {
    private String traceId;
    private Object object;
}
