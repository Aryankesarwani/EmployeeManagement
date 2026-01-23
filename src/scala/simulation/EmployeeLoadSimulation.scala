package simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.language.postfixOps

class EmployeeLoadSimulation extends Simulation{
  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  val createEmployee = scenario("Create Employee")
    .exec(
      http("Create Employee Request")
        .post("addEmployee")
        .body(StringBody(
          """{
            "name":"Aryan",
            "email":"Aryan@Example.com",
            "phone":"23421342134",
            "address":"GomtiNagar",
            "designation":"Developer",
            "CTC":7.9
            }"""
        ))
        .check(status.is(201))
        .check(jsonPath("$.id").saveAs("EmployeeId"))
    )
  setUp(
    createEmployee.inject(
      rampUsers(20) during(10 seconds)
    )
  ).protocols(httpProtocol)
}