package com.flipkart.fdsg.planning.ip.core.helpers;

import com.flipkart.fdsg.planning.ip.core.entities.BaseEntity;
import lombok.experimental.UtilityClass;
import org.reflections.Reflections;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public final class EntityHelper {
    private static Reflections reflections = new Reflections(getEntityPackage());

    /**
     * List of all Hibernate entity classes, exception junction tables mapped using {@link JoinTable} annotation
     */
    public static Set<Class<?>> getEntityClasses() {
        Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);
        if (entityClasses.isEmpty()) {
            throw new IllegalStateException("Couldn't get any entities through Reflections. Either your codebase does not have any database entities (in that case you add one) or Reflections is configured incorrectly.");
        }
        return entityClasses;
    }

    /**
     * List of all database table names
     */
    public static Set<String> getEntityTableNames() {
        Set<Class<?>> entityClasses = getEntityClasses();
        Set<String> tableNames = entityClasses.stream().map(e -> e.getAnnotation(Table.class).name()).collect(Collectors.toSet());
        for (Class entityClass : entityClasses) {
            for (Field field : entityClass.getDeclaredFields()) {
                if (!field.isAnnotationPresent(JoinTable.class))
                    continue;
                JoinTable joinTable = field.getAnnotation(JoinTable.class);
                tableNames.add(joinTable.name());
            }
        }
        return tableNames;
    }

    /**
     * Package that contains all the Hibernate entities
     */
    public static String getEntityPackage() {
        return BaseEntity.class.getPackage().getName();
    }

}
