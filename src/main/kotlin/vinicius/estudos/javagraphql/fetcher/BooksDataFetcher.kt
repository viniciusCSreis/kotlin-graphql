package vinicius.estudos.javagraphql.fetcher

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import vinicius.estudos.javagraphql.repository.Database
import kotlin.streams.toList

@Component
class BooksDataFetcher : DataFetcher<List<Map<String, String>>> {
    override fun get(dataFetchingEnvironment: DataFetchingEnvironment): List<Map<String, String>> {
        return Database.books
    }


}