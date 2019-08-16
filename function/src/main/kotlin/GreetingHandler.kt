import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent

class GreetingHandler: RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    override fun handleRequest(
            input: APIGatewayProxyRequestEvent?,
            context: Context?
    ): APIGatewayProxyResponseEvent = APIGatewayProxyResponseEvent()
            .withStatusCode(200)
            .withHeaders(headers())
            .withBody(body(input ?. queryStringParameters ?: emptyMap()))

    private fun headers() = mapOf(
            "Content-Type" to "application/json"
    )

    private fun body(params: Map<String, String>): String = params
            .getOrDefault("content", "Hello")
            .let { content ->
                //language=JSON
                """
                    {
                        "message": "${content.capitalize()}!"
                    }
                """.trimIndent()
            }
}