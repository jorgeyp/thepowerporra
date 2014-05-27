package utils

import org.joda.time._
import org.joda.time.format._
import anorm._

/**
 * Extensión de Anorm para parsear LocalDate
 */
object AnormExtension {

  val dateFormatGeneration: DateTimeFormatter = DateTimeFormat.forPattern("yyyyMMddHHmmssSS");

  implicit def rowToLocalDate: Column[LocalDate] = Column.nonNull {
    (value, meta) =>
      val MetaDataItem(qualified, nullable, clazz) = meta
      value match {
        case ts: java.sql.Timestamp => Right(new LocalDate(ts.getTime))
        case d: java.sql.Date => Right(new LocalDate(d.getTime))
        case str: java.lang.String => Right(dateFormatGeneration.parseLocalDate(str))
        case _ => Left(TypeDoesNotMatch("Cannot convert " + value + ":" + value.asInstanceOf[AnyRef].getClass))
      }
  }

  implicit def localDateToStatement: ToStatement[LocalDate] = new ToStatement[LocalDate] {
    def set(s: java.sql.PreparedStatement, index: Int, aValue: LocalDate) {
      s.setDate(index, new java.sql.Date(aValue.toDate.getTime))
    }
  }
}
