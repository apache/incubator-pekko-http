/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * license agreements; and to You under the Apache License, version 2.0:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * This file is part of the Apache Pekko project, which was derived from Akka.
 */

/*
 * Copyright (C) 2009-2022 Lightbend Inc. <https://www.lightbend.com>
 */

package docs.http.javadsl.server;

import org.apache.pekko.http.javadsl.model.RemoteAddress;
import org.apache.pekko.http.javadsl.server.Route;
import org.apache.pekko.http.javadsl.testkit.JUnitRouteTest;
import org.junit.Test;

import java.util.function.Function;
import java.util.function.Supplier;

// #example1
import static org.apache.pekko.http.javadsl.server.Directives.complete;
import static org.apache.pekko.http.javadsl.server.Directives.get;
import static org.apache.pekko.http.javadsl.server.Directives.path;
import static org.apache.pekko.http.javadsl.server.Directives.put;

import static org.apache.pekko.http.javadsl.server.PathMatchers.integerSegment;
import static org.apache.pekko.http.javadsl.server.PathMatchers.segment;

// #example1
// #usingConcat
import static org.apache.pekko.http.javadsl.server.Directives.complete;
import static org.apache.pekko.http.javadsl.server.Directives.get;
import static org.apache.pekko.http.javadsl.server.Directives.path;
import static org.apache.pekko.http.javadsl.server.Directives.put;
import static org.apache.pekko.http.javadsl.server.PathMatchers.integerSegment;
import static org.apache.pekko.http.javadsl.server.PathMatchers.segment;

// #usingConcat
// #usingConcatBig
import static org.apache.pekko.http.javadsl.server.Directives.complete;
import static org.apache.pekko.http.javadsl.server.Directives.get;
import static org.apache.pekko.http.javadsl.server.Directives.head;
import static org.apache.pekko.http.javadsl.server.Directives.path;
import static org.apache.pekko.http.javadsl.server.Directives.put;
import static org.apache.pekko.http.javadsl.server.PathMatchers.integerSegment;
import static org.apache.pekko.http.javadsl.server.PathMatchers.segment;

// #usingConcatBig

// #getOrPut
import static org.apache.pekko.http.javadsl.server.Directives.complete;
import static org.apache.pekko.http.javadsl.server.Directives.extractMethod;
import static org.apache.pekko.http.javadsl.server.Directives.get;
import static org.apache.pekko.http.javadsl.server.Directives.path;
import static org.apache.pekko.http.javadsl.server.Directives.put;
import static org.apache.pekko.http.javadsl.server.PathMatchers.integerSegment;
import static org.apache.pekko.http.javadsl.server.PathMatchers.segment;

// #getOrPut
// #getOrPutUsingAnyOf
import org.apache.pekko.http.javadsl.server.Directives;

import static org.apache.pekko.http.javadsl.server.Directives.anyOf;

import static org.apache.pekko.http.javadsl.server.PathMatchers.integerSegment;
import static org.apache.pekko.http.javadsl.server.PathMatchers.segment;

// #getOrPutUsingAnyOf
// #composeNesting
import static org.apache.pekko.http.javadsl.server.Directives.complete;
import static org.apache.pekko.http.javadsl.server.Directives.extractClientIP;
import static org.apache.pekko.http.javadsl.server.Directives.get;
import static org.apache.pekko.http.javadsl.server.Directives.path;

// #composeNesting
// #allOf
import org.apache.pekko.http.javadsl.server.Directives;

import static org.apache.pekko.http.javadsl.server.Directives.complete;
import static org.apache.pekko.http.javadsl.server.Directives.allOf;
import static org.apache.pekko.http.javadsl.server.Directives.path;

// #allOf

public class DirectiveExamplesTest extends JUnitRouteTest {

  @Test
  public void compileOnlySpec() throws Exception {
    // just making sure for it to be really compiled / run even if empty
  }

  // #example1
  Route orElse() {
    return path(
        segment("order").slash(integerSegment()),
        id ->
            get(() -> complete("Received GET request for order " + id))
                .orElse(put(() -> complete("Received PUT request for order " + id))));
  }
  // #example1

  // #usingConcat
  Route usingConcat() {
    return path(
        segment("order").slash(integerSegment()),
        id ->
            concat(
                get(() -> complete("Received GET request for order " + id)),
                put(() -> complete("Received PUT request for order " + id))));
  }
  // #usingConcat

  // #usingConcatBig
  Route multipleRoutes() {
    return path(
        segment("order").slash(integerSegment()),
        id ->
            concat(
                get(() -> complete("Received GET request for order " + id)),
                put(() -> complete("Received PUT request for order " + id)),
                head(() -> complete("Received HEAD request for order " + id))));
  }
  // #usingConcatBig

  // #getOrPut
  Route getOrPut(Supplier<Route> inner) {
    return get(inner).orElse(put(inner));
  }

  Route customDirective() {
    return path(
        segment("order").slash(integerSegment()),
        id ->
            getOrPut(
                () ->
                    extractMethod(method -> complete("Received " + method + " for order " + id))));
  }
  // #getOrPut

  // #getOrPutUsingAnyOf
  Route usingAnyOf() {
    return path(
        segment("order").slash(integerSegment()),
        id ->
            anyOf(
                Directives::get,
                Directives::put,
                () ->
                    extractMethod(method -> complete("Received " + method + " for order " + id))));
  }
  // #getOrPutUsingAnyOf

  // #composeNesting
  Route getWithIP(Function<RemoteAddress, Route> inner) {
    return get(() -> extractClientIP(address -> inner.apply(address)));
  }

  Route complexRoute() {
    return path(
        segment("order").slash(integerSegment()),
        id ->
            getWithIP(
                address -> complete("Received request for order " + id + " from IP " + address)));
  }
  // #composeNesting

  // #composeNestingAllOf
  Route complexRouteUsingAllOf() {
    return path(
        segment("order").slash(integerSegment()),
        id ->
            allOf(
                Directives::get,
                Directives::extractClientIP,
                address -> complete("Received request for order " + id + " from IP " + address)));
  }
  // #composeNestingAllOf
}
