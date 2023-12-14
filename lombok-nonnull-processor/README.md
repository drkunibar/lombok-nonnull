# Lombok `@Nonnull` Extension

This is an extension for _Lombok_ to generate checks for the `@Nonnull` annotation.

The current Version supports Java 8

<!-- TOC -->
- [How it works](#how-it-works)
    - [Method arguments](#method-arguments)
    - [Method return value](#method-return-value)
- [Special cases](#special-cases)
- [What does not work](#what-does-not-work)
- [Disable code generation](#disable-code-generation)
    - [Method arguments](#method-arguments)
    - [Method return value](#method-return-value)
- [Configure Warning](#configure-warning)
<!-- /TOC -->


## How it works

There are two places that will be supported:

-  Method arguments
-  Method return values

### Method arguments

```java
void setName(@Nonnull name){
    this.name = name;
}
```

becomes

```java
void setName(@Nonnull name){
    if (name == null) {
        throw new NullPointerException("name is marked non-null but is null");
    }
    this.name = name;
}
```

### Method return value

```java
@Nonnull String getName(){
    return this.name;
}
```

becomes

```java
@Nonnull String getName(){
    final String $result = this.name;
    if ($result == null) {
        throw new NullPointerException("the return value must not be 'null':");
    }
    return $result;
}
```

This also works in more complex cases

```java
@Nonnull
public String getName() {
    if (this.name == null) {
        return "f1";
    } else {
        return "f2";
    }
}
```

becomes

```java
@Nonnull
public String getName() {
    if (this.name == null) {
        final String $result = "f1";
        if ($result == null) {
            throw new NullPointerException("the return value must not be 'null':");
        }
        return $result;
    } else {
        final String $result = "f2";
        if ($result == null) {
            throw new NullPointerException("the return value must not be 'null':");
        }
        return $result;
    }
}
```

## Special cases

```java
@Getter(onMethod_ = {@Nonnull})
@Setter(onParam_ = {@Nonnull})
public String name;
```

becomes

```java
@Nonnull
public String getName() {
    final String $result = this.name;
    if ($result == null) {
        throw new NullPointerException("the return value must not be 'null':");
    }
    return $result;
}

public void setName(@Nonnull final String name) {
    if (name == null) {
        throw new NullPointerException("name is marked non-null but is null");
    }
    this.name = name;
}
```

It also works with `@Getter` and `@Setter` on classes

```java
@Nonnull
@Getter @Setter
public class Data {

    @Nonnull
    private String name;
}
```

becomes

```java
@Nonnull
public class Data {
    @Nonnull
    private String name;

    public Data() {
        super();
    }

    @Nonnull
    public String getName() {
        final String $result_0 = this.name;
        if ($result_0 == null) {
            throw new NullPointerException("the return value must not be 'null':");
        }
        return $result_0;
    }

    public void setName(@Nonnull final String name) {
        if (name == null) {
            throw new NullPointerException("name is marked non-null but is null");
        }
        this.name = name;
    }
}
```

`@Data` works as well

```java
@lombok.Data
public class Data {

    @Nonnull
    private String name;
```

becomes

```java
public class Data {
    @Nonnull
    private String name;

    public Data(@Nonnull final String name) {
        super();
        if (name == null) {
            throw new NullPointerException("name is marked non-null but is null");
        }
        this.name = name;
    }

    @Nonnull
    public String getName() {
        final String $result_0 = this.name;
        if ($result_0 == null) {
            throw new NullPointerException("the return value must not be 'null':");
        }
        return $result_0;
    }

    public void setName(@Nonnull final String name) {
        if (name == null) {
            throw new NullPointerException("name is marked non-null but is null");
        }
        this.name = name;
    }

    ....

}
```

## What does not work

```java
@Getter @Setter @Nonnull
public String name;

```

There will be no check-block in the `getter` (don't know why)

```java
@Nonnull
public String getName() {
    return this.name;
}

public void setName(@Nonnull final String name) {
    if (name == null) {
        throw new NullPointerException("name is marked non-null but is null");
    }
    this.name = name;
}
```

`@Nonnull` on classes does not work as well.

```java
@Nonnull
@Getter @Setter

```

## Disable code generation

You can disable code generation by using `@DisableNonNull`.

### Method arguments

```java
public void setName(@Nonnull @DisableNonNull String name) {
    this.name = name;
}
```

### Method return value

```java
public @Nonnull @DisableNonNull String getName() {
    return name;
}
```

## Configure Warning

If you use the `@Nonnull` annotation in places where it is meaningless (primitive types or abstract method etc.), a warning is generated.

```java
com/github/drkunibar/lombok/javac/data/DataTestClass.java:[25,5] @Nonnull only works on methods and arguments
```

You can disable this warnings by setting the configuration paramater `lombok.javax.nonnull.ignoreUnsupportedTypes` in 
your `lombok.config`

```java
lombok.javax.nonnull.ignoreUnsupportedTypes = true
```
