package isel.leic.daw.hvac.common

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Class used for error models, based on the [Problem Json spec](https://tools.ietf.org/html/rfc7807)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProblemJson(
        val type: String,
        val title: String,
        val detail: String,
        val status: Int
)