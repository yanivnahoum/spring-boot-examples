## Spring Boot Converters

Usually we deal with deserializing the body of http requests from json to a POJO using `@RequestBody` and Jackson.
But when dealing with query params annotated with `@RequestParam`, we need to convert the String values to the desired type.
Basic conversion from String to number types, Enums, and DateTime types are supported out of the box.
But what if we want to convert to a custom type? This is where Spring Boot Converters come in handy.

### How to use
Had we wanted the default enum conversion, this would work:
`http ':8080/demo/api?color=RED'`
But our api required the color to be lowercase, so we created a custom converter to convert the String to a `Color` object:
Run `http ':8080/demo/api?color=red'` to see the color being converted to a `Color` object.
