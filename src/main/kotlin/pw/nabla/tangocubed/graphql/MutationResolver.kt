package pw.nabla.tangocubed.graphql

import org.springframework.stereotype.Component
import org.axonframework.commandhandling.gateway.CommandGateway
import com.coxautodev.graphql.tools.GraphQLMutationResolver

import pw.nabla.tangocubed.domain.dictionary.command.CreateDictionaryCommand

@Component
class MutationResolver(
    private val commandGateway: CommandGateway
): GraphQLMutationResolver {

    fun createDictionary(
        title: String
    ): String {
        val command = CreateDictionaryCommand(
            title = title
        )
        return commandGateway.sendAndWait(command)
    }

}
