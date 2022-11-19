package product.kafka.topics;

public enum ProductTopics {

    GET_ALL("product_get_all"),
    GET_BY_ID("product_get_by_id"),
    ADD_NEW("product_add_new"),
    UPDATE("product_update"),
    DELETE("product_delete");

    public final String topic;

    ProductTopics(String topic) {
        this.topic = topic;
    }

}
