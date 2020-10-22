package com.example.ch3_maskinfokt

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ch3_maskinfokt.databinding.ItemStoreBinding
import com.example.ch3_maskinfokt.model.Store
import java.util.*

//아이템 뷰 정보를 가지고 있는 클래스
class StoreViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    val binding = ItemStoreBinding.bind(itemView)
}

//internal (protected)
class StoreAdapter : RecyclerView.Adapter<StoreViewHolder>() {
    private var mItems: List<Store> = ArrayList<Store>()
    fun updateItems(items: List<Store>) {
        mItems = items
        notifyDataSetChanged() //ui 갱
    }

    //뷰홀더를 만드는 부분
    //뷰홀더 클래스를 만들어주고 리턴시켜야함
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_store,
            parent,
            false
        ) //adapter가 항상 쓰는 패턴, 사 외워야하는건 아니고.. 개발자 홈페이지에 가이드 보고 하면 되는 것임.
        return StoreViewHolder(view)
    }

    //실제 데이터를 표시하는 부분
    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.binding.store = mItems[position]

    }

    override fun getItemCount() = mItems.size

}

@BindingAdapter("remainStat")
fun setRemainStat(textView: TextView, store: Store){
    when (store.remain_stat) {
        "plenty" -> textView.text = "충분"
        "some" -> textView.text = "여유"
        "few" -> textView.text = "매진 임박"
        else -> textView.text = "매진"

    }
}

@BindingAdapter("count")
fun setCount(textView: TextView, store: Store){
    when (store.remain_stat) {
        "plenty" -> textView.text = "100이상"
        "some" -> textView.text = "30개 이상"
        "few" -> textView.text = "2개 이상"
        else -> textView.text = "매진"

    }
}

@BindingAdapter("color")
fun setColor(textView: TextView, store: Store){
    when (store.remain_stat) {
        "plenty" -> textView.setTextColor(Color.BLACK)
        "some" -> textView.setTextColor(Color.BLUE)
        "few" -> textView.setTextColor(Color.GREEN)
        else -> textView.setTextColor(Color.MAGENTA)

    }
}