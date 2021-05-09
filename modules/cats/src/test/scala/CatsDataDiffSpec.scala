import munit.FunSuite
import CatsInstances._
import cats.data._
import difflicious.Differ
import difflicious.Differ.{SetDiffer, SeqDiffer}
import difflicious.testtypes.CC
import difflicious.testutils._

class CatsDataDiffSpec extends FunSuite {
  test("NonEmptyMap: Has map-like diff result") {
    assertConsoleDiffOutput(
      Differ[NonEmptyMap[String, CC]],
      NonEmptyMap.of(
        "a" -> CC(1, "1", 1),
        "b" -> CC(2, "2", 2),
      ),
      NonEmptyMap.of(
        "a" -> CC(1, "x", 1),
        "b" -> CC(2, "2", 2),
        "c" -> CC(1, "x", 1),
      ),
      s"""NonEmptyMap(
         |  "a" -> CC(
         |      i: 1,
         |      s: $R"1"$X -> $G"x"$X,
         |      dd: 1.0,
         |    ),
         |  "b" -> CC(
         |      i: 2,
         |      s: "2",
         |      dd: 2.0,
         |    ),
         |  $G"c"$X -> ${G}CC(
         |      i: 1,
         |      s: "x",
         |      dd: 1.0,
         |    )$X,
         |)""".stripMargin,
    )
  }
  /*
  FIXME
  test("NonEmptyMap: Prop: isOk if equals") {
    assertOkIfValuesEqualProp[NonEmptyMap[MapKey, CC]](implicitly)
  }

  test("NonEmptyMap: Prop: isOk == false if not equal") {
    assertNotOkIfNotEqualProp[NonEmptyMap[MapKey, CC]](implicitly)
  }
   */

  test("NonEmptyList: Has list-like diff result") {
    assertConsoleDiffOutput(
      Differ[NonEmptyList[CC]],
      NonEmptyList.of(
        CC(1, "1", 1),
        CC(2, "2", 2),
      ),
      NonEmptyList.of(
        CC(1, "2", 1),
        CC(2, "2", 2),
        CC(1, "x", 1),
      ),
      s"""NonEmptyList(
         |  CC(
         |    i: 1,
         |    s: $R"1"$X -> $G"2"$X,
         |    dd: 1.0,
         |  ),
         |  CC(
         |    i: 2,
         |    s: "2",
         |    dd: 2.0,
         |  ),
         |  ${G}CC(
         |    i: 1,
         |    s: "x",
         |    dd: 1.0,
         |  )$X,
         |)""".stripMargin,
    )
  }

  test("NonEmptyList: matchBy") {
    assertConsoleDiffOutput(
      implicitly[SeqDiffer[NonEmptyList, CC]].matchBy(_.s),
      NonEmptyList.of(
        CC(1, "1", 1),
        CC(2, "2", 2),
      ),
      NonEmptyList.of(
        CC(2, "1", 2),
        CC(2, "3", 2),
      ),
      s"""NonEmptyList(
         |  CC(
         |    i: ${R}1$X -> ${G}2$X,
         |    s: "1",
         |    dd: ${R}1.0$X -> ${G}2.0$X,
         |  ),
         |  ${R}CC(
         |    i: 2,
         |    s: "2",
         |    dd: 2.0,
         |  )$X,
         |  ${G}CC(
         |    i: 2,
         |    s: "3",
         |    dd: 2.0,
         |  )$X,
         |)""".stripMargin,
    )
  }

  test("NonEmptyVector: Has list-like diff result") {
    assertConsoleDiffOutput(
      Differ[NonEmptyVector[CC]],
      NonEmptyVector.of(
        CC(1, "1", 1),
        CC(2, "2", 2),
      ),
      NonEmptyVector.of(
        CC(1, "2", 1),
        CC(2, "2", 2),
        CC(1, "x", 1),
      ),
      s"""NonEmptyVector(
         |  CC(
         |    i: 1,
         |    s: $R"1"$X -> $G"2"$X,
         |    dd: 1.0,
         |  ),
         |  CC(
         |    i: 2,
         |    s: "2",
         |    dd: 2.0,
         |  ),
         |  ${G}CC(
         |    i: 1,
         |    s: "x",
         |    dd: 1.0,
         |  )$X,
         |)""".stripMargin,
    )
  }

  test("NonEmptyVector: matchBy") {
    assertConsoleDiffOutput(
      implicitly[SeqDiffer[NonEmptyVector, CC]].matchBy(_.s),
      NonEmptyVector.of(
        CC(1, "1", 1),
        CC(2, "2", 2),
      ),
      NonEmptyVector.of(
        CC(2, "1", 2),
        CC(2, "3", 2),
      ),
      s"""NonEmptyVector(
         |  CC(
         |    i: ${R}1$X -> ${G}2$X,
         |    s: "1",
         |    dd: ${R}1.0$X -> ${G}2.0$X,
         |  ),
         |  ${R}CC(
         |    i: 2,
         |    s: "2",
         |    dd: 2.0,
         |  )$X,
         |  ${G}CC(
         |    i: 2,
         |    s: "3",
         |    dd: 2.0,
         |  )$X,
         |)""".stripMargin,
    )
  }

