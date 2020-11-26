using Grpc.Net.Client;
using GrpcServer.Web.Protos;
using System;
using System.Threading.Tasks;

namespace GrpcClient.CSharp
{
    class Program
    {
        static async Task Main(string[] args)
        {
            Console.WriteLine("Hello Grpc World!");
            using var channel = GrpcChannel.ForAddress("https://localhost:5001");
            var client = new EmployeeService.EmployeeServiceClient(channel);
            var response = await client.GetByNoAsync(new GetByNoRequest
            {
                No = 10
            });
            Console.WriteLine($"Response {response}");
            Console.WriteLine("Press any key exit");
            Console.ReadKey();
        }
    }
}
