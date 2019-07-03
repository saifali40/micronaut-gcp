package demo;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.reactivex.Flowable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Controller("/")
public class HelloController {

    ProductService service;
    public HelloController(ProductService service){
        this.service = service;
    }

    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        List<Integer> integers = Arrays.asList(1,2,3,4,5,6,7);
        integers.parallelStream().forEach(System.out::println);
        return "Hello World!";
    }


    @Get("/demo")
    @Produces(MediaType.TEXT_EVENT_STREAM)
    public Flowable<List<List<Product>>> demo(){
        return service.findProduct();
    }
}