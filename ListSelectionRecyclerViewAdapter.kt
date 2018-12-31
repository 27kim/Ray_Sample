package dh.com.listmaker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_selection_view_holder.view.*

//Click listener 생성한다.
//ListSelectionRecyclerViewAdapter의 생성자에도 listener를 받도록 변경한다.
class ListSelectionRecyclerViewAdapter(val lists: ArrayList<TaskList>, val clickListener: ListSelectionRecyclerViewClickListener) : RecyclerView.Adapter<ListSelectionViewHolder>() {
    /**
     * //    var listTitles = arrayOf("Shopping List", "Chores", "Android Tutorials")
    기존에는 하드코딩 된 리스트를 사용했지만 Dialog 에서 받은 내용으로 리스트를 구성하기 위해 주석처리함.
    preference를 사용해서 만들려면 어떻게 해야하는가?
     * Adapter 변경사항
     * 최초에 list 를 불러오기 위해서
    1. 어댑터에 list를 전달하기 위해 생성자에서 list 를 받는다.
    2. gietItemcount를 전달받은 list로 변경
    3. onBindViewHolder 에서도 text 를 전달받은 리스트의 데이터로 변경한다.
    4. AddList
     * Activity 변경사항
     *  add한 리스트를 adapter에 반영하기 위해 adapter 객체를 만들어서 adapter 에서 list 에 추가 후 notifyDatasetChanged를 변경한다.
     *  input 받은 리스트를 Adapter로 전달
     */


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ListSelectionViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_selection_view_holder, parent, false)
        return ListSelectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {

        holder?.let {
            var listTitle = holder.itemView.itemString
            var listPosition = holder.itemView.itemNumber

            //Click listener 생성한다.
            //viewholder를 bind 할 때에도 click listener 를 set 해줘야 함
            holder.itemView.setOnClickListener {
                clickListener.onClicked(lists.get(position))
            }

            listTitle.text = lists.get(position).name
            listPosition.text = (position + 1).toString()
        }
    }

    fun addList(list: TaskList) {
        lists.add(list)
        notifyDataSetChanged()
    }

    //Click listener 생성한다.
    //MainActivity 에서 구현
    interface ListSelectionRecyclerViewClickListener {
        fun onClicked(list: TaskList)
    }
}
