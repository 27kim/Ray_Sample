package dh.com.listmaker

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_list_detail.*

class ListDetailActivity : AppCompatActivity() {

    //MainActivity 에서 보내는 list 를 받기 위함
    lateinit var list : TaskList
    lateinit var addTaskButton : FloatingActionButton

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
        list_item_recyclerview.adapter = ListItemRecyclerViewAdapter(list)

        /**
         * FAB 만들기
         */

        addTaskButton = findViewById(R.id.add_task_button)
        addTaskButton.setOnClickListener{
            showCreateTaskDialog()
        }
    }

    fun showCreateTaskDialog(){
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT
        AlertDialog.Builder(this).setTitle(R.string.task_to_add)
                .setView(taskEditText)
                .setPositiveButton(R.string.add_task, {dialog, _ ->
                    val task = taskEditText.text.toString()
                    list.tasks.add(task)

                    list_item_recyclerview.adapter?.notifyItemInserted(list.tasks.size)

                    dialog.dismiss()
                }).create().show()
    }

    override fun onBackPressed() {
        val bundle = Bundle()
        bundle.putParcelable(MainActivity.INTENT_LIST_KEY, list)
        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)


        super.onBackPressed()
    }
}
