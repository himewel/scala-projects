package com.himewel.circe

import io.circe.{Json, Encoder, Decoder, HCursor}
import io.circe.parser.decode
import io.circe.syntax.*

case class FooBar(bar: Boolean, baz: Float, qux: Seq[String])
object FooBar {
  given decodeFooBar: Decoder[FooBar] =
    Decoder.instance(c =>
      for {
        bar <- c.downField("bar").as[Boolean]
        baz <- c.downField("baz").as[Float]
        qux <- c.downField("qux").as[Seq[String]]
      } yield FooBar(bar, baz, qux)
    )

  given encodeFooBar: Encoder[FooBar] =
    Encoder.instance(c =>
      Json.obj(
        "bar" -> c.bar.asJson,
        "baz" -> c.baz.asJson,
        "qux" -> c.qux.asJson
      )
    )
}

case class JsonObject(id: String, name: String, counts: Seq[Int], values: FooBar)
object JsonObject {
  given decodeObject: Decoder[JsonObject] =
    Decoder.instance(c =>
      for {
        id <- c.downField("id").as[String]
        name <- c.downField("name").as[String]
        counts <- c.downField("counts").as[Seq[Int]]
        values <- c.downField("values").as[FooBar]
      } yield JsonObject(id, name, counts, values)
    )

  given encodeObject: Encoder[JsonObject] =
    Encoder.instance(c =>
      Json.obj(
        "id" -> c.id.asJson,
        "name" -> c.name.asJson,
        "counts" -> c.counts.asJson,
        "values" -> c.values.asJson
      )
    )
}

object JsonCodec {
  def apply(): Unit = {
    val json: String = """
      {
        "id": "c730433b-082c-4984-9d66-855c243266f0",
        "name": "Foo",
        "counts": [1, 2, 3],
        "values": {
          "bar": true,
          "baz": 100.001,
          "qux": ["a", "b"]
        }
      }
    """

    val doc = decode[JsonObject](json) match {
      case Left(error)  => println(error)
      case Right(value) => println(value.asJson)
    }
  }
}
