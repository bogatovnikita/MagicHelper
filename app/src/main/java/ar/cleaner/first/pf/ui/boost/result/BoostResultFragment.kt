package ar.cleaner.first.pf.ui.boost.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentBoostResultBinding
import ar.cleaner.first.pf.ui.result.ResultAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoostResultFragment : Fragment(R.layout.fragment_boost_result) {

    private val binding: FragmentBoostResultBinding by viewBinding()

    private lateinit var adapter: ResultAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
        initRecyclerView()
    }

    private fun initClickListener() {
        binding.arrowBackIv.setOnClickListener {
            findNavController().popBackStack(R.id.menuFragment, false)
        }
    }

    private fun initRecyclerView() {

    }


}