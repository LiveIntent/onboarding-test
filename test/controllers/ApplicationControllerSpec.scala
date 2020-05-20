package controllers

import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.test.Helpers._
import play.api.test._

@RunWith(classOf[JUnitRunner])
class ApplicationControllerSpec extends Specification {

  "ApplicationController" should {

    "send 404 on a bad request" in new WithApplication() {
      route(app, FakeRequest(GET, "/boum")) must beSome.which (status(_) == NOT_FOUND)
    }

    "send 200 on a health check request" in new WithApplication() {
      route(app, FakeRequest(GET, "/health_check")) must beSome.which (status(_) == OK)
    }

    "produce swagger docs" in new WithApplication() {
      route(app, FakeRequest(GET, "/api/v1/swagger.json")) must beSome.which (status(_) == OK)
    }
  }
}
