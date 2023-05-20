package ru.boringowl.itroadmap.domain.model.competence


import java.util.Locale
import java.util.UUID

data class SkillTodo(
    var skillTodoId: UUID,
    var skillName: String = "",
    var manualName: String = "",
    var todo: Todo? = null,
    var progress: Int = 0,
    var necessity: Int = 0,
    var notes: String = "",
    var binaryProgress: Boolean = false,
    var favorite: Boolean = false,
) {
    fun name() = (if (manualName == skillName) skillName else "$manualName ($skillName)")
        .replaceFirstChar { it.titlecase(Locale.getDefault()) }

}
