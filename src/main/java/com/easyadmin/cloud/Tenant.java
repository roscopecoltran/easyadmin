package com.easyadmin.cloud;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by gongxinyi on 2017-09-09.
 */
@Data
@Entity(value = "_tenant", noClassnameStored = true)
public class Tenant {
    @Id
    private String id;
    private String connectionStr;
    private String dbName;

    private String[] users;

    private static InheritableThreadLocal<Tenant> threadLocal = new InheritableThreadLocal<Tenant>() {
        @Override
        protected Tenant initialValue() {
            return new Tenant();
        }
    };

    public static Tenant get() {
        return threadLocal.get();
    }

    public static void set(Tenant tenant) {
        threadLocal.set(tenant);
    }

}
