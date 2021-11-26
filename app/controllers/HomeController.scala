package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.http.HeaderNames
import scala.concurrent.Future

/** This controller creates an `Action` to handle HTTP requests to the application's home page.
  */
@Singleton
class HomeController @Inject() (
    val controllerComponents: ControllerComponents,
    config: Configuration
) extends BaseController
    with Logging {

  /** Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method will be called when the
    * application receives a `GET` request with a path of `/`.
    */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index("Welcome"))
  }

  def ipWhileList[A](action: Action[A]) = Action.async(action.parser) { request =>
    val whiteList = config.getOptional[Seq[String]]("paypay.webhook.ip.whitelists")
    val clientIP = request.headers
      .get(HeaderNames.X_FORWARDED_FOR)
      .map(_.split("\\s*,\\s*").toList.last)
      .getOrElse(request.remoteAddress)

    whiteList.filter(_.contains(clientIP)) match {
      case Some(_) => action(request)
      case None    => Future.successful(Forbidden("Request not allowed"))
    }
  }

  def showIPAddress =
    Action { implicit request: Request[AnyContent] =>
      val xip =
        request.headers
          .get(HeaderNames.X_FORWARDED_FOR)
          .map(_.split(',').toList.last)
      Ok(views.html.index(xip.getOrElse(request.remoteAddress)))
    }

  def paypayTransactionEventWebhook = ipWhileList(
    Action { implicit request: Request[AnyContent] =>
      val t = request.body.asJson
      t.map(jsValue => logger.info(s"paypayTransactionEventWebhook receive: ${jsValue}"))
      Ok("OK")
    }
  )

  def paypayReconFileCreateEventWebhook = ipWhileList(
    Action { implicit request: Request[AnyContent] =>
      val t = request.body.asJson
      t.map(jsValue => logger.info(s"paypayReconFileCreateEventWebhook receive: ${jsValue}"))
      Ok("OK")
    }
  )
}
