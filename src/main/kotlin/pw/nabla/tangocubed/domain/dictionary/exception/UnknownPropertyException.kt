package pw.nabla.tangocubed.domain.dictionary.exception

class UnknownPropertyException(
    unknownProperties: List<String>,
    allowedProperties: List<String>,
    context: Any?
): Exception(
    "The following properties can not be allowed: ${unknownProperties.joinToString(", ")}." +
    " They should be one of ${allowedProperties.joinToString(", ")}." +
    (if (context == null) "" else " -- $context")
)