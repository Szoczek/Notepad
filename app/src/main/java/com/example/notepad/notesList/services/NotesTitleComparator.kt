package com.example.notepad.notesList.services

import com.example.notepad.db.Repository
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.utils.NotesListSearchResult
import com.example.notepad.utils.filterByTitle
import io.reactivex.Observable

class NotesTitleComparator {
    fun compareTitles(query: String, noteRepository: Repository<Note>): Observable<NotesListSearchResult> {
        val notes = noteRepository.getItemsList(Repository.allNotes)

        if (query.isEmpty())
            return Observable.just(NotesListSearchResult.Canceled(ArrayList(notes)))
        if (query.length < 3)
            return Observable.just(NotesListSearchResult.Error("Query must be longer than 2 characters"))

        return Observable.just(NotesListSearchResult.Completed(ArrayList(notes.filterByTitle(query))))
    }
}