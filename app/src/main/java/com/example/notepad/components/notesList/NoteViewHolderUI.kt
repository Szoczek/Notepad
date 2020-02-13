package com.example.notepad.components.notesList

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.*
import com.example.notepad.R
import org.jetbrains.anko.*

class NoteViewHolderUI(context: Context) : LinearLayout(context) {

    lateinit var mTvTitle: TextView
    lateinit var mTvDate: TextView
    lateinit var mTvContent: TextView
    lateinit var mBtArchive: Button
    lateinit var mIbFav: ImageButton

    init {
        verticalLayout {
            this.orientation = VERTICAL
            lparams(matchParent, matchParent)

            relativeLayout {
                mTvTitle = textView {
                    id = View.generateViewId()
                    textSize = 18f
                }.lparams {
                    alignParentStart()
                    alignParentTop()
                }

                mIbFav = imageButton {
                    id = View.generateViewId()
                    backgroundColor = Color.TRANSPARENT
                }.lparams {
                    endOf(mTvTitle)
                    alignParentTop()
                }

                mTvDate = textView {
                    id = View.generateViewId()
                    textSize = 18f
                }.lparams {
                    alignParentTop()
                    centerHorizontally()
                }

                mBtArchive = button {
                    id = View.generateViewId()
                    text = context.resources.getText(R.string.btArchive)
                }.lparams {
                    alignParentEnd()
                    alignParentTop()
                }
            }.lparams(matchParent, wrapContent)

            mTvContent = textView {
                id = View.generateViewId()
                textSize = 14f
            }.lparams(matchParent, wrapContent)
        }
    }
}