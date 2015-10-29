package chandu0101.reindexjs

import org.scalatest._

import scala.concurrent.ExecutionContext
import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => json}

class ReindexTest extends AsyncFunSuite with BeforeAndAfter {

  var ri: Reindex = null

  before(
    ri = new Reindex("https://integrated-branch-17.myreindex.com")
  )

  test("check is Loggedin") {
    assert(!ri.isLoggedIn())
  }

  test("test query and promise to future") {
    val x = ri.query("query { viewer {id} }", json())
    x.map((resp: js.Dynamic) => assert(!js.isUndefined(resp.data.viewer.id)))
  }

  override implicit def executionContext: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  override def newInstance: Suite with OneInstancePerTest = new ReindexTest
}
