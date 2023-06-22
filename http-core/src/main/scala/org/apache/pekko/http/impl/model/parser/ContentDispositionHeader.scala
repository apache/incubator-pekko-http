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

package org.apache.pekko.http.impl.model.parser

import scala.collection.immutable.TreeMap

import org.apache.pekko
import org.parboiled2.Parser
import pekko.http.scaladsl.model.headers._
import pekko.http.impl.util.ISO88591
import pekko.http.impl.util.UTF8
import pekko.http.impl.model.parser.CharacterClasses.`attr-char`
import pekko.http.impl.model.parser.CharacterClasses.HEXDIG
import pekko.http.scaladsl.model.Uri

import java.nio.charset.Charset

private[parser] trait ContentDispositionHeader { this: Parser with CommonRules with CommonActions =>

  // http://tools.ietf.org/html/rfc6266#section-4.1
  def `content-disposition` = rule {
    `disposition-type` ~ zeroOrMore(ws(';') ~ `disposition-parm`) ~ EOI ~> { p =>
      val all = TreeMap(p: _*)
      // https://tools.ietf.org/html/rfc6266#section-4.3
      // when both "filename" and "filename*" are present in a single header field value,
      //   recipients SHOULD pick "filename*" and ignore "filename"
      all.get("filename*").map(fExt => all - "filename*" + ("filename" -> fExt)).getOrElse(all)
    } ~> (`Content-Disposition`(_, _))
  }

  def `disposition-type` = rule(
    ignoreCase("inline") ~ OWS ~ push(ContentDispositionTypes.inline)
    | ignoreCase("attachment") ~ OWS ~ push(ContentDispositionTypes.attachment)
    | ignoreCase("form-data") ~ OWS ~ push(ContentDispositionTypes.`form-data`)
    | `disp-ext-type` ~> (ContentDispositionTypes.Ext(_)))

  def `disp-ext-type` = rule { token }

  def `disposition-parm` = rule { (`filename-parm` | `disp-ext-parm`) ~> (_ -> _) }

  def `filename-parm` = rule(
    ignoreCase("filename") ~ OWS ~ ws('=') ~ push("filename") ~ word
    | ignoreCase("filename*") ~ OWS ~ ws('=') ~ push("filename*") ~ `ext-value`)

  def `disp-ext-parm` = rule(
    token ~ ws('=') ~ word
    | `ext-token` ~ ws('=') ~ `ext-value`)

  def `ext-token` = rule { // token which ends with '*'
    token ~> (s => test(s.endsWith("*")) ~ push(s))
  }

  // https://tools.ietf.org/html/rfc5987#section-3.2.1
  def `ext-value` = rule {
    (charset ~ '\'' ~ optional(language) ~ '\'' ~ capture(`value-chars`)) ~> (decodeExtValue(_, _, _))
  }

  def charset = rule {
    ignoreCase("utf-8") ~ push(UTF8) |
    ignoreCase("iso-8859-1") ~ push(ISO88591)
    // | `mime-charset` // reserved for future use
  }

  def `value-chars` = rule {
    zeroOrMore(`pct-encoded` | `attr-char`)
  }

  def `pct-encoded` = rule {
    '%' ~ HEXDIG ~ HEXDIG
  }

  def decodeExtValue(cs: Charset, language: Option[Language], extValue: String): String =
    Uri.decode(extValue, cs)
}
