package com.livestream.Util;

import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.Predicate;

/**
 * Specification Builder - Tự động build WHERE clause từ Map params
 * Không cần QueryDSL hay Q-classes, dùng JPA Specification
 */
@Component
public class SpecificationBuilder {

    /**
     * Build Specification từ Map<String, Object> params
     * Tự động tìm field và build LIKE condition
     *
     * @param params      Map chứa {fieldName: value, ...}
     * @param entityClass Entity class (e.g., Category.class)
     * @return Specification để dùng trong findAll(spec)
     */
    public <T> Specification<T> buildPredicate(Map<String, Object> params, Class<T> entityClass) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (params == null || params.isEmpty()) {
                return predicate;
            }

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (value == null || value.toString().trim().isEmpty()) {
                    continue; // Skip null hoặc empty values
                }

                try {
                    var fieldPath = root.get(key);
                    Class<?> fieldType = fieldPath.getJavaType();

                    // Check kiểu dữ liệu và build predicate tương ứng
                    if (fieldType.equals(String.class)) {
                        // String → Dùng LIKE với LOWER (case-insensitive)
                        predicate = criteriaBuilder.and(
                                predicate,
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(fieldPath.as(String.class)),
                                        "%" + value.toString().toLowerCase() + "%"));
                    } else if (Number.class.isAssignableFrom(fieldType) || fieldType.isPrimitive()) {
                        // Number (int, long, double, ...) → Dùng EQUALS
                        Object convertedValue = convertToNumber(value, fieldType);
                        predicate = criteriaBuilder.and(
                                predicate,
                                criteriaBuilder.equal(fieldPath, convertedValue));
                    } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
                        // Boolean → Dùng EQUALS
                        Boolean boolValue = Boolean.parseBoolean(value.toString());
                        predicate = criteriaBuilder.and(
                                predicate,
                                criteriaBuilder.equal(fieldPath, boolValue));
                    } else {
                        // Các kiểu khác → Dùng EQUALS
                        predicate = criteriaBuilder.and(
                                predicate,
                                criteriaBuilder.equal(fieldPath, value));
                    }
                } catch (Exception e) {
                    // Field không tồn tại hoặc lỗi, skip
                    System.err.println("Không thể build predicate cho field: " + key + ". Error: " + e.getMessage());
                    continue;
                }
            }

            return predicate;
        };
    }

    /**
     * Convert String value sang Number type tương ứng
     */
    private Object convertToNumber(Object value, Class<?> targetType) {
        String strValue = value.toString();

        if (targetType.equals(Integer.class) || targetType.equals(int.class)) {
            return Integer.parseInt(strValue);
        } else if (targetType.equals(Long.class) || targetType.equals(long.class)) {
            return Long.parseLong(strValue);
        } else if (targetType.equals(Double.class) || targetType.equals(double.class)) {
            return Double.parseDouble(strValue);
        } else if (targetType.equals(Float.class) || targetType.equals(float.class)) {
            return Float.parseFloat(strValue);
        } else if (targetType.equals(Short.class) || targetType.equals(short.class)) {
            return Short.parseShort(strValue);
        } else if (targetType.equals(Byte.class) || targetType.equals(byte.class)) {
            return Byte.parseByte(strValue);
        }
        return value;
    }
}
