package dev.sukhrob.bankapp.presentation.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.bankapp.databinding.ScreenMainBinding
import dev.sukhrob.bankapp.presentation.adapter.ViewPagerAdapter
import dev.sukhrob.bankapp.presentation.viewmodel.CardsViewModel

val tabs = arrayOf(
    "Hammasi",
    "UzCard",
    "HUMO",
    "Xalqaro Kartalar"
)

@AndroidEntryPoint
class MainScreen : Fragment() {

    private var _binding: ScreenMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ViewPagerAdapter
    private val viewModel: CardsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScreenMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ViewPagerAdapter(
            childFragmentManager,
            lifecycle,
            viewModel
        )
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()

        setListeners()
    }

    private fun setListeners() {
        binding.btnAddCard.setOnClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreenToAddCardScreen())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}