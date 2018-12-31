package dh.com.listmaker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_detail.*

class ListDetailActivity : AppCompatActivity() {

    //MainActivity 에서 보내는 list 를 받기 위함
    lateinit var list : TaskList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)

        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)
        //title (앱의 타이틀)을 set 한다
        title  = list.name

        /**
         *  recyclerview 를 만들기 위해서?
         *  1. Adapter 필요
         *  2. ViewHolder 필
         */
        list_item_recyclerview.layoutManager = LinearLayoutManager(this)
    }
}
