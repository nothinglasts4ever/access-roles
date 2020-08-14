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
* Apache Kafka
* Groovy and Spock
* Maven
* Docker with docker-compose
* Git

## To Do List
- [x] Simple *domain model* using **Spring Data** and **Lombok**
- [x] **Spring MVC** *REST* controller
- [x] Introduce **Docker** and put *database* into separate container
- [x] *Service discovery* using **Eureka** in separate container
- [x] Split *domain model* and put to different *containers*
- [x] Communication between *microservices* using **Feign**
- [x] Refactor one service to use **MongoDB** and **Spring Reactive WebFlux**
- [x] Add new *microservice* with **WebFlux** controller in *functional style*
- [x] *Unit tests* using **Groovy Spock**
- [x] Add *API gateway* service using *Spring Cloud Gateway* or *Zuul*
- [x] Implement *authentication* using *JSON Web Token*
- [x] Split configuration into dev and local *profiles*
- [x] Decouple *authorization server* to separate microservice
- [ ] *Frontend* using **React/Redux** and **Webpack**
- [ ] **Spring MVC Test**
- [x] Add communication between microservices using **Apache Kafka**
- [ ] Implement *CQRS* for one microservice
- [ ] Migrate to **Spring Cloud Kubernetes**
- [ ] Tune Spring (investigate *modularity*, *AppCDS* and maven dependencies) and JVM 

