package ar.cleaner.first.pf.ui.junk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.ads.preloadAd
import ar.cleaner.first.pf.ads.showAds
import ar.cleaner.first.pf.databinding.FragmentProgressBinding
import ar.cleaner.first.pf.domain.models.JunkFile
import ar.cleaner.first.pf.domain.usecases.junk.ExtendedCleanUseCase
import ar.cleaner.first.pf.ui.menu.MenuViewModel
import ar.cleaner.first.pf.ui.progress.ActionsAdapter
import ar.cleaner.first.pf.ui.result.ResultFragment
import ar.cleaner.first.pf.ui.result.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class JunkProgressFragment : Fragment(R.layout.fragment_progress) {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    private var scanIsDone = false

    @Inject
    lateinit var extendedCleanUseCase: ExtendedCleanUseCase

    private val menuViewModel: MenuViewModel by activityViewModels()

    private val resultViewModel: ResultViewModel by activityViewModels()

    private val args by navArgs<JunkProgressFragmentArgs>()

    override fun onResume() {
        super.onResume()
        if (scanIsDone) scanIsDone()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preloadAd()
        initRecyclerView()
        updateUseCase()
    }

    private fun initRecyclerView() {
        val actions = scanList().shuffled().subList(0, 15)
        val adapter = ActionsAdapter(actions)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        val repeat = actions.size
        lifecycleScope.launch(Dispatchers.Default) {
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

    private fun updateUseCase() {
        val junk = args.listJunk.filter { it.typeJunk == JunkViewModel.JUNK_FILE }
            .map { JunkFile(it.pathForDelete, "", 0.0, "", "", false) }
        lifecycleScope.launch(Dispatchers.Main) {
            extendedCleanUseCase.invoke(junk).collect {}
        }
        resultViewModel.initCleanerDetails()
    }

    private fun scanIsDone() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                menuViewModel.initAllUseCase()
                delay(500)
                withContext(Dispatchers.Main) {
                    binding.recyclerView.visibility = View.GONE
                    binding.isDoneTv.visibility = View.VISIBLE
                }
                delay(1000)
                withContext(Dispatchers.Main) { goScreenResult() }
            }
        }
    }

    private fun goScreenResult() {
        showAds {
            findNavController().navigate(
                JunkProgressFragmentDirections.actionJunkProgressFragmentToResultFragment(
                    ResultFragment.CLEANING_KEY
                )
            )
        }
    }

    private fun scanList(): List<String> {
        return listOf(
            "/apex/com.android.tethering/priv-app/TetheringGoogle/TetheringGoogle.apk",
            "/system/app/FilterProvider/FilterProvider.apk",
            "/data/app/~~bdjQFK06A3fQ_MpGON9WzA==/com.antapps.skyphclean-GJo_Pf0vT0iou1c36S_f_g==/base.apk",
            "/system/app/AutomationTest_FB/AutomationTest_FB.apk",
            "/system/priv-app/SmartSwitchAssistant/SmartSwitchAssistant.apk",
            "/system/app/SetupWizardLegalProvider/SetupWizardLegalProvider.apk",
            "/system/priv-app/Finder/Finder.apk",
            "/system/priv-app/NSFusedLocation_v5.3/NSFusedLocation_v5.3.apk",
            "/system/app/ChromeCustomizations/ChromeCustomizations.apk",
            "/system/priv-app/AODService_v60/AODService_v60.apk",
            "/system/priv-app/CocktailBarService_v3.2/CocktailBarService_v3.2.apk",
            "/apex/com.android.extservices/priv-app/GoogleExtServices/GoogleExtServices.apk",
            "/system/priv-app/SecTelephonyProvider/SecTelephonyProvider.apk",
            "/data/app/~~DIKEQ6QinBDjzDQlbHn1KA==/com.sec.android.app.ve.vebgm-XWmxPEE7j026NBKXKGRoBg==/base.apk",
            "/system/app/DRParser/DRParser.apk",
            "/system/priv-app/DynamicSystemInstallationService/DynamicSystemInstallationService.apk",
            "/product/app/NetworkStackOverlay/NetworkStackOverlay.apk",
            "/data/app/~~EaVT6DHvcdtOhd-e_-KWUg==/com.samsung.android.calendar-XO6DPMPgrZRvWwi7HwC7qw==/base.apk",
            "/apex/com.android.cellbroadcast/priv-app/GoogleCellBroadcastServiceModule/GoogleCellBroadcastServiceModule.apk",
            "/system/priv-app/SamsungCalendarProvider/SamsungCalendarProvider.apk",
            "/data/app/~~edFOXh3WpKerJY1-X8f1EQ==/com.osp.app.signin-JYsMLk_l87iQYtheqkroGw==/base.apk",
            "/system/priv-app/AREmoji/AREmoji.apk",
            "/system/app/TetheringAutomation/TetheringAutomation.apk",
            "/system/priv-app/MediaProviderLegacy/MediaProviderLegacy.apk",
            "/system/priv-app/SamsungSocial/SamsungSocial.apk",
            "/system/system_ext/priv-app/WallpaperCropper/WallpaperCropper.apk",
            "/system/priv-app/wallpaper-res/wallpaper-res.apk",
            "/system/app/SmartMirroring/SmartMirroring.apk",
            "/system/app/MAPSAgent/MAPSAgent.apk",
            "/system/priv-app/SendHelpMessage/SendHelpMessage.apk",
            "/system/priv-app/SamsungInCallUI/SamsungInCallUI.apk",
            "/system/app/FactoryCameraFB/FactoryCameraFB.apk",
            "/system/app/USBSettings/USBSettings.apk",
            "/data/app/~~N7czJw18VjEgh1OiKAHZOw==/com.samsung.android.easysetup-Vwy8CcD2EwY6AmdP6IFaQQ==/base.apk",
            "/system/priv-app/ExternalStorageProvider/ExternalStorageProvider.apk",
            "/system/app/AllShareAware/AllShareAware.apk",
            "/system/app/EasyOneHand3/EasyOneHand3.apk",
            "/system/priv-app/DeviceTest/DeviceTest.apk",
            "/system/app/SecHTMLViewer/SecHTMLViewer.apk",
            "/system/app/CompanionDeviceManager/CompanionDeviceManager.apk",
            "/system/priv-app/MmsService/MmsService.apk",
            "/data/app/~~Qgm5KQL77yoefzGyKZxdDA==/com.samsung.android.rubin.app-3tGQL_nA4Hcy0pfqlkbXEA==/base.apk",
            "/system/priv-app/SecDownloadProvider/SecDownloadProvider.apk",
            "/system/app/SmartSwitchAgent/SmartSwitchAgent.apk",
            "/vendor/overlay/TetheringOverlay/TetheringOverlay.apk",
            "/data/app/~~FYxuh_m6lZDt87GbuoitVg==/com.samsung.android.mdx.quickboard-Lj7BEftNTLVQaapt4Q5YoQ==/base.apk",
            "/system/priv-app/OmaCP/OmaCP.apk",
            "/system/priv-app/FaceService/FaceService.apk",
            "/system/priv-app/GpuWatchApp/GpuWatchApp.apk",
            "/system/priv-app/MtpApplication/MtpApplication.apk"
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}