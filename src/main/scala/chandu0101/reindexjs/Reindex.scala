package chandu0101.reindexjs

import org.scalajs.dom.raw.{Promise => JPromise}

import scala.concurrent.{Future, Promise}
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSName, ScalaJSDefined}


@js.native
trait EventEmitter extends js.Object {

  def on(event: String, listener: js.Function): Unit = js.native

  def off(event: String, listener: js.Function): Unit = js.native

}

case class ReindexException(err: js.Dynamic) extends Exception

@js.native
@JSName("Reindex")
class ReindexJS(var url: String) extends EventEmitter {

  @JSName("login") def loginJS(providerName: String): JPromise[js.Dynamic] = js.native

  @JSName("logout") def logoutJS(): JPromise[js.Dynamic] = js.native

  def isLoggedIn(): Boolean = js.native

  def setToken(token: String): Unit = js.native

  @JSName("query") def queryJS(query: String, variables: js.Object): JPromise[js.Dynamic] = js.native

  def getAuthenticationHeaders(): js.Object = js.native

  def getRelayNetworkLayer(timeout: Int, retryDelays: Int): ReindexRelayNetWorkLayer = js.native

}

@js.native
trait ReindexRelayNetWorkLayer extends js.Object

@ScalaJSDefined
class Reindex(url: String) extends ReindexJS(url) {

  def onLogin(listener: js.Function1[js.Dynamic, _]): Unit = on("login", listener)

  def onLogout(listener: js.Function0[_]): Unit = on("logout", listener)

  def onTokenChange(listener: js.Function1[String, _]): Unit = on("tokenChange", listener)

  @JSName("slogin")
  def login(providerName: String): Future[js.Dynamic] = {
    wrapJSPromise(loginJS(providerName))
  }

  @JSName("slogout")
  def logout(providerName: String): Future[js.Dynamic] = {
    wrapJSPromise(logoutJS())
  }

  @JSName("squery")
  def query(query: String, variables: js.Object): Future[js.Dynamic] = {
    wrapJSPromise(queryJS(query, variables))
  }

  def wrapJSPromise[A](jsPromise: JPromise[A]): Future[A] = {
    val promise = Promise[A]()
    jsPromise.andThen((resp: A) => promise.success(resp), (err: Any) => promise.failure(ReindexException(err.asInstanceOf[js.Dynamic])))
    promise.future
  }
}
