package ar.cleaner.first.pf.ui.boost

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.ads.appShowAds
import ar.cleaner.first.pf.databinding.FragmentProgressBinding
import ar.cleaner.first.pf.domain.models.RunningApp
import ar.cleaner.first.pf.domain.usecases.boosting.GetInstalledAppsUseCase
import ar.cleaner.first.pf.domain.usecases.boosting.KillBackgroundProcessUseCase
import ar.cleaner.first.pf.ui.progress.BoostAdapter
import ar.cleaner.first.pf.ui.result.ResultFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yin_kio.ads.preloadAd
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class BoostProgressFragment : Fragment(R.layout.fragment_progress) {

    private val binding: FragmentProgressBinding by viewBinding()

    @Inject
    lateinit var killBackgroundProcessUseCase: KillBackgroundProcessUseCase

    @Inject
    lateinit var getInstalledAppsUseCase: GetInstalledAppsUseCase

    private var scanIsDone = false

    override fun onResume() {
        super.onResume()
        if (scanIsDone) scanIsDone()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preloadAd()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val actionList = getActionList()
        val adapter = BoostAdapter(previewActions = getPreviewList(), actions = actionList)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        val repeat = getPreviewList().size + actionList.size
        lifecycleScope.launch(Dispatchers.Default) {
            killBackgroundProcessUseCase.killBackgroundProcessSystemApps()
            repeat(repeat) {
                delay(TimeUnit.SECONDS.toMillis(8) / repeat)
                withContext(Dispatchers.Main) {
                    adapter.removeFirst()
                }
            }
            scanIsDone = true
            if (scanIsDone) scanIsDone()
        }
    }

    private fun getActionList(): MutableList<RunningApp> {
        var actionList = mutableListOf<RunningApp>()
        lifecycleScope.launch {
            actionList = getInstalledAppsUseCase.getRunningApp().toMutableList()
            if (actionList.size > 8) {
                val randomIndices = (0 until actionList.size).shuffled().take(8)
                val randomObjects = randomIndices.map { actionList[it] }
                actionList.clear()
                actionList.addAll(randomObjects)
            }
        }
        return actionList
    }

    private fun getPreviewList(): List<String> {
        return listOf(
            getString(R.string.boost_checking_apps),
            getString(R.string.boost_sending_command)
        )
    }

    private fun scanIsDone() {
        lifecycleScope.launch(Dispatchers.Default) {
            killBackgroundProcessUseCase.killBackgroundProcessInstalledApps()
            withContext(Dispatchers.Main) {
                delay(500)
                withContext(Dispatchers.Main) {
                    binding.recyclerView.visibility = View.GONE
                    binding.isDoneTv.visibility = View.VISIBLE
                }
                delay(500)
                withContext(Dispatchers.Main) { goScreenResult() }
            }
        }
    }

    private fun goScreenResult() {
        appShowAds {
            findNavController().navigate(
                BoostProgressFragmentDirections.actionBoostProgressFragmentToResultFragment(
                    ResultFragment.BOOST_KEY
                )
            )
        }
    }

}