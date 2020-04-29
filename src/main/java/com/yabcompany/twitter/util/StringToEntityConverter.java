package com.yabcompany.twitter.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Custom converter
 * String to Object and Object to String
 */
public class StringToEntityConverter implements GenericConverter {

    @Autowired
    private EntityManager entityManager;

    private final Class<?> clazz;

    public StringToEntityConverter(Class<?> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> types = new HashSet<>();
        types.add(new ConvertiblePair(String.class, this.clazz));
        types.add(new ConvertiblePair(this.clazz, String.class));
        return types;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (String.class.equals(sourceType.getType())) {
            if (!StringUtils.hasText((String) source) || ((String) source).equals("NONE")) {
                return null;
            }
            Long id = Long.parseLong((String) source);
            return this.entityManager.find(this.clazz, id);
        } else if (this.clazz.equals(sourceType.getType())) {
            if (source == null) {
                return "";
            } else {
                try {
                    Method method = this.clazz.getMethod("getId");
                    return method.invoke(source, null).toString();
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    throw new IllegalArgumentException("Cannot convert " + source + " into a suitable type: can't call getId method of " + this.clazz.getName() + "!");
                }
            }
        }
        throw new IllegalArgumentException("Cannot convert " + source + " into a suitable type!");
    }
}
