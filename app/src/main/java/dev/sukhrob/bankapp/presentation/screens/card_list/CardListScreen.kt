package dev.sukhrob.bankapp.presentation.screens.card_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.bankapp.R
import dev.sukhrob.bankapp.core.toHumoCards
import dev.sukhrob.bankapp.core.toUzCards
import dev.sukhrob.bankapp.core.toVisaCards
import dev.sukhrob.bankapp.databinding.ScreenAddCardBinding
import dev.sukhrob.bankapp.databinding.ScreenCardListBinding
import dev.sukhrob.bankapp.domain.model.Card
import dev.sukhrob.bankapp.presentation.adapter.CardListAdapter
import dev.sukhrob.bankapp.presentation.viewmodel.CardsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardListScreen(
    private val viewModel: CardsViewModel,
    private val screenNumber: Int
) : Fragment() {

    private var _binding: ScreenCardListBinding? = null
    private val binding get() = _binding!!

    private val cardListAdapter by lazy { CardListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScreenCardListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUiState()
        setupCardList()
        viewModel.getCards()

    }

    private fun setupCardList() {
        binding.rvCardList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cardListAdapter
        }
    }

    private fun observeUiState() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.cards.collect { cards ->
                showContent(cards)
            }
        }
    }

    private fun showContent(cardList: List<Card>) {
        when (screenNumber) {
            1 -> cardListAdapter.submitList(cardList)
            2 -> cardListAdapter.submitList(cardList.toUzCards())
            3 -> cardListAdapter.submitList(cardList.toHumoCards())
            4 -> cardListAdapter.submitList(cardList.toVisaCards())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}