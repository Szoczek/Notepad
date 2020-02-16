package com.example.notepad.note.ui

import com.example.notepad.R
import com.example.notepad.base.HaveTag
import com.example.notepad.db.models.Note
import com.example.notepad.main.utils.ReplaceFragmentArguments
import com.example.notepad.note.mvi.NoteViewState
import com.example.notepad.note.utils.NoteOperationResult
import com.example.notepad.utils.NOTE_FRAGMENT_TAG
import io.reactivex.Completable
import io.reactivex.Observable
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.toast
import java.util.*

class NoteFragment : NoteFragmentBase(), HaveTag {
    override fun onStart() {
        val id = this.arguments?.getInt("ID", -1) ?: -1
        if (id == -1) {
            note = Note(0, Date().time, "", "", isArchival = false, isFavourite = false)
            this.loadingIntent = Completable.complete().toObservable()
        } else
            this.loadingIntent = Observable.just(id)

        super.onStart()
    }

    override fun render(state: NoteViewState) {
        when (state.noteOperationResult) {
            is NoteOperationResult.Failed -> noteOperationStateFailed(state)
            is NoteOperationResult.Completed -> noteOperationStateCompleted(state)
        }
    }

    private fun noteOperationStateCompleted(state: NoteViewState) {
        this.ui.etTitle.error = null
        this.ui.saveMenuItem.isEnabled = true

        if (state.updateTextEdits) {
            note = (state.noteOperationResult as NoteOperationResult.Completed).result!!
            this.ui.etTitle.setText(note.title)
            this.ui.etContent.setText(note.content)
        }

        if (state.changeFavouritesIcon)
            this.ui.favouriteMenuItem.icon =
                if (note.isFavourite) context!!.getDrawable(R.drawable.ic_favorite_white_24dp)
                else context!!.getDrawable(R.drawable.ic_favorite_border_white_24dp)

        if (state.finishActivity) {
            toast(context!!.getString(R.string.notesSavedToast))
            mainActivity.redirectSubject.onNext(
                ReplaceFragmentArguments(
                    -1,
                    redirectToNoteFragment = false,
                    redirectToNotesListFragment = true
                )
            )
        }
    }

    private fun noteOperationStateFailed(state: NoteViewState) {
        val error = (state.noteOperationResult as NoteOperationResult.Failed).error
        if (state.showValidationError)
            this.ui.etTitle.error = error
        else
            this.ui.mainLayout.snackbar(error ?: "")

        this.ui.saveMenuItem.isEnabled = false
    }

    override fun getFragmentTag(): String {
        return NOTE_FRAGMENT_TAG
    }
}