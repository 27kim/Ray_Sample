package dh.com.listmaker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SharedMemory
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


/** Activity 변경사항
*  add한 리스트를 adapter에 반영하기 위해 adapter 객체를 만들어서 adapter 에서 list 에 추가 후 notifyDatasetChanged를 변경한다.
*  input 받은 리스트를 Adapter로 전달
*/

/**
 * Click listener 생성한다.
 * ListSelectionRecyclerViewClickListener 를 구현하면서
 * onClicked 를 override 함
 */
class MainActivity : AppCompatActivity(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {
    override fun onClicked(list: TaskList) {
        showListDetail(list)
    }

    companion object {
        var INTENT_LIST_KEY = "list"
        val LIST_DETAIL_REQUEST_CODE = 100
    }
//    lateinit var listRecyclerView: RecyclerView
    lateinit var listDataManager: ListDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            showCreateListDialog()
        }

        /**
         * Preference의 list를 adapter 로 전달하기 위해 listDataManager 생성
         */
        listDataManager = ListDataManager(this)
        val lists = listDataManager.readList()


        //lists_recyclerview 가 바로 xml 을 참조할 수 있다면 아래 처럼 가능
        lists_recyclerview.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        lists_recyclerview.adapter = ListSelectionRecyclerViewAdapter(lists, this)


        //그렇지 않다면 lateinit 한 listRecyclerView를 찾아서 해야함
//
//        listsRecyclerView = findViewById<RecyclerView>(R.id.lists_recyclerview)
//
//        listsRecyclerView.layoutManager = LinearLayoutManager(this)
//        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showCreateListDialog(){
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)

        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT
        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)
        builder.setPositiveButton(positiveButtonTitle) { dialog, which ->

            val list = TaskList(listTitleEditText.text.toString())
            listDataManager.saveList(list)

            val recyclerViewAdapter = lists_recyclerview.adapter as ListSelectionRecyclerViewAdapter
            recyclerViewAdapter.addList(list)
            dialog.dismiss()
            //dialog 사라진 후에 detail activity를 부른다
            showListDetail(list)
        }


        builder.create().show()
    }


    fun showListDetail(list : TaskList){
        var intent = Intent(this, ListDetailActivity::class.java)
        /**
         * Extra 에 TaskList를 바로 넣을 수 없기 때문에
         * parcel 에 담아서 넣어야 한다
         */
//        intent.putExtra(INTENT_LIST_KEY, list)
        intent.putExtra(INTENT_LIST_KEY, list)
//        startActivity(intent)
        startActivityForResult(intent, LIST_DETAIL_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LIST_DETAIL_REQUEST_CODE){
            data?.let{
                var test = data
                var daaa= ArrayList<String>()
                daaa.add("허허허")
                var task = TaskList("와놔", daaa)
                listDataManager.saveList(task)
//                listDataManager.saveList(data.getParcelableExtra<TaskList>(INTENT_LIST_KEY))
                updateLists()
            }
        }
    }

    fun updateLists(){
        val lists = listDataManager.readList()
        lists_recyclerview.adapter = ListSelectionRecyclerViewAdapter(lists, this)
    }

}
