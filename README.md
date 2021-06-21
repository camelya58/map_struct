# map_struct

Simple project using MapStruct and Transfer Object Pattern.

## Step 1
Add dependencies.
For convenience, I suggest using the Lombok.
```xml
<dependencies>
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>1.4.1.Final</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.5.2</version>
            <scope>test</scope>
        </dependency>

</dependencies>
```
You also need to add the annotationProcessorPaths section to the configuration part of the maven-compiler-plugin
for mapstruct and lombok.
```xml
     <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.mapstruct</groupId>
                            <artifactId>mapstruct-processor</artifactId>
                            <version>${mapstruct.version}</version>
                        </path>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <version>${lombok.version}</version>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
      </plugins>
```

## Step 2
Create POJO for source class and target with the same fields.
```java
@Data
public class Source {
    private String name;
    private String description;
    // getters and setters
}
@Data
public class Target {
    private String name;
    private String description;
    // getters and setters
}
```
Then create an interface SourceTargetMapper using annotation @Mapper.
```java
@Mapper
public interface SourceTargetMapper {
    Target sourceToTarget(Source source);
    Source targetToSource(Target target);
}
```
That's all. 

## Step 3
We can trigger the MapStruct processing by executing an *mvn clean install*.
This will generate the implementation class under **/target/generated-sources/annotations/**.

And we will see something like this:
```java
public class SourceTargetMapperImpl
  implements SourceTargetMapper {
    @Override
    public Target sourceToTarget(Source source) {
        if (source == null) {
            return null;
        }
        Target target = new Target();
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        return target;
    }
    @Override
    public Source targetToSource(Target target){
        if (target == null) {
            return null;
        }
        Source Source = new Source();
        source.setName(destination.getName());
        source.setDescription(destination.getDescription());
        return source;
    }
}
```
Test Case
```java
public class SourceTargetMapperIntegrationTest {
    private SourceTargetMapper mapper
      = Mappers.getMapper(SourceTargetMapper.class);
    @Test
    public void givenSourceToTarget() {
        Source source = new Source();
        source.setName("SourceName");
        source.setDescription("SourceDescription");
        Target target = mapper.sourceToTarget(source);
 
        assertEquals(source.getName(), target.getName());
        assertEquals(source.getDescription(), target.getDescription());
    }
    @Test
    public void givenTargetToSource() {
        Target target = new Target();
        target.setName("TargetName");
        target.setDescription("TargetDescription");
        Source source = mapper.targetToSource(target);
        assertEquals(target.getName(), source.getName());
        assertEquals(target.getDescription(), source.getDescription());
    }
}
```

### Mapping Beans with different fields.
Create POJO.
```java
@Data
public class EmployeeDTO {
    private int employeeId;
    private String employeeName;
    // getters and setters
}
@Data
public class Employee {
    private int id;
    private String name;
    // getters and setters
}
```
When you create your interface mapper use @Mappings and @Mapping
to describe which fields need to be the same 
```java
@Mapper
public interface EmployeeMapper {
    @Mappings({
      @Mapping(target="employeeId", source="entity.id"),
      @Mapping(target="employeeName", source="entity.name")
    })
    EmployeeDTO employeeToEmployeeDTO(Employee entity);

    @Mappings({
      @Mapping(target="id", source="dto.employeeId"),
      @Mapping(target="name", source="dto.employeeName")
    })
    Employee employeeDTOtoEmployee(EmployeeDTO dto);
}
```

### Mapping Beans with child beans.
If your class contains over class:
```java
@Data
public class EmployeeDTO {
    private int employeeId;
    private String employeeName;
    private DivisionDTO division;
    // getters and setters
}
@Data
public class Employee {
    private int id;
    private String name;
    private Division division;
    // getters and setters
}
@Data
@NoArgsConstructor
public class DivisionDTO {
    private int id;
    private String name;
    // default constructor, getters and setters
}
@Data
@NoArgsConstructor
public class Division {
    private int id;
    private String name;
    // default constructor, getters and setters
}
```
You just need to add this to the previous mapper:
 ```java
@Mapper
public interface EmployeeMapper {
    //...

    DivisionDTO divisionToDivisionDTO(Division entity);

    Division divisionDTOtoDivision(DivisionDTO dto);
}
``` 
### Mapping with type conversion
Add a start date to our employee.
```java
@Data
public class Employee {
    // other fields
    private Date startDt;
    // getters and setters
}
@Data
public class EmployeeDTO {
    // other fields
    private String employeeStartDt;
    // getters and setters
}
```
In this case we need to add dateFormat to @Mapping annotation as a parameter.
```java
@Mapper
public interface EmployeeMapper {
       @Mappings({
         @Mapping(target="employeeId", source = "entity.id"),
         @Mapping(target="employeeName", source = "entity.name"),
         @Mapping(target="employeeStartDt", source = "entity.startDt",
                  dateFormat = "dd-MM-yyyy HH:mm:ss")})
       EmployeeDTO employeeToEmployeeDTO(Employee entity);
      
       @Mappings({
         @Mapping(target="id", source="dto.employeeId"),
         @Mapping(target="name", source="dto.employeeName"),
         @Mapping(target="startDt", source="dto.employeeStartDt",
                  dateFormat="dd-MM-yyyy HH:mm:ss")})
       Employee employeeDTOtoEmployee(EmployeeDTO dto);
}
```

### Mapping With an Abstract Class
If you have the source and the target with different fields.
We need to convert the BigDecimal total amount of dollars into a Long totalInCents.
```java
@Getter
public class Transaction {
    private Long id;
    private String uuid = UUID.randomUUID().toString();
    private BigDecimal total;

    //standard getters
}
@Data
public class TransactionDTO {

    private String uuid;
    private Long totalInCents;

    // standard getters and setters
}
```

In case when you need to convert one field to another using some manipulations
we need to convert it ourselves.
But the collection of the source type will be automatically converted to the list of the target type.

```java
@Mapper
abstract class TransactionMapper {

    public TransactionDTO toTransactionDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setUuid(transaction.getUuid());
        transactionDTO.setTotalInCents(transaction.getTotal()
          .multiply(new BigDecimal("100")).longValue());
        return transactionDTO;
    }

    public abstract List<TransactionDTO> toTransactionDTO(
      Collection<Transaction> transactions);
}
```

### Before-Mapping and After-Mapping Annotations
In case when the source can have different types and we want to convert it to target
depends on that difference.
```java
@Data
@NoArgsConstructor
public class Car {
    private int id;
    private String name;
}

public class BioDieselCar extends Car {
}

public class ElectricCar extends Car {
}

@Data
@NoArgsConstructor
public class CarDTO {
    private int id;
    private String name;
    private FuelType fuelType;
}

public enum FuelType {
    ELECTRIC, BIO_DIESEL
}
```
 
We need to write the methods which can be invoked before and after mapping
using @BeforeMapping and @AfterMapping.
The conversion itself will be automatic.
```java
@Mapper
public abstract class CarsMapper {
    @BeforeMapping
    protected void enrichDTOWithFuelType(Car car, @MappingTarget CarDTO carDto) {
        if (car instanceof ElectricCar) {
            carDto.setFuelType(FuelType.ELECTRIC);
        }
        if (car instanceof BioDieselCar) { 
            carDto.setFuelType(FuelType.BIO_DIESEL);
        }
    }

    @AfterMapping
    protected void convertNameToUpperCase(@MappingTarget CarDTO carDto) {
        carDto.setName(carDto.getName().toUpperCase());
    }

    public abstract CarDTO toCarDto(Car car);
}
```

### Support for defaultExpression
```java
@Data
public class Person {
    private String id;
    private String name;
}
@Data
public class PersonDTO {
    private String id;
    private String name;
}
```
In case when the value of the source field can be null
we can set the default value to the target field using @Mapping.

Moreover we need to instantiate PersonMapper instance which we will use to invoke
the conversion methods.
```java
@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
    
    @Mapping(target = "id", source = "person.id", 
      defaultExpression = "java(java.util.UUID.randomUUID().toString())")
    PersonDTO personToPersonDTO(Person person);
}
```
Test Case
```java
public class PersonMapperIntegrationTest {
    @Test
    public void givenPersonEntityToPerson() {
        Person entity  = new Person();
        entity.setName("Micheal");
        PersonDTO personDto = PersonMapper.INSTANCE.personToPersonDTO(entity);
        assertNull(entity.getId());
        assertNotNull(personDto.getId());
        assertEquals(personDto.getName(), entity.getName());
    }
}
```