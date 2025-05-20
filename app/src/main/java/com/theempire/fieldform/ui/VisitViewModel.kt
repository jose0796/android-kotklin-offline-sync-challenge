package com.theempire.fieldform.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.theempire.fieldform.data.local.Visit
import com.theempire.fieldform.data.local.VisitDatabase
import com.theempire.fieldform.data.repository.VisitRepository
import kotlinx.coroutines.launch

class VisitViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: VisitRepository

    val visits by lazy {
        repo.visits
    }

    init {
        val dao = VisitDatabase.getDatabase(application).visitDao()
        repo = VisitRepository(dao)
    }

    fun saveVisit(visit: Visit) = viewModelScope.launch {
        repo.save(visit)
    }

    fun updateVisit(visit: Visit) = viewModelScope.launch {
        repo.update(visit)
    }
}
