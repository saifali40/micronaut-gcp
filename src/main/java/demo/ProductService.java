package demo;


import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Singleton
final class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private static final Map<String, Supplier<Product>> products = new ConcurrentHashMap<>();

    static {

        for (int i = 0; i < 1000 ; i++) {
            products.put("PROD-001"+i, createProduct("PROD-001"+i, "Micronaut in Action", 29.99, 120));
            products.put("PROD-002"+i, createProduct("PROD-002"+i, "Netty in Action", 31.22, 190));
            products.put("PROD-003"+i, createProduct("PROD-003"+i, "Effective Java, 3rd edition", 31.22, 600));
            products.put("PROD-004"+i, createProduct("PROD-004"+i, "Clean Code", 31.22, 1200));
        }

    }



    private static List<Product> getProducts(){
        List<Product> r = new ArrayList<>();
        for (int i = 0; i < 100000 ; i++) {
            Product p = new Product("PROD-"+i, "name"+i, BigDecimal.valueOf(i));
            r.add(p);
        }
        return r;
    }

    public Maybe<Product> findProductById(final String id) {
        return Maybe.just(id)
                .subscribeOn(Schedulers.io())
                .map(it -> products.getOrDefault(it, () -> null).get());
    }


//    public Flowable<List<Product>> findProduct() {
//        return Flowable.just(getProducts())
//                .subscribeOn(Schedulers.io());
//    }

    public Flowable<List<List<Product>>> findProduct() {
        return Flowable
                .just(getProducts())
                .delay(1, TimeUnit.SECONDS)
                .limit(1000)
                .buffer(1)
                .subscribeOn(Schedulers.io())
                ;
    }


    private static Supplier<Product> createProduct(final String id, final String name, final Double price, final int latency) {
        return () -> {
            simulateLatency(latency);
            log.debug("Product with id {} ready to return...", id);
            return new Product(id, name, BigDecimal.valueOf(price));
        };
    }

    private static void simulateLatency(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }
}

class Product{
    String id;
    String name;
    BigDecimal price;
    int latency;

    public Product(String id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }
}
