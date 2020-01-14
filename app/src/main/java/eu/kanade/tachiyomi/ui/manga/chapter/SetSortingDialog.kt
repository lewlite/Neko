package eu.kanade.tachiyomi.ui.manga.chapter

import android.app.Dialog
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.bluelinelabs.conductor.Controller
import eu.kanade.tachiyomi.R
import eu.kanade.tachiyomi.data.database.models.Manga
import eu.kanade.tachiyomi.ui.base.controller.DialogController

class SetSortingDialog<T>(bundle: Bundle? = null) : DialogController(bundle)
        where T : Controller, T : SetSortingDialog.Listener {

    private val selectedIndex = args.getInt("selected", -1)

    constructor(target: T, selectedIndex: Int = -1) : this(Bundle().apply {
        putInt("selected", selectedIndex)
    }) {
        targetController = target
    }

    override fun onCreateDialog(savedViewState: Bundle?): Dialog {
        val activity = activity!!
        val ids = intArrayOf(Manga.SORTING_SOURCE, Manga.SORTING_NUMBER)
        val choices = intArrayOf(R.string.sort_by_source, R.string.sort_by_number)
                .map { activity.getString(it) }

        return MaterialDialog(activity)
                .title(R.string.sorting_mode)
                .listItemsSingleChoice(items = choices, initialSelection = selectedIndex) { _, position, _ ->
                    (targetController as? Listener)?.setSorting(ids[position])
                }
                .positiveButton(android.R.string.ok)
    }

    interface Listener {
        fun setSorting(id: Int)
    }

}