package com.oliverspryn.gradle

object CentralRepositoryConfig {
    const val LIBRARY_NAME = "QuickE2E"

    object Artifact {
        const val GROUP_ID = "com.oliverspryn.android"
        const val ID = "quick-e2e"
        const val VERSION = "1.0.0"
    }

    object Developer {
        const val ID = "oliverspryn"
        const val NAME = "Oliver Spryn"
        const val URL = "https://oliverspryn.com/"
    }

    object Project {
        const val DESCRIPTION = "Eases the pain of preparing for Android 15 Edge-to-Edge support"
        const val NAME = "Quick Edge-to-Edge"
        const val URL = "https://oliverspryn.com/"
    }

    object License {
        const val NAME = "MIT License"
        const val URL = "https://mit-license.org/"
    }

    object SCM {
        const val URL = "github.com/oliverspryn/quick-e2e" // Omit the `protocol://` and trailing slash
    }
}
