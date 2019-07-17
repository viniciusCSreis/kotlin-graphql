package vinicius.estudos.javagraphql.fetcher

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import vinicius.estudos.javagraphql.repository.Database

@Component
class AuthorDataFetcher : DataFetcher<Map<String, String>> {
    override fun get(dataFetchingEnvironment: DataFetchingEnvironment): Map<String, String> {
        val book: Map<String, String> = dataFetchingEnvironment.getSource()
        val authorId: String = book.getValue("authorId")
        return Database.authors
            .stream()
            .filter { author -> author["id"].equals(authorId) }
            .findFirst()
            .orElse(null)
    }


}