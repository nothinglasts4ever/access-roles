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
>and it uses frameworks and technologies that I decided to learn by that time.
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
* PostgreSQL and MongoDB
* Groovy and Spock
* Maven
* Docker with docker-compose
* Git

## To Do List
- [x] Simple *domain model* using **Spring Data** and **Lombok**
- [x] **Spring MVC** *REST* controller
- [x] Introduce **Docker** and put database into separate container
- [x] *Service discovery* using **Eureka** in separate container
- [x] Split domain model and put to different containers
- [x] Communication between *microservices* using **Feign**
- [x] Refactor one service to use **MongoDB** and **Spring Reactive WebFlux**
- [x] Add new microservice with WebFlux controller in *functional style*
- [x] *Unit tests* using **Groovy Spock**
- [x] Add *API gateway* service using *Spring Cloud Gateway* or *Zuul*
- [x] Implement *authentication* using JSON Web Token
- [x] Split configuration into dev and local profiles
- [ ] *Frontend* using **React/Redux** and **Webpack**
- [ ] **Spring MVC Test**
- [x] Add communication between microservices using **Kafka**
- [ ] Implement *CQRS* for one microservice
- [ ] Migrate to Spring Cloud Kubernetes
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
        
        _  _ \            /|\                /|\  
       ( \--,/)          / |/\              / |/\ 
   ,---\ ` '_/          | \|.'|            | \|.'|
  /( ___'--/`           \'.|/ /            \'.|/ /
   |_|\ |_|\             '.|.'              '.|.' 
   PostgreSQL           MongoDb            MongoDb

```

## Domain Model
The main goal of this project was to try domain driven design and learn microservices infrastructure.
Solution itself is about handling passes to access to locations.
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

## REST API
<details>
  <summary>Click to expand</summary>

  ### Get all locations and one by id
  ```
  GET /locations
  GET /locations/{id}
  ``` 
  ```
  {
      "id": "5e644a41-50ff-43e5-be85-26d6f9619b6b",
      "name": "Earth"
  }
  ```
  ### Create location
  ```
  POST /locations
  {
      "name": "Pluto"
  }
  ```
  ### Update location
  ```
  PUT /locations/{id} 
  {
      "name": "Gazorpazorp"
  }
  ```
  ### Delete location
  ```
  DELETE /locations/{id} 
  ```
</details>

<details>
  <summary>Get all access roles or one by id</summary>
  
  GET /access-roles
  GET /access-roles/{id}
  ```
  {
      "id": "6d4066e8-d471-4fe5-8982-d96e690794d6",
      "personId": "fd9da139-7bc5-4885-982b-d107732f1cc1",
      "location": {
          "id": "5e644a41-50ff-43e5-be85-26d6f9619b6b",
          "name": "Earth"
      },
      "startTime": "2019-03-21T13:30:37+03:00",
      "endTime": "2069-03-21T13:30:37+03:00"
  }
  ```
</details>

<details>
  <summary>Create access role</summary>
  
  POST /access-roles
  ```
  {
      "personId": "fd9da139-7bc5-4885-982b-d107732f1cc1",
      "locationId": "5e644a41-50ff-43e5-be85-26d6f9619b6b",
      "startTime": "2019-03-21T13:30:37+03:00",
      "endTime": "2069-03-21T13:30:37+03:00"
  }
  ```
</details>

<details>
  <summary>Update access role</summary>

  PUT /access-roles/{id} 
  ```
  {
      "startTime": "2029-03-21T13:30:37+03:00",
      "endTime": "2069-03-21T13:30:37+03:00"
  }
  ```
</details>

<details>
  <summary>Delete access role by role id or person id</summary>
  
  DELETE /access-roles/{id}
  DELETE /access-roles?personId={id}
</details>

## Implementation Details
### Simple domain model using Spring Data and Lombok
```java
@Entity
@Where(clause = "deleted_at IS NULL")
@Data
public class AccessRole {
    @Id
    private UUID id;

    @NotNull
    private UUID personId;
    @ManyToOne
    @NotNull
    private Location location;
    @NotNull
    private OffsetDateTime startTime;
    @NotNull
    private OffsetDateTime endTime;

