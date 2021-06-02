package isel.leic.daw.hvac.common

const val debugBuildVersion = "debug.version"

/**
 * Contains information about the project's build. This object must be contained in the project's base package.
 */
object BuildInfo {

    /**
     * The current build version.
     */
    val buildVersion: String = this.javaClass.`package`.implementationVersion ?: debugBuildVersion

    /**
     * A function that returns a boolean value indicating whether the current build is a debug build or not
     */
    fun isDebugBuild() = buildVersion == debugBuildVersion
}