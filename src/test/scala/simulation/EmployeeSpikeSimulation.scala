package simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.language.postfixOps

class EmployeeSpikeSimulation extends Simulation{
  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  val mixqueryies = scenario("Spike Test - Sudden Load")
    .exec(session => {
      val uniqueId = session.userId + "-" + System.currentTimeMillis()
      session.set("uniqueId", uniqueId)
    })
    .exec(
      http("Create Employee Request")
        .post("/graphql")
        .body(StringBody(
          """{
            "query": "mutation AddEmployee($employeeDTO: EmployeeDTO) { addEmployee(employeeDTO: $employeeDTO) { id name email phone address designation CTC } }",
            "variables": {
              "employeeDTO": {
                "name": "arereryan-#{uniqueId}",
                "email": "aryeweran#{uniqueId}@example.com",
                "phone": "#{uniqueId}",
                "address": "Gomti Nagar",
                "designation": "Developer",
                "CTC": 7.9
              }
            }
          }"""
        ))
        .check(status.is(200))
        .check(bodyString.saveAs("responseBody"))
        .check(jsonPath("$.data.addEmployee.id").optional.saveAs("EmployeeId"))
    )
    .pause(1)
    .exec(
      http("Get All Employee")
        .post("/graphql")
        .body(StringBody(
          """{
            "query":"query GetAllEmployee { getAll{ id name email phone address designation CTC } }"
          }"""
        ))
        .check(status.is(200))
        .check(jsonPath("$.data.getAll").exists)
    )
    .pause(1)
    .exec(
      http("Get Employee By Id")
        .post("/graphql")
        .body(StringBody(
          """{
            "query": "query GetEmployee($id: ID!) { get(id: $id) { id name email phone address designation CTC } }",
            "variables": {
              "id": "1"
            }
          }"""
        ))
        .check(status.is(200))
    )
    .pause(1)
    .exec(
      http("Update Employee By Id")
        .post("/graphql")
        .body(StringBody(
          """{
            "query":"mutation UpdateEmployee($id:ID, $employeeDTO : EmployeeDTO) { updateEmployee(id: $id, employeeDTO : $employeeDTO){ id name email phone address designation CTC } }",
            "variables":{
              "id":"1",
              "employeeDTO":{
                  "name":"Nishant-#{uniqueId}",
                  "email":"nishant#{uniqueId}@example.com",
                  "phone":"99#{uniqueId}",
                   "address":"Bangalore",
                    "designation":"Software Engineer",
                    "CTC":24.0
              }
            }
          }"""
        ))
        .check(status.in(200,201))
    )
    .pause(1)
    .exec(
      http("Delete Employee")
        .post("/graphql")
        .body(StringBody(
          """{
            "query": "mutation DeleteEmployee($id: ID!) { delete(id: $id)  }",
            "variables": {
              "id": "1"
            }
          }"""
        ))
        .check(status.is(200))
    )
  setUp(
    mixqueryies.inject(
      nothingFor(5 seconds),
      atOnceUsers(10),
      nothingFor(5 seconds),
      rampUsers(10)during(5 seconds)
    )
  ).protocols(httpProtocol)
    .assertions(
      global.responseTime.max.lt(10000),
      global.successfulRequests.percent.gt(85)
    )
}
