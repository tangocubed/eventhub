package pw.nabla.tangocubed.domain.dictionary.command

import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.Future

@RestController
@RequestMapping("/dictionary")
class RestEndpoint(
    val commandGateway: CommandGateway
) {
    @PostMapping("/")
    fun createDictionary(
        @RequestBody command: CreateDictionaryCommand
    ) : Future<Any> {
        return commandGateway.send(command)
    }
}