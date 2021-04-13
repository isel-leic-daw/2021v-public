# Hypermedia

## [RFC 8288 - Web Linking](ttps://tools.ietf.org/html/rfc8288)

> This specification defines a model for the relationships between
   resources on the Web ("links") and the type of those relationships
   ("link relation types").

> In this specification, a link is a typed connection between two
   resources and is comprised of:
>
>   -  a link context,
>   -  a link relation type (Section 2.1),
>   -  a link target, and
>   -  optionally, target attributes (Section 2.2).

>  A link can be viewed as a statement of the form "link context has a
   link relation type resource at link target, which has target
   attributes".

>  In the simplest case, a link relation type identifies the semantics
   of a link.  For example, a link with the relation type "copyright"
   indicates that the current link context has a copyright resource at
   the link target.

>   Link relation types can also be used to indicate that the target
   resource has particular attributes, or exhibits particular
   behaviours; for example, a "service" link implies that the link
   target can be used as part of a defined protocol (in this case, a
   service description).

>   Relation types are not to be confused with media types [RFC2046];
   they do not identify the format of the representation that results
   when the link is dereferenced.  Rather, they only describe how the
   current context is related to another resource. 

There is a IANA registry for link relations located at [https://www.iana.org/assignments/link-relations/link-relations.xhtml](https://www.iana.org/assignments/link-relations/link-relations.xhtml).

## Hypermedia-enabled representation formats

Format with support to include _hypermedia controls_ in resource representations.
Hypermedia controls provide information on how to perform interactions (i.e. HTTP requests), typically related to the context resource.

### [HAL - Hypertext Application Language](https://stateless.group/hal_specification.html)

There is an expired IETF draft at [https://www.ietf.org/archive/id/draft-kelly-json-hal-08.txt](https://www.ietf.org/archive/id/draft-kelly-json-hal-08.txt).

Defines a format for JSON based representations with a well defined way to including links to other resources (`_links` field), as well as a way to include the partial representation of related resources (`_embedded` field).

Notice the use of the `self` relation on the embedded representations.

### [Siren: a hypermedia specification for representing entities](https://github.com/kevinswiber/siren)

Defines a way to represent:
- Links.
- Partial representations of related _entities_.
- Actions, as an extension to links, with support for non-safe requests.
- The concept of `class`.

> It's important to note the distinction between link relations and classes. Link relations define a relationship between two resources. Classes define a classification of the nature of the element, be it an entity or an action, in its current representation.
> Another distinction is the difference between sub-entities and links. Sub-entities exist to communicate a relationship between entities, in context. Links are primarily navigational and communicate ways clients can navigate outside the entity graph.

### [Collection+JSON](https://github.com/collection-json/spec)

> Collection+JSON is a JSON-based read/write hypermedia-type designed to support management and querying of simple collections.

### [Home Documents for HTTP APIs](https://mnot.github.io/I-D/json-home/)

> It is becoming increasingly common to use HTTP [RFC7230] for applications other than traditional Web browsing. Such “HTTP APIs” are used to integrate processes on disparate systems, make information available to machines across the Internet, and as part of the implementation of “microservices.”

> By using HTTP, these applications realise a number of benefits, from message framing to caching, and well-defined semantics that are broadly understood and useful.

> Often, these applications of HTTP are defined by documenting static URLs that clients need to know and servers need to implement. Any interaction outside of these bounds is uncharted territory.

> For some applications, this approach brings issues, especially when the interface changes, either due to evolution, extension or drift between implementations. Furthermore, implementing more than one instance of interface can bring further issues, as different environments have different requirements.

> The Web itself offers one way to address these issues, using links [RFC3986] to navigate between states. A link-driven application discovers relevant resources at run time, using a shared vocabulary of link relations [RFC8288] and internet media types [RFC6838] to support a “follow your nose” style of interaction – just as a Web browser does to navigate the Web.

> This document defines a “home document” format using the JSON format [RFC7159] for APIs to use as a launching point for the interactions they offer, using links. Having a well-defined format for this purpose promotes good practice and development of tooling.

Example of a resource with the same purpose - [https://api.github.com/](https://api.github.com/)

## [RFC 6570 - URI template](https://tools.ietf.org/html/rfc6570)

> A URI Template is a compact sequence of characters for describing a
   range of Uniform Resource Identifiers through variable expansion.
   This specification defines the URI Template syntax and the process
   for expanding a URI Template into a URI reference, along with
   guidelines for the use of URI Templates on the Internet.

Examples:
- `http://example.com/~{username}/`
- `http://example.com/search{?q,lang}`


## Additional resources

- [Chapter 5 - The application](https://www.oreilly.com/library/view/designing-evolvable-web/9781449337919/ch05.html)
