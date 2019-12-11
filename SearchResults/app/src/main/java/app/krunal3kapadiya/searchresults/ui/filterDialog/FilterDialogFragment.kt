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
        dialog?.window?.attributes?.windowAnimations = R.style.FilterDialogAnimation

        val size_one_list = ArrayList<String>()
        val size_two_list = ArrayList<String>()
        size_one_list.add("Keep Company")
        size_two_list.add("Mens / Size 8.5")

        val size_one_adapter: ArrayAdapter<String>? = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item, size_one_list
            )
        }
        val size_two_adapter: ArrayAdapter<String>? = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item, size_two_list
            )
        }
        size_one_spinner.adapter = size_one_adapter
        size_two_spinner.adapter = size_two_adapter
    }
}