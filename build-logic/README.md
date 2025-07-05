# Convention Plugins

The `build-logic` folder defines project-specific convention plugins, used to keep a single source
of truth for common module configurations.

This approach is heavily based on these articles and/or repositories:

- [Now in Android](https://github.com/android/nowinandroid/tree/main/build-logic)
- [Unlocking Reusability in Gradle: How to Use Kotlin-written Convention
  Plugins](https://medium.com/@amsavarthan/11b95cb008ef)
- [Mastering Dependency Management: Version Catalog & Convention Plugin at
  Scale](https://proandroiddev.com/b94205595f6b)
- [Using Version Catalogs from Gradle Precompiled Scripts with Kotlin
  DSL](https://medium.com/@saulmm2/df3c27ea017c)

By setting up convention plugins in `build-logic`, we can avoid duplicated build script setup,
messy `subproject` configurations, without the pitfalls of the `buildSrc` directory.

`build-logic` is an included build, as configured in the root
[`settings.gradle.kts`](../settings.gradle.kts).

Inside `build-logic` is a `convention` module, which defines a set of plugins that all other
modules can use to configure themselves.

`build-logic` also includes a set of `Kotlin` files used to share logic between plugins themselves,
which is most useful for configuring Android components (libraries vs applications) with shared
code.

These plugins are *additive* and *composable*, and try to only accomplish a single responsibility.
Modules can then pick and choose the configurations they need.

If there is one-off logic for a module without shared code, it's preferable to define that directly
in the module's `build.gradle.kts`, as opposed to creating a convention plugin with module-specific
setup.