    private String createdBy;
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
    private OffsetDateTime deletedAt;
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
    public ResponseEntity<Void> createAccessRole(@RequestBody @Valid AccessRoleRequest accessRole, HttpServletRequest request) {
        var createdAccessRole = accessRolesService.createAccessRole(accessRole);
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
### Introduce Docker and put database into separate container
```
FROM openjdk:13-alpine
COPY target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```
```yaml
  access-roles-postgresql:
    container_name: access-roles-postgresql
    image: postgres:12
    environment:
      - POSTGRES_USER=user
      - POSTGRES_PASSWORD=password
      - POSTGRES_DB=access_roles
    ports:
      - 5432:5432
    volumes:
      - /tmp/owl/access-roles-postgresql:/var/lib/postgresql/data
    restart: on-failure

  access-roles-app:
    container_name: access-roles-app
    build:
      context: access-roles-app
      dockerfile: Dockerfile
    depends_on:
      - service-discovery
      - access-roles-postgresql
    environment:
      - DATABASE_HOST=access-roles-postgresql
      - DATABASE_PORT=5432
      - DATABASE_NAME=access_roles
      - DATABASE_USER=user
      - DATABASE_PASSWORD=***
    volumes:
      - /tmp/owl/access-roles-app:/app
    restart: on-failure
    links:
      - service-discovery
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
```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://service-discovery:8761/eureka
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

        var delete = RequestPredicates.DELETE("/cards/{id}")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

        return RouterFunctions.route(get, cardHandler::get)
                .andRoute(getAll, cardHandler::getAll)
                .andRoute(post, cardHandler::post)
                .andRoute(delete, cardHandler::delete);
    }
}
```
```java
@Component
@RequiredArgsConstructor
public class CardHandler {

    private final CardService cardService;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        var active = request.queryParam("active")
                .orElse("true");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(cardService.getAllCards(Boolean.parseBoolean(active)), CardDto.class));
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

    public Mono<ServerResponse> delete(ServerRequest request) {
        var id = request.pathVariable("id");
        return cardService.softDelete(UUID.fromString(id))
                .flatMap(c -> ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build());
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
                .personId(UUID.fromString("9f0011f5-72d6-4275-8555-15e350362828"))
                .personName("Rick Sanchez")
                .personDetails(CardUtil.composePersonDetails(Gender.MALE, LocalDate.now().minusYears(70), null))
                .build()
        role = AccessRoleInfo.builder()
                .accessRoleId(UUID.fromString("373ea031-93da-46f0-b2d1-ebf6f851ddd7"))
                .locationId(UUID.fromString("5dda0159-ed5c-44d4-b5f7-efb68ffbe8f8"))
                .locationName("Gazorpazorp")
                .expiration(OffsetDateTime.now().plusYears(30))
                .build()
    }

    def "Barcode generated correctly when all required values are present"() {
        given:
            def role2 = AccessRoleInfo.builder()
                    .accessRoleId(UUID.fromString("f4cf559f-21bf-4818-82d3-3298fe482dc0"))
                    .locationId(UUID.fromString("584c3365-bb4a-4a78-a2d6-d9b6d256dc19"))
                    .locationName("Cronenberg World")
                    .expiration(OffsetDateTime.now().plusYears(10))
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
### Split configuration into dev and local profiles
```yaml
spring:
  profiles:
    active: dev 
```
### Add communication between microservices using Kafka
```yaml
spring:
  kafka:
    bootstrap-servers: kafka:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
    consumer:
      group-id: cards-app
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: com.github.nl4.owl.common.messaging
app:
  topic:
    access-role: access-role
    location: location
    person: person
```
```java
@Service
@RequiredArgsConstructor
public class Producer {

    @Value("${app.topic.person}")
    private String personTopic;

    private final KafkaTemplate<String, MessagingEvent> template;

    public void sendToPersonTopic(MessagingEvent message) {
        sendMessage(message, personTopic);
    }

    private void sendMessage(MessagingEvent message, String topic) {
        var msg = MessageBuilder.withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();

        template.send(msg);
    }

}
```
```java
@Service
@RequiredArgsConstructor
@Slf4j
public class Consumer {

    private final UpdateService updateService;

    @KafkaListener(topics = "${app.topic.person}")
    public void listenPersonUpdates(Message<MessagingEvent> message) throws Exception {
        var data = message.getPayload();
        var type = data.getType();
        log.info("Received message [{}] for person [{}]", type, data.getId());

        if (type == MessageType.PERSON_UPDATED) {
            updateService.updateCardsForPerson(data);
        } else if (type == MessageType.PERSON_DELETED) {
            updateService.deleteCards(data);
        }
    }

}
```