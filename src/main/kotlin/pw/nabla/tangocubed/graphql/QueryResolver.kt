package pw.nabla.tangocubed.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.eventhandling.EventHandler
import org.axonframework.modelling.command.Repository
import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Component
import pw.nabla.tangocubed.domain.dictionary.Dictionary
import pw.nabla.tangocubed.domain.dictionary.event.DictionaryEvent

import pw.nabla.tangocubed.domain.dictionary.query.FindDictionariesQuery

@Component
class QueryResolver(
    private val queryGateway: QueryGateway,
    private val dictionaryRepository: Repository<Dictionary>
): GraphQLQueryResolver {

    fun dictionaries(): List<Dictionary> {
        val query = FindDictionariesQuery()
        val responseType = ResponseTypes.multipleInstancesOf(Dictionary::class.java)
        return queryGateway.query(query, responseType).join()
    }
}