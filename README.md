# chatson
 
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

## Using

Get started using Chatson

```
Chatson.toJson(chatson);
Chatson.toTextComponent(chatson);
```

Warning do not use other chatson project classes as those are subject to change and the version will not bump.

## Setting up as a dependency

### Gradle

``` groovy
repositories {
     maven { url 'https://jitpack.io' }
}
compile 'com.github.clubobsidian:chatson:1.0.0'
```

### Maven

``` xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependency>
	<groupId>com.github.clubobsidian</groupId>
	<artifactId>chatson</artifactId>
	<version>1.0.0</version>
</dependency>
```

## Dependencies

* [text](https://github.com/KyoriPowered/text)

## Development

### Eclipse

1.  Git clone the project
2.  Generate eclipse files with `gradlew eclipse`
3.  Import project

### Intellij

1.  Git clone the project
2.  Generate intellij files with `gradlew idea`
3.  Import project

### Building

`gradlew shadowJar`