  test("Chain: Has list-like diff result") {
    assertConsoleDiffOutput(
      Differ[Chain[CC]],
      Chain(
        CC(1, "1", 1),
        CC(2, "2", 2),
      ),
      Chain(
        CC(1, "2", 1),
        CC(2, "2", 2),
        CC(1, "x", 1),
      ),
      s"""Chain(
         |  CC(
         |    i: 1,
         |    s: $R"1"$X -> $G"2"$X,
         |    dd: 1.0,
         |  ),
         |  CC(
         |    i: 2,
         |    s: "2",
         |    dd: 2.0,
         |  ),
         |  ${G}CC(
         |    i: 1,
         |    s: "x",
         |    dd: 1.0,
         |  )$X,
         |)""".stripMargin,
    )
  }

  test("Chain: matchBy") {
    assertConsoleDiffOutput(
      implicitly[SeqDiffer[Chain, CC]].matchBy(_.s),
      Chain(
        CC(1, "1", 1),
        CC(2, "2", 2),
      ),
      Chain(
        CC(2, "1", 2),
        CC(2, "3", 2),
      ),
      s"""Chain(
         |  CC(
         |    i: ${R}1$X -> ${G}2$X,
         |    s: "1",
         |    dd: ${R}1.0$X -> ${G}2.0$X,
         |  ),
         |  ${R}CC(
         |    i: 2,
         |    s: "2",
         |    dd: 2.0,
         |  )$X,
         |  ${G}CC(
         |    i: 2,
         |    s: "3",
         |    dd: 2.0,
         |  )$X,
         |)""".stripMargin,
    )
  }

  test("NonEmptyChain: Has list-like diff result") {
    assertConsoleDiffOutput(
      Differ[NonEmptyChain[CC]],
      NonEmptyChain(
        CC(1, "1", 1),
        CC(2, "2", 2),
      ),
      NonEmptyChain(
        CC(1, "2", 1),
        CC(2, "2", 2),
        CC(1, "x", 1),
      ),
      s"""NonEmptyChain(
         |  CC(
         |    i: 1,
         |    s: $R"1"$X -> $G"2"$X,
         |    dd: 1.0,
         |  ),
         |  CC(
         |    i: 2,
         |    s: "2",
         |    dd: 2.0,
         |  ),
         |  ${G}CC(
         |    i: 1,
         |    s: "x",
         |    dd: 1.0,
         |  )$X,
         |)""".stripMargin,
    )
  }

  test("NonEmptyChain: matchBy") {
    assertConsoleDiffOutput(
      implicitly[SeqDiffer[NonEmptyChain, CC]].matchBy(_.s),
      NonEmptyChain(
        CC(1, "1", 1),
        CC(2, "2", 2),
      ),
      NonEmptyChain(
        CC(2, "1", 2),
        CC(2, "3", 2),
      ),
      s"""NonEmptyChain(
         |  CC(
         |    i: ${R}1$X -> ${G}2$X,
         |    s: "1",
         |    dd: ${R}1.0$X -> ${G}2.0$X,
         |  ),
         |  ${R}CC(
         |    i: 2,
         |    s: "2",
         |    dd: 2.0,
         |  )$X,
         |  ${G}CC(
         |    i: 2,
         |    s: "3",
         |    dd: 2.0,
         |  )$X,
         |)""".stripMargin,
    )
  }

  test("NonEmptySet: Has set-like diff result") {
    assertConsoleDiffOutput(
      Differ[NonEmptySet[CC]],
      NonEmptySet.of(
        CC(1, "1", 1),
        CC(2, "2", 2),
      ),
      NonEmptySet.of(
        CC(1, "2", 1),
        CC(2, "2", 2),
        CC(1, "x", 1),
      ),
      s"""NonEmptySet(
         |  ${R}CC(
         |    i: 1,
         |    s: "1",
         |    dd: 1.0,
         |  )$X,
         |  CC(
         |    i: 2,
         |    s: "2",
         |    dd: 2.0,
         |  ),
         |  ${G}CC(
         |    i: 1,
         |    s: "2",
         |    dd: 1.0,
         |  )$X,
         |  ${G}CC(
         |    i: 1,
         |    s: "x",
         |    dd: 1.0,
         |  )$X,
         |)""".stripMargin,
    )
  }

  test("NonEmptySet: with matchBy") {
    assertConsoleDiffOutput(
      implicitly[SetDiffer[NonEmptySet, CC]].matchBy(_.i),
      NonEmptySet.of(
        CC(1, "1", 1),
        CC(2, "2", 2),
      ),
      NonEmptySet.of(
        CC(1, "2", 1),
        CC(3, "3", 3),
      ),
      s"""NonEmptySet(
         |  CC(
         |    i: 1,
         |    s: $R"1"$X -> $G"2"$X,
         |    dd: 1.0,
         |  ),
         |  ${R}CC(
         |    i: 2,
         |    s: "2",
         |    dd: 2.0,
         |  )$X,
         |  ${G}CC(
         |    i: 3,
         |    s: "3",
         |    dd: 3.0,
         |  )$X,
         |)""".stripMargin,
    )
  }

}