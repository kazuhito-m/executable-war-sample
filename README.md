Excutable War Sample
====================

## Latest integration

+ TODO CIService

## What's this ?

実行可能Warのビルドサンプル。

## Build

`./gradlew clean build`

## Usage

`java -jar ./build/libs/*.war`

### Parameters

#### ConntextRoot

```bash
java -jar ./build/libs/*.war -c 'contextRootName'
java -jar ./build/libs/*.war --ContextRoot 'contextRootName'
```

#### Port

```bash
java -jar ./build/libs/*.war -p 8888
java -jar ./build/libs/*.war --Port 8888
```

## Author

Kazuhito Miura ( [@kazuhito_m](https://twitter.com/kazuhito_m) on Twitter )
