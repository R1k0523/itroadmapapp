package ru.boringowl.itroadmap.data.persistence.room.competence.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.boringowl.itroadmap.domain.model.competence.SkillTodo
import ru.boringowl.itroadmap.domain.model.competence.Todo
import java.util.UUID

@Entity(tableName = "skilltodos")
class SkillTodoEntity (
    @PrimaryKey
    @ColumnInfo(name="skill_todo_id")
    var skillTodoId: UUID,
    @ColumnInfo(name="skill_name")
    var skillName: String = "",
    @ColumnInfo(name="manual_name")
    var manualName: String = "",
    @ColumnInfo(name="todo")
    var todoId: UUID,
    @ColumnInfo(name="progress")
    var progress: Int = 0,
    @ColumnInfo(name="necessity")
    var necessity: Int = 0,
    @ColumnInfo(name="notes")
    var notes: String = "",
    @ColumnInfo(name="binary_progress")
    var binaryProgress: Boolean = false,
    @ColumnInfo(name="favorite")
    var favorite: Boolean = false,
) {
    @ColumnInfo(name="uploaded")
    var uploaded: Boolean = true
}


fun SkillTodoEntity.toModel(todo: Todo? = null): SkillTodo = SkillTodo(
    skillTodoId = skillTodoId,
    skillName = skillName,
    manualName = manualName,
    todo = todo,
    progress = progress,
    necessity = necessity,
    notes = notes,
    binaryProgress = binaryProgress,
    favorite = favorite,
)
fun SkillTodo.toEntity(todoId: UUID? = null): SkillTodoEntity = SkillTodoEntity(
    skillTodoId = skillTodoId,
    skillName = skillName,
    manualName = manualName,
    todoId = todo?.todoId ?: todoId!!,
    progress = progress,
    necessity = necessity,
    notes = notes,
    binaryProgress = binaryProgress,
    favorite = favorite,
)