package dev.sukhrob.bankapp.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.sukhrob.bankapp.presentation.screens.card_list.CardListScreen
import dev.sukhrob.bankapp.presentation.viewmodel.CardsViewModel

private const val NUM_TABS = 4

class ViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    private val viewModel: CardsViewModel
) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = NUM_TABS

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CardListScreen(viewModel, 1)
            1 -> CardListScreen(viewModel, 2)
            2 -> CardListScreen(viewModel, 3)
            else -> CardListScreen(viewModel, 4)
        }
    }
}