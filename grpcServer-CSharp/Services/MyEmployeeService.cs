using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Grpc.Core;
using GrpcServer.Web.Protos;
using Microsoft.Extensions.Logging;

namespace GrpcServer.Web
{
    public class MyEmployeeService:EmployeeService.EmployeeServiceBase
    {
        private readonly ILogger<MyEmployeeService> logger;

        public MyEmployeeService(ILogger<MyEmployeeService> logger) {
            this.logger = logger;
        }

        public override Task<EmployeeResponse> GetByNo(GetByNoRequest request, ServerCallContext context)
        {
            var employee = new Employee
            {
                Id = 101,
                No = request.No,
                FirstName = $"FirstName ${request.No}",
                LastName = $"LastName ${request.No}",

            };
            var response = new EmployeeResponse {
                Employee = employee
            };

            return Task.FromResult(response);
      
        }
    }
}
