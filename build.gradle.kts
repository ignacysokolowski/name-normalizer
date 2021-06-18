import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jmailen.gradle.kotlinter.tasks.LintTask

plugins {
    kotlin("jvm") version "1.4.32"
    id("org.jmailen.kotlinter") version "3.3.0"
}

repositories {
    mavenCentral()
}

val junitVersion: String = "5.7.0"

configure(subprojects) {
    group = "ignacysokolowski"

    apply(plugin = "kotlin")
    apply(plugin = "org.jmailen.kotlinter")

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
        testImplementation("com.natpryce:hamkrest:1.7.0.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "11"
        }

        withType<Test> {
            useJUnitPlatform()
            testLogging {
                showExceptions = true
                exceptionFormat = TestExceptionFormat.FULL
                events("passed", "skipped", "failed")

                addTestListener(object : TestListener {
                    override fun beforeSuite(suite: TestDescriptor) {}
                    override fun beforeTest(testDescriptor: TestDescriptor) {}
                    override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}
                    override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                        if (suite.parent == null) {
                            println(
                                "\n${result.resultType}: ${result.testCount} tests, " +
                                    "${result.successfulTestCount} passed, " +
                                    "${result.failedTestCount} failed, " +
                                    "${result.skippedTestCount} skipped " +
                                    "in ${(result.endTime - result.startTime).toFloat() / 1000}s"
                            )
                        }
                    }
                })
            }
        }

        withType<LintTask> {
            shouldRunAfter("test")
        }
    }
}
