# DAW Project #

## Goal ##

The DAW project is comprised by the analysis, design and implementation of an information system to manage _project issues_ and associated information.
Its development is divided into two phases, with incremental requirements published before the beginning of each phase.
* Phase 1 will focus on providing an HTTP-based Application Programmers Interface (API).
* Phase 2 will be the creation of a browser-based client application.

## Domain and functionality ##

* A _project_ is a long-running development activity, such as "DAW Project" or "LS Project".
It is characterized by:
  * An unique name.
  * A short description. 

* An _issue_ is a task that needs to be done in the context of a project, such as adding a new functionality, resolve an error, add a test, create a final release.
An _issue_ always exists in the context of a project.
It is characterized by:
  * An unique identifier.
  * A name.
  * A short description.
  * A creation date and an optional close date.
  * A set of labels, such as `defect`, `new-functionality`, or `exploration`. 
 
* An _issue_ has a _state_. The set of available states and the set of possible transitions between states is configurable per project.
  * The set of available states must include the `closed` and `archived` states. It should always be possible to transition from the `closed` to the `archived` state.
  * The set of available states must also define a single start state.

* An issue has a sequence of _issue comments_, ordered in chronological order. 
A comment is characterized by a short text.
A comment cannot be added to an archived issue.

* Issues can have zero or more _labels_. The set of allowed labels is defined per project.

## Phase 1 requirements ##

* Issue management functionality should be exposed via an HTTP API, including possible states and transitions.

* Storage should use a relational database management system, preferably PostgreSQL.

* Authentication should use the [HTTP Basic scheme](https://tools.ietf.org/html/rfc7617). Better authentication mechanisms will be introduced in future releases.

* Error information should use the format described in the [Problem Details for HTTP APIs](https://tools.ietf.org/html/rfc7807) specification, whenever possible.

* The phase 1 delivery must also include all the API documentation required to use it. Namely, it should be possible to create an API client just by using this documentation or the external documents referenced by it (e.g. media type formats). However, the documentation should not hinder the API evolution.

## Evaluation and Delivery

* The evaluation focus will be on the HTTP API documentation and implementation.
 
* Phase 1 must be delivered until April 24, via the creation of a `0.1.0` tag on the group's GitHub repository.

* Phase 1 deliveries will be presented between April 26 and April 30.
 