package basecomponent.query;

import lombok.extern.log4j.Log4j2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public enum Operator {

    EQUAL {
        @Override
        public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest request, Predicate predicate) {
            Object value = request.getFieldType().parse(request.getValue().toString());
            Expression<?> key = root.get(request.getKey());
            return criteriaBuilder.and(criteriaBuilder.equal(key, value), predicate);
        }
    },

    NOT_EQUAL {
        @Override
        public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest request, Predicate predicate) {
            Object value = request.getFieldType().parse(request.getValue().toString());
            Expression<?> key = root.get(request.getKey());
            return criteriaBuilder.and(criteriaBuilder.notEqual(key, value), predicate);
        }
    },

    LIKE {
        @Override
        public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest request, Predicate predicate) {
            Expression<String> key = root.get(request.getKey());
            return criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(key), "%" + request.getValue().toString().toUpperCase() + "%"), predicate);
        }
    },

    IN {
        @Override
        public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest request, Predicate predicate) {
            List<Object> values = request.getValues();
            CriteriaBuilder.In<Object> inClause = criteriaBuilder.in(root.get(request.getKey()));
            for (Object value : values) {
                inClause.value(request.getFieldType().parse(value.toString()));
            }
            return criteriaBuilder.and(inClause, predicate);
        }
    },

    BETWEEN {
        @Override
        public <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest request, Predicate predicate) {
            Object value = request.getFieldType().parse(request.getValue().toString());
            Object valueTo = request.getFieldType().parse(request.getValue().toString());
            if (request.getFieldType() == FieldType.DATE) {
                LocalDateTime startDate = (LocalDateTime) value;
                LocalDateTime endDate = (LocalDateTime) valueTo;
                Expression<LocalDateTime> key = root.get(request.getKey());
                return criteriaBuilder.and(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(key, startDate), criteriaBuilder.lessThanOrEqualTo(key, endDate)), predicate);
            }

            if (request.getFieldType() != FieldType.CHAR && request.getFieldType() != FieldType.BOOLEAN && request.getFieldType() != FieldType.STRING) {
                Number start = (Number) value;
                Number end = (Number) valueTo;
                Expression<Number> key = root.get(request.getKey());
                return criteriaBuilder.and(criteriaBuilder.and(criteriaBuilder.ge(key, start), criteriaBuilder.le(key, end)), predicate);
            }

            log.debug("Cannot use BETWEEN for field type {}.", request.getFieldType());
            return predicate;
        }
    };

    public abstract <T> Predicate build(Root<T> root, CriteriaBuilder criteriaBuilder, FilterRequest request, Predicate predicate);

}
