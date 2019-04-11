# Owl
Pet (that's whyl owl) project to play with microservices infrastructure built using Spring Boot

        .-"""""""-.    .'""."".""`.
       /   \ * /   \  /  ^ \   /^  \
      { ((@)\*/((@) }.{ ~Q '\ / Q ~}}
     ( `.~~ .V. ~~.'}.`._.'  V`._.'  )
     ( ( `-')  `-'  }   ~~     ~~    )
     (  (   ) ^ ^   0 "  " "   (  (   )
     (      )  ^^  00   "      (      .)
     ( (   )     ^ 00     "    ( (   ) )
      {   )  ^^   .0'0  "   "   (     }
       { }  ^   ))    0          (   }
        `--www--'       "--mmm------ "

## To do list
- [x] Simple *domain model* using **Spring Data** and **Lombok**
- [x] **Spring MVC** *REST* controller
- [x] Introduce **Docker** and put **MySQL** in separate container
- [x] *Service discovery* using **Eureka** in separate container
- [x] Split domain model and put to different containers
- [x] Communication between *microservices* using **Feign**
- [x] Refactor one service to use **MongoDB** and **Spring Reactive WebFlux**
- [x] Add new microservice with WebFlux controller in *functional style*
- [x] *Unit tests* using **Groovy Spock**
- [x] Add *API gateway* service
- [ ] Implement *authentication* (probably add **Redis**)
- [ ] *Frontend* using **React/Redux** and **Webpack**
- [ ] **Spring MVC Test**
- [ ] Communicate between microservices using **Kafka** and/or **RabbitMQ**
- [ ] Implement *CQRS* for one microservice
- [ ] Tune Spring (investigate *modularity*, *AppCDS* and maven dependencies) and JVM 
- [ ] Refactor one microservice with **Kotlin**
- [ ] Add new microservice using **Golang**

## Architecture
```
                ,_,                ,_,
               (^,^)              (^,^)
               (   )              (   )
              --"-"--            --"-"--
            Gateway API     Service Discovery
  
       ,_,                ,_,                ,_,  
      (.,.)              (O,O)              (-,-)  
      (   )              (   )              (   )
     --"-"--            --"-"--            --"-"--         
Access Roles App      Persons App         Cards App

        |                  |                  |
        
       _.-,               /|\                /|\  
   .--'  '-._            / |/\              / |/\ 
 _/`-  _      '.        | \|.'|            | \|.'|
'----'._`.----. \       \'.|/ /            \'.|/ /
        `     \;         '.|.'              '.|.' 
      MySQL   ;_\       MongoDb            MongoDb

```

## Details
### Simple domain model using Spring Data and Lombok
```java
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessRole implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String personId;
    @ManyToOne
    private Location location;
    private LocalDateTime start;
    private LocalDateTime end;
    private String createdBy;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
```

### Spring MVC REST controller
```java
@RestController
@RequestMapping("/access-roles")
@Slf4j
public class AccessRoleController {

    private final AccessRoleService accessRolesService;

    @Autowired
    public AccessRoleController(AccessRoleService accessRolesService) {
        this.accessRolesService = accessRolesService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccessRole> getAccessRole(@PathVariable Long id) {
        var accessRoles = accessRolesService.getAccessRole(id);
        return ResponseEntity.ok(accessRoles);
    }

    @PostMapping
    public ResponseEntity<Void> createAccessRole(@RequestBody AccessRole accessRoles, HttpServletRequest request) {
        var createdAccessRole = accessRolesService.createAccessRole(accessRoles);
        var uri = ServletUriComponentsBuilder
                .fromContextPath(request)
                .path("/access-roles/{id}")
                .buildAndExpand(createdAccessRole.getId())
                .toUri();
        log.info("Access role created: " + uri);
        return ResponseEntity.created(uri).build();
    }

}
```
### Introduce Docker and put MySQL in separate container
```
FROM openjdk:13-alpine
COPY target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```
```
  access-roles-mysql:
    container_name: access-roles-mysql
    image: mysql:8.0.15
    command: --default-authentication-plugin=mysql_native_password
    ports:
      - 3308:3306
    expose:
      - 3306
    environment:
      MYSQL_DATABASE: access_roles
      MYSQL_USER: ***
      MYSQL_PASSWORD: ***
      MYSQL_ROOT_PASSWORD: ***

  access-roles-app:
    container_name: access-roles-app
    build:
      context: access-roles-app
      dockerfile: Dockerfile
    ports:
      - 8080:8080
    depends_on:
      - access-roles-mysql
    environment:
      - DATABASE_HOST=access-roles-mysql
      - DATABASE_PORT=3306
      - DATABASE_NAME=access_roles
      - DATABASE_USER=***
      - DATABASE_PASSWORD=***
```
### Service discovery using Eureka in separate container
```java
@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoveryApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDiscoveryApplication.class, args);
    }
}
```
```
spring:
  application:
    name: service-discovery
server:
  port: 8761
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```
### Communication between microservices using Feign
```java
@FeignClient(value = "access-roles-app")
public interface AccessRolesClient {
    @RequestMapping(method = RequestMethod.DELETE, value = "/access-roles")
    ResponseEntity<Void> deleteAccessRolesForPerson(@RequestParam String personId);
}
```
### Refactor one service to use MongoDB and Spring Reactive WebFlux
```java
@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthday;
    private Set<Address> addresses;
}
```
```java
@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String city;
    private String street;
    private int building;
    private int apartment;
}
```
```java
public interface PersonRepository extends ReactiveMongoRepository<Person, String> {
}
```
```java
    @PostMapping
    public Mono<ResponseEntity<Person>> createPerson(@RequestBody Person person) {
        return personService.createPerson(person)
                .map(p -> {
                    log.info("Person with id [" + p.getId() + "] created");
                    return new ResponseEntity<>(p, HttpStatus.CREATED);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
```
### Add new microservice with WebFlux controller in functional style
```java
@Configuration
public class CardRouter {
    @Bean
    public RouterFunction<ServerResponse> route(CardHandler cardHandler) {
        var get = RequestPredicates.GET("/cards/{id}")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

        var getAll = RequestPredicates.GET("/cards")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

        var post = RequestPredicates.POST("/cards")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON));

        return RouterFunctions.route(get, cardHandler::get)
                .andRoute(getAll, cardHandler::getAll)
                .andRoute(post, cardHandler::post);
    }
}
```
```java
@Component
public class CardHandler {

    private final CardService cardService;

    @Autowired
    public CardHandler(CardService cardService) {
        this.cardService = cardService;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(cardService.getAllCards(), Card.class));
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        var id = request.pathVariable("id");
        var card = cardService.getCard(id);
        var successResponse = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(card, Card.class));
        return card
                .flatMap(c -> successResponse)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

     public Mono<ServerResponse> post(ServerRequest request) {
        var location = UriComponentsBuilder.fromPath("cards/" + "id").build().toUri();
        var cardRequest = request.bodyToMono(CardCreateRequest.class)
                .flatMap(c -> cardService.createCard(c.getPersonInfo(), c.getAccessRoles()));
        return ServerResponse.created(location)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(cardRequest, Card.class));
    }

}
```
### Unit tests using Groovy Spock
```groovy
class CardUtilTest extends Specification {

    @Shared
    def rick

    @Shared
    def role

    def setup() {
        rick = PersonInfo.builder()
                .personId("1")
                .personName("Rick Sanchez")
                .personDetails("Gender: M, Age: 70")
                .build()
        role = AccessRoleInfo.builder()
                .accessRoleId(10L)
                .locationId(1L)
                .locationName("Gazorpazorp")
                .expiration(LocalDateTime.now().plusYears(30))
                .build()
    }

    def "Barcode generated correctly when all required values are present"() {
        given:
            def role2 = AccessRoleInfo.builder()
                    .accessRoleId(11L)
                    .locationId(2L)
                    .locationName("Cronenberg World")
                    .expiration(LocalDateTime.now().plusYears(10))
                    .build()
        when:
            def barcode = CardUtil.generateBarcode(rick, [role, role2].toSet())
        then:
            barcode == "MToyOjEwOjE6MTE6Mg=="                                  || "MToyOjExOjI6MTA6MQ=="
            new String(Base64.getDecoder().decode(barcode)) == "1:2:10:1:11:2" || "1:2:11:2:10:1"
    }

    def "Barcode is 0 when not all required values are present"() {
        expect:
            CardUtil.generateBarcode(person, roles) == barcode
        where:
            person | roles          | barcode
            null   | [role].toSet() | "MA=="
            rick   | null           | "MA=="
            rick   | [].toSet()     | "MA=="
    }

}
```
