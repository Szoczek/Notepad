package com.example.notepad.notesList.utils

sealed class NoteOperationResult {
    object NotStarted : NoteOperationResult()
    object Pending : NoteOperationResult()
    data class Completed(val id: String) : NoteOperationResult()
    data class Failed(val error: String) : NoteOperationResult()
}