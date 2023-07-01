/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package docs.http.javadsl.server.cors;

// #cors-server-example
import org.apache.pekko.http.javadsl.model.StatusCodes;
import org.apache.pekko.http.javadsl.server.*;

import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.apache.pekko.http.cors.javadsl.CorsDirectives.cors;
import static org.apache.pekko.http.cors.javadsl.CorsDirectives.corsRejectionHandler;

public class CorsServerExample extends HttpApp {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    final CorsServerExample app = new CorsServerExample();
    app.startServer("127.0.0.1", 9000);
  }

  protected Route routes() {

    // Your CORS settings are loaded from `application.conf`

    // Your rejection handler
    final RejectionHandler rejectionHandler =
        corsRejectionHandler().withFallback(RejectionHandler.defaultHandler());

    // Your exception handler
    final ExceptionHandler exceptionHandler =
        ExceptionHandler.newBuilder()
            .match(
                NoSuchElementException.class,
                ex -> complete(StatusCodes.NOT_FOUND, ex.getMessage()))
            .build();

    // Combining the two handlers only for convenience
    final Function<Supplier<Route>, Route> handleErrors =
        inner ->
            Directives.allOf(
                s -> handleExceptions(exceptionHandler, s),
                s -> handleRejections(rejectionHandler, s),
                inner);

    // Note how rejections and exceptions are handled *before* the CORS directive (in the inner
    // route).
    // This is required to have the correct CORS headers in the response even when an error occurs.
    return handleErrors.apply(
        () ->
            cors(
                () ->
                    handleErrors.apply(
                        () ->
                            concat(
                                path("ping", () -> complete("pong")),
                                path(
                                    "pong",
                                    () ->
                                        failWith(
                                            new NoSuchElementException(
                                                "pong not found, try with ping")))))));
  }
}
// #cors-server-example
