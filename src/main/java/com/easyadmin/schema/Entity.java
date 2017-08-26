package com.easyadmin.schema;

import com.easyadmin.schema.field.Field;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

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
    private List<Field> fields;
    @Builder
    public Entity(String id, String label, String name) {
        this.id = id;
        this.label = label;
        this.name = name;
    }
}
