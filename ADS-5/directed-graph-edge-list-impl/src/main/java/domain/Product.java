package domain;

public class Product {
        private int id;
        private String name;

        public Product(int id,String name) {
            this.id = id;
            this.name = name;
        }

        public Product(Order order) {
            this.id = order.getProductId();
            this.name = order.getProduct();
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

    @Override
    public String toString() {
        return String.format("%-45s|%-45s|", getId(),getName());
    }
}

