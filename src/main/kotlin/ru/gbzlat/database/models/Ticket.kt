package ru.gbzlat.database.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.ktorm.dsl.QueryRowSet
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.*
import ru.gbzlat.database.DatabaseManager
import ru.gbzlat.plugins.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class Ticket (
    val id: Int,
    val creatorId: Int,
    val executorId: Int,
    val subject: String,
    val text: String?,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createDate: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val closeDate: LocalDateTime?,
    val priorityId: Int,
    val statusId: Int,
)

object Tickets: BaseTable<Ticket>("Tickets") {
    val id = int("id").primaryKey()
    val creatorId = int("creator_id")
    val executorId = int("executor_id")
    val subject = varchar("subject")
    val text = varchar("text")
    val createDate = datetime("create_date")
    val closeDate = datetime("close_date")
    val priorityId = int("priority_id")
    val statusId = int("status_id")

    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean)= Ticket(
        id = row[id]!!,
        creatorId = row[creatorId]!!,
        executorId = row[executorId]!!,
        subject = row[subject]!!,
        text = row[text].orEmpty(),
        createDate = row[createDate]?: LocalDateTime.now(),
        closeDate = row[closeDate],
        priorityId = row[priorityId]!!,
        statusId = row[statusId]!!,
    )
}

val DatabaseManager.tickets get() = database.sequenceOf(Tickets)