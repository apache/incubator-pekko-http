# Exception Handling

Exceptions thrown during route execution bubble up through the route structure to the next enclosing
@ref[handleExceptions](directives/execution-directives/handleExceptions.md) directive or the top of your route structure.

Similarly to the way that @ref[Rejections](rejections.md) are handled the @ref[handleExceptions](directives/execution-directives/handleExceptions.md) directive delegates the actual job
of converting an exception to its argument, an @apidoc[ExceptionHandler]@scala[, which is defined like this:]@java[.]

@@@ div { .group-scala }
```scala
trait ExceptionHandler extends PartialFunction[Throwable, Route]
```
@@@

Since an @apidoc[ExceptionHandler] is a partial function, it can choose which exceptions it would like to handle and
which not. Unhandled exceptions will simply continue to bubble up in the route structure.
At the root of the route tree any still unhandled exception will be dealt with by the top-level handler which always
handles *all* exceptions.

`Route.seal` internally wraps its argument route with the @ref[handleExceptions](directives/execution-directives/handleExceptions.md) directive in order to "catch" and
handle any exception.

So, if you'd like to customize the way certain exceptions are handled you need to write a custom @apidoc[ExceptionHandler].
Once you have defined your custom @apidoc[ExceptionHandler] you have two options for "activating" it:

 1. @scala[Bring it into implicit scope at the top-level.]@java[Pass it to the `seal()` method of the @javadoc[Route](org.apache.pekko.http.javadsl.server.Route)]
 2. Supply it as argument to the @ref[handleExceptions](directives/execution-directives/handleExceptions.md) directive.

In the first case your handler will be "sealed" (which means that it will receive the default handler as a fallback for
all cases your handler doesn't handle itself) and used for all exceptions that are not handled within the route
structure itself.
Here you can see an example of it:

Scala
:   @@snip [ExceptionHandlerExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/ExceptionHandlerExamplesSpec.scala) { #seal-handler-example }

Java
:   @@snip [ExceptionHandlerExamplesTest.java](/docs/src/test/java/docs/http/javadsl/ExceptionHandlerInSealExample.java) { #seal-handler-example }


The second case allows you to restrict the applicability of your handler to certain branches of your route structure.

Here is an example for wiring up a custom handler via @ref[handleExceptions](directives/execution-directives/handleExceptions.md):

Scala
:   @@snip [ExceptionHandlerExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/ExceptionHandlerExamplesSpec.scala) { #explicit-handler-example }

Java
:   @@snip [ExceptionHandlerExamplesTest.java](/docs/src/test/java/docs/http/javadsl/ExceptionHandlerExample.java) { #explicit-handler-example }

@@@ div { .group-scala }
And this is how to do it implicitly:

@@snip [ExceptionHandlerExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/ExceptionHandlerExamplesSpec.scala) { #implicit-handler-example }
@@@

## Default Exception Handler

A default @apidoc[ExceptionHandler] is used if no custom instance is provided.

It will handle every `NonFatal` throwable, write its stack trace and complete the request
with `InternalServerError` `(500)` status code.

The message body will contain a string obtained via `Throwable#getMessage` call on the exception caught.

In case `getMessage` returns `null` (which is true for e.g. `NullPointerException` instances),
the class name and a remark about the message being null are included in the response body.

Note that `IllegalRequestException`s' stack traces are not logged, since instances of this class
normally contain enough information to provide a useful error message.

@@@ note

Users are strongly encouraged not to rely on using the @apidoc[ExceptionHandler] as a means of handling errors. By errors, we mean things that are an expected part of normal operations: for example, issues discovered during input validation. The @apidoc[ExceptionHandler] is meant to be a means of handling failures. See [Failure vs Error](https://www.reactivemanifesto.org/glossary#Failure) in the glossary of the [Reactive Manifesto](https://www.reactivemanifesto.org).

Distinguishing between errors and failures (i.e. thrown `Exceptions` handled via the @apidoc[ExceptionHandler]) provides a much better mental model but also leads to performance improvements.

This is because exceptions are known to have a negative performance impact for cases
when the depth of the call stack is significant (stack trace construction cost)
and when the handler is located far from the place of the throwable instantiation (stack unwinding costs).

In a typical Apache Pekko application both these conditions are frequently true,
so as a rule of thumb, you should try to minimize the number of `Throwable` instances
reaching the exception handler.

To understand the performance implications of (mis-)using exceptions,
have a read at this excellent post by A. Shipilёv: [The Exceptional Performance of Lil' Exception](https://shipilev.net/blog/2014/exceptional-performance/).
@@@


@@@ note
Please note that the default `ExceptionHandler` will also discard the entity bytes automatically. If you want to change this behavior,
please refer to @ref[the section above](exception-handling.md#exception-handling); however, might cause connections to stall
if the entity is not properly rejected or cancelled on the client side.
@@@

## Including sensitive data in exceptions

To prevent certain types of attack, it is not recommended to include arbitrary invalid user input in the response.
However, sometimes it can be useful to include it in the exception and logging for diagnostic reasons.
In such cases, you can use exceptions that extend `ExceptionWithErrorInfo`, such as `IllegalHeaderException`:

Scala
:   @@snip [ExceptionHandlerExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/ExceptionHandlerExamplesSpec.scala) { #no-exception-details-in-response }

Java
:   @@snip [ExceptionHandlerExamplesTest.java](/docs/src/test/java/docs/http/javadsl/RespondWithHeaderHandlerExampleTest.java) { #no-exception-details-in-response  }


## Respond with headers and Exception Handler

If you wrap an ExceptionHandler inside a different directive, then that directive will still apply. Example below shows
that wrapping an ExceptionHandler inside a respondWithHeader directive will still add the header to the response.   

Scala
:   @@snip [ExceptionHandlerExamplesSpec.scala](/docs/src/test/scala/docs/http/scaladsl/server/ExceptionHandlerExamplesSpec.scala) { #respond-with-header-exceptionhandler-example }

Java
:   @@snip [ExceptionHandlerExamplesTest.java](/docs/src/test/java/docs/http/javadsl/RespondWithHeaderHandlerExampleTest.java) { #respond-with-header-exceptionhandler-example  }
