package se.ulvhamne.time.entities

import java.util.*

data class WorkDay(val date: Date = Date(),
                   val tasks: List<WorkTask> = emptyList())

data class WorkTask ( val project: Project = Project(),
                      val name: String = "default",
                      val timeUsed: Int = 0,
                      val rate: WorkRate = WorkRate.NORMAL)

data class Project(val name: String = "default",
                   val code: String = "OMGWTFBBQ",
                   val company: String = "Internal",
                   val taskNames: List<String> = emptyList(),
                   val validFrom: Date = Date(2001, 9, 11),
                   val validUntil: Date = Date(2038, 1, 1))

enum class WorkRate {
    NORMAL, OVERTIME, EXTRA_OMGWTFBBQ_OVERTIME
}
