package com.easyadmin.schema;

import lombok.*;
import org.bson.types.ObjectId;

/**
 * Created by gongxinyi on 2017-08-23.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode()
@NoArgsConstructor
public final class Entity {
    private String id;
    private String label;
    private String name;

    @Builder
    public Entity(String id, String label, String name) {
        this.id = id;
        this.label = label;
        this.name = name;
    }
}
