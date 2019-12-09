package app.krunal3kapadiya.searchresults.ui.filterDialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import app.krunal3kapadiya.searchresults.R
import kotlinx.android.synthetic.main.dialog_filter.*


class FilterDialogFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val spinnerList = ArrayList<String>()
        for (i in 0..10) {
            spinnerList.add("Keep Company")
            spinnerList.add("Mens / Size 8.5")
        }

        val adapter: ArrayAdapter<String>? = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item, spinnerList
            )
        }
        appCompatSpinner.adapter = adapter
        appCompatSpinner2.adapter = adapter
    }
}