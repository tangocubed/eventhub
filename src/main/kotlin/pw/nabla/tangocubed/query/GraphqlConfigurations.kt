package pw.nabla.tangocubed.query

import com.coxautodev.graphql.tools.SchemaParser
import graphql.schema.GraphQLSchema
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GraphqlConfigurations(
    private val queryResolver: QueryResolver
) {
    @Bean
    fun schema(): GraphQLSchema = _schema

    private val _schema: GraphQLSchema by lazy {
        SchemaParser
        .newParser()
        .files(
            "pw/nabla/tangocubed/query/Query.graphql",
            "pw/nabla/tangocubed/domain/dictionary/Dictionary.graphql",
            "pw/nabla/tangocubed/domain/dictionary/Word.graphql"
        )
        .resolvers(
            queryResolver
        )
        .build()
        .makeExecutableSchema()
    }
}
