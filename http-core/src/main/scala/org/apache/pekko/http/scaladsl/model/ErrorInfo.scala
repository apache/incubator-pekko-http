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

package org.apache.pekko.http.scaladsl.model

import StatusCodes.ClientError
import org.apache.pekko.annotation.InternalApi

/**
 * Two-level model of error information.
 * The summary should explain what is wrong with the request or response *without* directly
 * repeating anything present in the message itself (in order to not open holes for XSS attacks),
 * while the detail can contain additional information from any source (even the request itself).
 */
final class ErrorInfo(
    val summary: String = "",
    val detail: String = "",
    val errorHeaderName: String = "") extends scala.Product with scala.Equals with java.io.Serializable {
  def withSummary(newSummary: String) = copy(summary = newSummary)
  def withSummaryPrepended(prefix: String) = withSummary(if (summary.isEmpty) prefix else prefix + ": " + summary)
  def withErrorHeaderName(headerName: String) = new ErrorInfo(summary, detail, headerName.toLowerCase)
  def withFallbackSummary(fallbackSummary: String) = if (summary.isEmpty) withSummary(fallbackSummary) else this
  def formatPretty = if (summary.isEmpty) detail else if (detail.isEmpty) summary else summary + ": " + detail
  def format(withDetail: Boolean): String = if (withDetail) formatPretty else summary

  /** INTERNAL API */
  @InternalApi private[pekko] def copy(summary: String = summary, detail: String = detail): ErrorInfo = {
    new ErrorInfo(summary, detail, errorHeaderName)
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[ErrorInfo]

  override def equals(that: Any): Boolean = that match {
    case that: ErrorInfo => that.canEqual(
        this) && that.summary == this.summary && that.detail == this.detail && that.errorHeaderName == this.errorHeaderName
    case _ => false
  }

  override def productElement(n: Int): Any = n match {
    case 0 => summary
    case 1 => detail
    case 2 => errorHeaderName
  }

  override def productArity: Int = 3

  /** INTERNAL API */
  @InternalApi private[pekko] def this(summary: String, detail: String) = this(summary, detail, "")

  override def toString(): String = s"ErrorInfo($summary, (details omitted), $errorHeaderName)"
}

object ErrorInfo {

  /** INTERNAL API */
  @InternalApi private[pekko] def apply(summary: String = "", detail: String = ""): ErrorInfo =
    new ErrorInfo(summary, detail, "")

  def unapply(arg: ErrorInfo): Option[(String, String)] = Some((arg.summary, arg.detail))

  /**
   * Allows constructing an `ErrorInfo` from a single string.
   * Used for example when catching exceptions generated by the header value parser, which doesn't provide
   * summary/details information but structures its exception messages accordingly.
   */
  def fromCompoundString(message: String): ErrorInfo = message.split(": ", 2) match {
    case Array(summary, detail) => apply(summary, detail)
    case _                      => ErrorInfo("", message)
  }
}

/** Marker for exceptions that provide an ErrorInfo */
abstract class ExceptionWithErrorInfo(
    val info: ErrorInfo, cause: Throwable) extends RuntimeException(info.formatPretty, cause) {
  def this(info: ErrorInfo) = this(info, null)
}

case class IllegalUriException(override val info: ErrorInfo) extends ExceptionWithErrorInfo(info)
object IllegalUriException {
  def apply(summary: String, detail: String = ""): IllegalUriException = apply(ErrorInfo(summary, detail))
}

case class IllegalHeaderException(override val info: ErrorInfo) extends ExceptionWithErrorInfo(info)
object IllegalHeaderException {
  def apply(summary: String, detail: String = ""): IllegalHeaderException = apply(ErrorInfo(summary, detail))
}

case class InvalidContentLengthException(override val info: ErrorInfo) extends ExceptionWithErrorInfo(info)
object InvalidContentLengthException {
  def apply(summary: String, detail: String = ""): InvalidContentLengthException = apply(ErrorInfo(summary, detail))
}

case class ParsingException(override val info: ErrorInfo) extends ExceptionWithErrorInfo(info)
object ParsingException {
  def apply(summary: String, detail: String = ""): ParsingException = apply(ErrorInfo(summary, detail))
}

case class IllegalRequestException(override val info: ErrorInfo, status: ClientError)
    extends ExceptionWithErrorInfo(info)
object IllegalRequestException {
  def apply(status: ClientError): IllegalRequestException = apply(ErrorInfo(status.defaultMessage), status)
  def apply(status: ClientError, info: ErrorInfo): IllegalRequestException =
    apply(info.withFallbackSummary(status.defaultMessage), status)
  def apply(status: ClientError, detail: String): IllegalRequestException =
    apply(ErrorInfo(status.defaultMessage, detail), status)
}

case class IllegalResponseException(override val info: ErrorInfo) extends ExceptionWithErrorInfo(info)
object IllegalResponseException {
  def apply(summary: String, detail: String = ""): IllegalResponseException = apply(ErrorInfo(summary, detail))
}

case class EntityStreamException(override val info: ErrorInfo) extends ExceptionWithErrorInfo(info)
object EntityStreamException {
  def apply(summary: String, detail: String = ""): EntityStreamException = apply(ErrorInfo(summary, detail))
}

/**
 * This exception is thrown when the size of the HTTP Entity exceeds the configured limit.
 * It is possible to configure the limit using configuration options `pekko.http.parsing.max-content-length`
 * or specifically for the server or client side by setting `pekko.http.[server|client].parsing.max-content-length`.
 *
 * The limit can also be configured in code, by calling [[HttpEntity#withSizeLimit]]
 * on the entity before materializing its `dataBytes` stream.
 */
final case class EntityStreamSizeException(limit: Long, actualSize: Option[Long] = None) extends RuntimeException {

  override def getMessage = toString

  override def toString = {
    s"EntityStreamSizeException: incoming entity size (${actualSize.getOrElse("while streaming")}) exceeded size limit ($limit bytes)! " +
    "This may have been a parser limit (set via `pekko.http.[server|client].parsing.max-content-length`), " +
    "a decoder limit (set via `pekko.http.routing.decode-max-size`), " +
    "or a custom limit set with `withSizeLimit`."
  }
}

case class RequestTimeoutException(request: HttpRequest, message: String) extends RuntimeException(message)
