package vinicius.estudos.javagraphql.provider

import com.google.common.io.Resources
import graphql.schema.GraphQLSchema
import graphql.GraphQL
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.io.IOException
import javax.annotation.PostConstruct
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import graphql.schema.idl.TypeRuntimeWiring.newTypeWiring
import org.springframework.beans.factory.annotation.Autowired
import vinicius.estudos.javagraphql.fetcher.AuthorDataFetcher
import vinicius.estudos.javagraphql.fetcher.BookByIdDataFetcher
import vinicius.estudos.javagraphql.fetcher.BooksDataFetcher


@Component
class GraphQLProvider(
    private val bookByIdDataFetcher: BookByIdDataFetcher,
    private val authorDataFetcher: AuthorDataFetcher,
    private val booksDataFetcher: BooksDataFetcher
) {

    private lateinit var graphQL: GraphQL

    @Bean
    fun graphQL(): GraphQL {
        return graphQL
    }


    init {
        val url = Resources.getResource("schema.graphqls")
        val sdl = Resources.toString(url, Charsets.UTF_8)
        val graphQLSchema = buildSchema(sdl)
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build()
    }


    private fun buildSchema(sdl: String): GraphQLSchema {
        val typeRegistry = SchemaParser().parse(sdl)
        val runtimeWiring = buildWiring()
        val schemaGenerator = SchemaGenerator()
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring)
    }

    private fun buildWiring(): RuntimeWiring {
        return RuntimeWiring.newRuntimeWiring()
            .type(
                newTypeWiring("Query")
                    .dataFetcher("bookById", bookByIdDataFetcher)
                    .dataFetcher("books", booksDataFetcher)
            )
            .type(
                newTypeWiring("Book")
                    .dataFetcher("author", authorDataFetcher)
            )
            .build()
    }
}