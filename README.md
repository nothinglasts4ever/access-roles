# Owl
Pet (that's why owl) project to play with microservices infrastructure built using Spring Boot

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

## Stack
**Note**
>This project was created prior to my experience in microservice architecture
>and uses frameworks and technologies that I decided to learn by that time.
>
>It does NOT use modern Spring Cloud stack, does NOT build around Kubernetes
>and does NOT use ready Access Management solution like Keycloak.

* Java 12 and Lombok
* Spring Boot 2
* Spring Data
* Spring MVC and Spring WebFlux
* Eureka
* Zuul
* Feign
* JWT
* MySQL and MongoDB
* Groovy and Spock
* Maven
* Docker with docker-compose
* Git

## To Do List
- [x] Simple *domain model* using **Spring Data** and **Lombok**
- [x] **Spring MVC** *REST* controller
- [x] Introduce **Docker** and put **MySQL** in separate container
- [x] *Service discovery* using **Eureka** in separate container
- [x] Split domain model and put to different containers
- [x] Communication between *microservices* using **Feign**
- [x] Refactor one service to use **MongoDB** and **Spring Reactive WebFlux**
- [x] Add new microservice with WebFlux controller in *functional style*
- [x] *Unit tests* using **Groovy Spock**
- [x] Add *API gateway* service using *Spring Cloud Gateway* or *Zuul*
- [x] Implement *authentication* using JSON Web Token
- [ ] *Frontend* using **React/Redux** and **Webpack**
- [ ] **Spring MVC Test**
- [ ] Communicate between microservices using **Kafka**
- [ ] Implement *CQRS* for one microservice
- [ ] Tune Spring (investigate *modularity*, *AppCDS* and maven dependencies) and JVM 

## Architecture
```
                ,_,                ,_,
               (^,^) - H2         (^,^)
               (   )              (   )
              --"-"--            --"-"--
         Gateway API & Auth  Service Discovery
  
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

## Domain Model
* Persons App
  * Contains information about persons and their addresses
  * Addresses and persons can be added, modified or deleted
* Access Roles App
  * Contains locations and access roles associated with these locations
  * Locations can be added, modified or deleted
  * Persons can get access roles for dedicated locations
* Cards App
  * Contains cards that associated with access roles
  * Cards can be generated for particular persons with access roles assigned to them
  * Cards contain persons, locations and access roles information
* Gateway App
  * Uses API Gateway pattern to route requests to appropriate services
  * Used for user authentication
  * New users can be registered
* Service Discovery App
  * Used for service discovery

## Details
### Simple domain model using Spring Data and Lombok
```java
@Entity
@Data
public class AccessRole implements Serializable {
    @Id
    private UUID id;
    @NotNull
    private String personId;
    @ManyToOne
    @NotNull
    private Location location;
    @NotNull
    private OffsetDateTime start;
    @NotNull
    private OffsetDateTime end;
    private String createdBy;
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
```

### Spring MVC REST controller
```java
@RestController
@RequestMapping("/access-roles")
@RequiredArgsConstructor
@Slf4j
public class AccessRoleController {

    private final AccessRoleService accessRolesService;

    @GetMapping
    public ResponseEntity<Collection<AccessRoleDto>> allAccessRoles() {
        var accessRoles = accessRolesService.allRoles();
        return ResponseEntity.ok(accessRoles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessRoleDto> getAccessRole(@PathVariable UUID id) {
        var accessRoles = accessRolesService.getAccessRole(id);
        return ResponseEntity.ok(accessRoles);
    }

    @PostMapping
    public ResponseEntity<Void> createAccessRole(@RequestBody @Valid AccessRoleDto accessRoles, HttpServletRequest request) {
        var createdAccessRole = accessRolesService.createAccessRole(accessRoles);
        var uri = ServletUriComponentsBuilder
                .fromContextPath(request)
                .path("/access-roles/{id}")
                .buildAndExpand(createdAccessRole.getId())
                .toUri();
        log.info("Access role created: {}", uri);
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
```yaml
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
```yaml
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
    @DeleteMapping("/access-roles")
    ResponseEntity<Void> deleteAccessRolesForPerson(@RequestParam UUID personId);
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
    private UUID id;
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
public interface PersonRepository extends ReactiveMongoRepository<Person, UUID> {
}
```
```java
    @PostMapping
    public Mono<ResponseEntity<PersonDto>> createPerson(@RequestBody PersonDto person) {
        return personService.createPerson(person)
                .map(p -> {
                    log.info("Person with id [{}] created", p.getId());
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
@RequiredArgsConstructor
public class CardHandler {

    private final CardService cardService;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(cardService.getAllCards(), CardDto.class));
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        var id = request.pathVariable("id");
        var card = cardService.getCard(UUID.fromString(id));
        var successResponse = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(card, CardDto.class));

        return card
                .flatMap(c -> successResponse)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> post(ServerRequest request) {
        var location = UriComponentsBuilder.fromPath("cards/" + "id").build().toUri();
        var cardRequest = request.bodyToMono(CardCreateRequest.class)
                .flatMap(cardService::createCard);

        return ServerResponse.created(location)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(cardRequest, CardDto.class));
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
### Add API gateway service using Spring Cloud Gateway
```yaml
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
      routes:
        - id: access-roles-app
          uri: lb://access-roles-app
          predicates:
            - Path=/access-roles/**
          filters:
            - RewritePath=/access-roles/(?<path>.*), /$\{path}
        - id: locations-app
          uri: lb://access-roles-app
          predicates:
            - Path=/locations/**
          filters:
            - RewritePath=/locations/(?<path>.*), /$\{path}
        - id: persons-app
          uri: lb://persons-app
          predicates:
            - Path=/persons/**
          filters:
            - RewritePath=/persons/(?<path>.*), /$\{path}
        - id: cards-app
          uri: lb://cards-app
          predicates:
            - Path=/cards/**
          filters:
            - RewritePath=/cards/(?<path>.*), /$\{path}
```
### API gateway service using Zuul
```yaml
zuul:
  ignoredServices: '*'
  routes:
    access-roles-app:
      path: /access-roles/**
      serviceId: access-roles-app
      stripPrefix: false
    locations-app:
      path: /locations/**
      serviceId: access-roles-app
      stripPrefix: false
    persons-app:
      path: /persons/**
      serviceId: persons-app
      stripPrefix: false
    cards-app:
      path: /cards/**
      serviceId: cards-app
      stripPrefix: false
```
### Implement authentication using JSON Web Token
```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
```