## Architecture
```
       ,_,                ,_,                ,_,  
      (^,^)              (^,^) - H2         (^,^)  
      (   )              (   )              (   )
     --"-"--            --"-"--            --"-"--  
   Gateway API        Auth Server     Service Discovery
  
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

## Description
The main goal of this project was to try domain driven design and learn microservices infrastructure.
Solution itself is about handling passes to access to locations.
Admin can provide access roles for particular persons to access to particular locations for concrete time period.
Only period can be changed or role be deactivated.
Card with barcode and other info is composed based on the access roles for person and can be used to pass the doors. 
All the changes in locations (name), access roles (period) and personal information are reflected in cards, barcode be re-generated.
If person deleted or all the access roles deactivated - card be disabled.

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

## REST API

<details>
  <summary>Login</summary>
  
  ```
  POST /login
  ```
  Payload:
  ```
  {
      "login": "***",
      "password": "***"
  }
  ```
  Add *Authorization* header with the returned token for subsequent requests:
  ```
  Bearer ***
  ```
</details>

<details>
  <summary>Register user</summary>
  
  ```
  POST /users/register
  ```
  Payload:
  ```
  {
      "firstName": "Homer",
      "lastName": "Simpson",
      "login": "***",
      "password": "***"
  }
  ```
</details>

<details>
  <summary>Get all locations or one by id</summary>
  
  ```
  GET /locations
  GET /locations/{id}
  ```
  Response:
  ```
  {
      "id": "5e644a41-50ff-43e5-be85-26d6f9619b6b",
      "name": "Earth"
  }
  ```
</details>

<details>
  <summary>Create location</summary>

  ```  
  POST /locations
  ```
  Payload:
  ```
  {
      "name": "Pluto"
  }
  ```
</details>

<details>
  <summary>Update location</summary>

  ```
  PUT /locations/{id} 
  ```
  Payload:
  ```
  {
      "name": "Gazorpazorp"
  }
  ```
</details>

<details>
  <summary>Delete location</summary>

  ```  
  DELETE /locations/{id} 
  ```
</details>

<details>
  <summary>Get all access roles or one by id</summary>
  
  ```
  GET /access-roles
  GET /access-roles/{id}
  ```
  Response:
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

  ```  
  POST /access-roles
  ```
  Payload:
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

  ```
  PUT /access-roles/{id} 
  ```
  Payload:
  ```
  {
      "startTime": "2029-03-21T13:30:37+03:00",
      "endTime": "2069-03-21T13:30:37+03:00"
  }
  ```
</details>

<details>
  <summary>Delete access role by role id or person id</summary>

  ```  
  DELETE /access-roles/{id}
  DELETE /access-roles?personId={id}
  ```
</details>

<details>
  <summary>Get all persons or one by id</summary>
  
  ```
  GET /persons
  GET /persons/{id}
  ```
  Response:
  ```
  {
      "id": "9f0011f5-72d6-4275-8555-15e350362828",
      "firstName": "Rick",
      "lastName": "Sanchez",
      "gender": "MALE",
      "birthday": "1950-06-05",
      "addresses": [
          {
              "city": "Yellow",
              "street": "32nd",
              "building": 123,
              "apartment": 9876
          },
          {
              "city": "Black",
              "street": "41st",
              "building": 234,
              "apartment": 2342
          }
      ]
  }
  ```
</details>

<details>
  <summary>Create person</summary>

  ```  
  POST /persons
  ```
  Payload:
  ```
  {
      "firstName": "Morty",
      "lastName": "Smith",
      "gender": "MALE",
      "birthday": "2006-06-05",
      "addresses": [
          {
              "city": "Yellow",
              "street": "32nd",
              "building": 123,
              "apartment": 9876
          }
      ]
  }
  ```
</details>

<details>
  <summary>Update person</summary>

  ```
  PUT /persons/{id} 
  ```
  Payload:
  ```
  {
        "firstName": "Morty",
        "lastName": "Smith",
        "gender": "MALE",
        "birthday": "2006-06-05",
        "addresses": [
            {
                "city": "Black",
                "street": "41st",
                "building": 234,
                "apartment": 2342
            }
        ]
    }
  ```
</details>

<details>
  <summary>Delete person</summary>

  ```  
  DELETE /persons/{id}
  ```
</details>

<details>
  <summary>Get all cards (active or deleted) or one by id</summary>
  
  ```
  GET /cards
  GET /cards?active={value}
  GET /cards/{id}
  ```
  Response:
  ```
  {
      "id": "3ab8ea7a-01a9-4dcc-91dd-43a6115f0fca",
      "barcode": "OWYwMDExZjUtNzJkNi00Mjc1LTg1NTUtMTVlMzUwMzYyODI4OjI6ZjRjZjU1OWYtMjFiZi00ODE4LTgyZDMtMzI5OGZlNDgyZGMwOjU4NGMzMzY1LWJiNGEtNGE3OC1hMmQ2LWQ5YjZkMjU2ZGMxOTozNzNlYTAzMS05M2RhLTQ2ZjAtYjJkMS1lYmY2Zjg1MWRkZDc6NWRkYTAxNTktZWQ1Yy00NGQ0LWI1ZjctZWZiNjhmZmJlOGY4",
      "personInfo": {
          "personId": "9f0011f5-72d6-4275-8555-15e350362828",
          "personName": "Rick Sanchez",
          "personDetails": "Gender: M, Age: 70, Addresses: [Yellow, 123 32nd st., apt. 9876; Black, 234 41st st., apt. 2342]"
      },
      "accessRoles": [
          {
              "accessRoleId": "f4cf559f-21bf-4818-82d3-3298fe482dc0",
              "locationId": "584c3365-bb4a-4a78-a2d6-d9b6d256dc19",
              "locationName": "Cronenberg World",
              "expiration": "2030-06-05T17:25:41.262+03:00"
          },
          {
              "accessRoleId": "373ea031-93da-46f0-b2d1-ebf6f851ddd7",
              "locationId": "5dda0159-ed5c-44d4-b5f7-efb68ffbe8f8",
              "locationName": "Gazorpazorp",
              "expiration": "2050-06-05T17:25:41.262+03:00"
          }
      ]
  }
  ```
</details>

<details>
  <summary>Create card</summary>

  ```  
  POST /cards
  ```
  Payload:
  ```
  {
      "personId": "4ab0e982-a990-4e98-8c25-de6e025681a6",
      "personName": "Morty Smith",
      "personDetails": "Gender: M, Age: 14, Addresses: [Yellow, 123 32nd st., apt. 9876]",
      "accessRoles": [
          {
              "accessRoleId": "4525b0a4-00ed-4779-aa09-24d920d53494",
              "locationId": "5e644a41-50ff-43e5-be85-26d6f9619b6b",
              "locationName": "Earth",
              "expiration": "2040-06-05T17:25:41.262+03:00"
          }
      ]
  }
  ```
</details>

<details>
  <summary>Delete card</summary>

  ```  
  DELETE /cards/{id}
  ```
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
    access-roles:
      path: /access-roles/**
      serviceId: access-roles-app
      stripPrefix: false
    locations:
      path: /locations/**
      serviceId: access-roles-app
      stripPrefix: false
    persons:
      path: /persons/**
      serviceId: persons-app
      stripPrefix: false
    cards:
      path: /cards/**
      serviceId: cards-app
      stripPrefix: false
```
### Implement authentication using JSON Web Token
```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http.cors()
            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(new JWTAuthenticationFilter(authenticationManager()))
            .addFilter(new JWTAuthorizationFilter(authenticationManager()))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
}
```
To login to the system use *POST /login* endpoint with the following payload:
```json
{
    "login": "***",
    "password": "***"
}
```
Add *Authorization* header with the returned token for subsequent requests:
```
Bearer ***
```
### Split configuration into dev and local profiles
```yaml
spring:
  profiles:
    active: dev 
```
### Add communication between microservices using Apache Kafka
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
### Decouple authorization server to separate microservice
Auth server config:
```yaml
security:
  oauth2:
    client:
      client-id: client
      client-secret: 53cr3t
    authorization:
      jwt:
        key-value: s1gn1ngK3y
```
Gateway API (as a resource server) config:
```yaml
security:
  oauth2:
    client:
      client-id: client
      client-secret: 53cr3t
    resource:
      jwt:
        key-value: s1gn1ngK3y
```