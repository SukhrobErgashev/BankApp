package dev.sukhrob.bankapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.sukhrob.bankapp.R
import dev.sukhrob.bankapp.databinding.ItemCardBinding
import dev.sukhrob.bankapp.domain.model.Card

class CardListAdapter : RecyclerView.Adapter<CardListAdapter.CardListViewHolder>() {

    private val cardList = ArrayList<Card>()

    fun submitList(list: List<Card>) {
        cardList.clear()
        cardList.addAll(list)
        notifyDataSetChanged()
    }

    inner class CardListViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val temp = cardList[adapterPosition]
            val context = binding.root.context

            with(binding) {
                textCardNumber.text = temp.cardNumber.toString()
                textCardExpiryDate.text = temp.expiryDate.toString()
                textCardName.text = temp.cardName.toString()
                textCardOwner.text = context.getString(R.string.ergashev_sukhrob)
            }
            if (temp.cardNumber!!.startsWith("8600")) {
                binding.cardColor.setImageResource(R.drawable.ic_card1)
                binding.textCardType.text = context.getString(R.string.uzcard)
            } else if (temp.cardNumber!!.startsWith("9860")) {
                binding.cardColor.setImageResource(R.drawable.ic_card2)
                binding.textCardType.text = context.getString(R.string.humo)
            } else if (temp.cardNumber!!.startsWith("4")) {
                binding.textMoneyAmount.text = "0.12 $"
                binding.cardColor.setImageResource(R.drawable.ic_card4)
                binding.textCardType.text = context.getString(R.string.visa)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListViewHolder {
        return CardListViewHolder(
            ItemCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardListViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = cardList.size

}