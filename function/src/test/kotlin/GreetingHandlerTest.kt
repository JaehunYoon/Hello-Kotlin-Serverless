import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GreetingHandlerTest {
    private lateinit var app: GreetingHandler

    @BeforeTest
    fun setup() {
        app = GreetingHandler()
    }

    @Test
    fun testSuccessfulResponse() {
        val result = app.handleRequest(null, null)
        assertEquals(result.statusCode, 200)
        assertEquals(result.headers["Content-Type"], "application/json")
        result.body?.let { content ->
            //language=JSON
            assertEquals(
                    expected = """
                        {
                            "message": "Hello!"
                        }
                    """.trimIndent(),
                    actual = content
            )
        }
    }

    @Test
    fun testSuccessfulResponseWithParams() {
        val result = APIGatewayProxyRequestEvent()
                .apply { queryStringParameters = mapOf("content" to "Thank You") }
                .let { input -> app.handleRequest(input, null) }
        assertEquals(result.statusCode, 200)
        assertEquals(result.headers["Content-Type"], "application/json")
        result.body?.let { content ->
            //language=JSON
            assertEquals(
                    expected = """
                        {
                            "message": "Thank You!"
                        }
                    """.trimIndent(),
                    actual = content
            )
        }
    }
}