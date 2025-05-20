package com.theempire.fieldform.data.repository

import com.theempire.fieldform.data.local.Visit
import com.theempire.fieldform.data.local.VisitDao

class VisitRepository(private val dao: VisitDao) {
    val visits = dao.getAll()

    suspend fun save(visit: Visit) = dao.insert(visit)

    suspend fun update(visit: Visit) = dao.update(visit)

    suspend fun getPending(): List<Visit> = dao.getPending()
}
