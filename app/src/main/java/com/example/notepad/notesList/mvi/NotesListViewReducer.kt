package com.example.notepad.notesList.mvi

import android.util.Log
import com.example.notepad.base.ReducerBase
import com.example.notepad.notesList.utils.NotesListArchiveResult
import com.example.notepad.notesList.utils.NotesListSearchResult

class NotesListViewReducer : ReducerBase<NotesListViewState, NotesListViewStateChange> {

    override fun reduce(
        state: NotesListViewState,
        change: NotesListViewStateChange
    ): NotesListViewState {
        val currentState = state.copy()
        Log.i("change", change.toString())

        when (change) {
            is NotesListViewStateChange.NotesListChanged -> {
                when (change.searchResult) {
                    is NotesListSearchResult.Canceled -> {
                        currentState.isSearchCanceled = true
                        currentState.isSearchCompleted = false
                        currentState.isSearchFailed = false
                        currentState.notesList = change.searchResult.notesList
                        currentState.error = ""
                    }

                    is NotesListSearchResult.Completed -> {
                        currentState.isSearchCanceled = false
                        currentState.isSearchCompleted = true
                        currentState.isSearchFailed = false
                        currentState.notesList = change.searchResult.notesList
                        currentState.error = ""
                    }

                    is NotesListSearchResult.Error -> {
                        currentState.isSearchCanceled = false
                        currentState.isSearchCompleted = false
                        currentState.isSearchFailed = true
                        currentState.notesList = ArrayList()
                        currentState.error = change.searchResult.error
                    }
                }
            }

            is NotesListViewStateChange.NotesListItemChanged -> {
                when (change.archiveResult) {
                    is NotesListArchiveResult.Pending -> {
                        currentState.archivedNote = null
                        currentState.isArchiveFailed = false
                        currentState.isArchiveCompleted = false
                        currentState.error = ""
                    }
                    is NotesListArchiveResult.Completed -> {
                        currentState.archivedNote = change.archiveResult.archivedNote
                        currentState.isArchiveFailed = false
                        currentState.isArchiveCompleted = true
                        currentState.error = ""
                    }
                    is NotesListArchiveResult.Error -> {
                        currentState.archivedNote = null
                        currentState.isArchiveFailed = true
                        currentState.isArchiveCompleted = false
                        currentState.error = change.archiveResult.error
                    }
                }
            }
        }

        return currentState
    }
}