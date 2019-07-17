package vinicius.estudos.javagraphql.fetcher

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import vinicius.estudos.javagraphql.repository.Database

@Component
class BookByIdDataFetcher : DataFetcher<Map<String, String>> {
    override fun get(dataFetchingEnvironment: DataFetchingEnvironment): Map<String, String> {
        val bookId: String = dataFetchingEnvironment.getArgument("id")
        return Database.books
            .stream()
            .filter { book -> book["id"].equals(bookId) }
            .findFirst()
            .orElse(null)
    }


}