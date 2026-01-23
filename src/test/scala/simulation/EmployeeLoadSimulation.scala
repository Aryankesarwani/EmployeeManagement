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
        .post("/graphql")
        .body(StringBody(
          """{
            "query": "mutation AddEmployee($employeeDTO: EmployeeDTO) { addEmployee(employeeDTO: $employeeDTO) { id name email phone address designation CTC } }",
            "variables": {
              "employeeDTO": {
                "name": "Aryan",
                "email": "Aryan@Example.com",
                "phone": "23421342134",
                "address": "Gomti Nagar",
                "designation": "Developer",
                "CTC": 7.9
              }
            }
          }"""
        ))
        .check(status.is(200))
        .check(jsonPath("$.data.addEmployee.id").optional.saveAs("EmployeeId"))
    )
  val getAllEmployee = scenario("Get All Employee")
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

  val getEmployee = scenario("Get Employee by Id")
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

  val updateEmployee = scenario("Update Employee by Id")
    .exec(
      http("Update Employee By Id")
        .post("/graphql")
        .body(StringBody(
          """{
            "query":"mutation UpdateEmployee($id:ID, $employeeDTO : EmployeeDTO) { updateEmployee(id: $id, employeeDTO : $employeeDTO){ id name email phone address designation CTC } }",
            "variables":{
              "id":"1",
              "employeeDTO":{
                  "name":"Nishant",
                  "email":"nishant@example.com",
                  "phone":"213453213",
                   "address":"Bangalore",
                    "designation":"Software Engineer",
                    "CTC":24.0
              }
            }
          }"""
        ))
        .check(status.in(200,201))
    )
  val deleteEmployee = scenario("Delete Employee")
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
    createEmployee.inject(
      rampUsers(20) during(10 seconds)
    ),
    getAllEmployee.inject(
      rampUsers(20) during(10 seconds)
    ),
    getEmployee.inject(
      rampUsers(20) during(10 seconds)
    ),
    updateEmployee.inject(
      rampUsers(20) during(10 seconds)
    ),
    deleteEmployee.inject(
      rampUsers(20) during(10 seconds)
    )
  ).protocols(httpProtocol)
}