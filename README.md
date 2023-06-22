# TextLocale

TextLocale is a Java package designed for easy localization of texts in your applications.

## Features

- Load localized texts from JSON files.
- Support for custom loaders to load texts from various sources.
- Retrieve localized texts using keys and optional formatting parameters.
- Dynamic updates of strings using the `Text` class.

## Installation

TextLocale library is available on JitPack, a package repository that allows you to easily use GitHub repositories as dependencies. To include textlocale in your project, follow the steps below:

1. Add the JitPack repository to your build file. For Maven, add the following to your `pom.xml` file:

   ```xml
   <repositories>
       <repository>
           <id>jitpack.io</id>
           <url>https://jitpack.io</url>
       </repository>
   </repositories>
   ```

   For Gradle, add the following to your `build.gradle` file:

   ```groovy
   repositories {
       maven { url 'https://jitpack.io' }
   }
   ```

2. Add textlocale as a dependency. For Maven, add the following to your `pom.xml` file:

   ```xml
   <dependencies>
       <dependency>
            <groupId>com.github.butvinm</groupId>
            <artifactId>textlocale</artifactId>
            <version>4.0.1</version>
        </dependency>
   </dependencies>
   ```

   For Gradle, add the following to your `build.gradle` file:

   ```groovy
   dependencies {
       implementation 'com.github.butvinm:textlocale:4.0.1'
   }
   ```

## JSON File Format

The JSON files containing the localized texts should have `.tl.json` extension and follow a specific format. Here is an example:

```json
{
    "Greet": {
        "en": "Hello, %s!",
        "uk": "Привіт, %s!"
    },
    "LoginButton": {
        "en": "Login",
        "uk": "Увійти"
    },
    "Fields": {
        "Username": {
            "Label": {
                "en": "Username",
                "uk": "Ім'я користувача"
            },
            "Placeholder": {
                "en": "Enter your username",
                "uk": "Введіть ваше ім'я користувача"
            }
        }
        ...
    }
}
```

The JSON file should contain key-value pairs, where the keys represent different categories or contexts of the localized texts. Each category can have multiple subkeys, representing different aspects or variations of the text.

The example above shows a category named "Greet" with two subkeys, "en" and "uk", representing the English and Ukrainian translations of the text. The "Fields" category has a subcategory named "Username" with two subkeys, "Label" and "Placeholder", representing the label and placeholder texts for the username field.

Terminal nodes of the JSON tree should contain the localized texts.

## Usage

### Loading Texts

To load localized texts, you need to specify a loader that can load the texts from the desired source. TextLocale supports loading texts from JSON files out of the box, but you can also implement a custom loader if needed.

Imagine that you have a Java application with the following structure:

```
app
├───menu
    ├───login_form.tl.json
```

So, you can load the texts from the JSON file using the following code:

```java
URL codeSourceUrl = App.class.getProtectionDomain().getCodeSource().getLocation();
FilesLoader loader = new ResourcesLoader(codeSourceUrl, TEXTS_JSON_EXTENSION);
TextPackage rootPackage = TextLocale.loadPackage("app", TextsManager::getLocale, loader);
```

In the above example, the `ResourcesLoader` is used to load JSON files from the specified location. Adjust the loader and location based on your specific requirements.

### Retrieving Texts

Once the texts are loaded, you can obtain a `TextSupplier` instance from the root package to retrieve localized texts. This allows you to access the texts in different parts of your application.

```java
TextSupplier ts = rootPackage.getPackage("menu.login_form")::getText;
```

### Accessing Texts

You can access a specific localized text using the `t` method of the `TextSupplier` interface. Provide the desired key and any formatting parameters required.

```java
System.out.println(ts.t("Greet", username));
```

In the example above, the text corresponding to the key "Greet" is retrieved and formatted with the `username` variable. The localized text can be used as needed in your application.

## Contributing

Contributions to TextLocale are welcome! If you find any issues or have suggestions for improvements, please submit them on the project's GitHub repository.

Before submitting a pull request, please ensure that you have tested your changes and they pass the existing unit tests. Additionally, consider adding new tests to cover the modified or added functionality.

### Roadmap

- [ ] Add support for custom parsers and files formats.
- [ ] Create tool for creating and managing localized texts.

## License

TextLocale is released under the [MIT License](https://opensource.org/licenses/MIT).
