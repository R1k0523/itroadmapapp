package ru.boringowl.itroadmap.data.persistence.room.competence.entity


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import ru.boringowl.itroadmap.domain.model.competence.Todo
import java.util.UUID

@Entity(tableName = "todos")
class TodoEntity (
    @PrimaryKey
    @ColumnInfo(name="todo_id")
    var todoId: UUID,
    @ColumnInfo(name="header")
    var header: String = "",
    @ColumnInfo(name="ready")
    var ready: Int = 0,
    @ColumnInfo(name="full")
    var full: Int = 0
)  {
    @ColumnInfo(name="uploaded")
    var uploaded: Boolean = true
}

fun TodoEntity.toModel(todoSkills: List<SkillTodoEntity> = listOf()): Todo = Todo(
    todoId = todoId,
    header = header,
    ready = ready,
    full = full,
    skills = todoSkills.map { ts -> ts.toModel() },
)
fun Todo.toEntity(): TodoEntity = TodoEntity(
    todoId = todoId,
    header = header,
    ready = ready,
    full = full,
)

data class TodoWithSkills(
    @Embedded val todo: TodoEntity,
    @Relation(
        parentColumn = "todo_id",
        entityColumn = "todo"
    )
    val todoSkills: List<SkillTodoEntity>
) {
    fun toTodoModel() = todo.toModel(todoSkills)
}