package com.example.memoapp

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnDeleteListener {

    lateinit var db : MemoDatabase
    var memoList = listOf<MemoEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //디비 불러오기
        db = MemoDatabase.getInstance(this)!!

        val btnAdd = findViewById<Button>(R.id.buttonAdd)
        val editText = findViewById<EditText>(R.id.editTextMemo)
        val recyclerView = findViewById<RecyclerView>(R.id.memoRecyclerView)

        btnAdd.setOnClickListener {

            val memo = MemoEntity(null, editText.text.toString())
            editText.setText("")
            insertMemo(memo)

        }

        //리사이클러뷰
        recyclerView.layoutManager = LinearLayoutManager(this)
        getAllMemos()
    }

    //추가
    private fun insertMemo(memo : MemoEntity) {

        val insertTask = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                db.memoDAO().insert(memo)
            }

            //앞에 작업 후에
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                //다시 불러오기
                getAllMemos()
            }


        }
        insertTask.execute()
    }

    //불러오기
    private fun getAllMemos() {
        val getTask = (@SuppressLint("StaticFieldLeak")
        object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                memoList = db.memoDAO().getAll()
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                setRecyclerview(memoList)
            }
        }).execute()
    }

    //삭제
    private fun deleteMemo(memo: MemoEntity) {
        val deleteTask = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                db.memoDAO().delete(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }
        deleteTask.execute()
    }

    private fun updateMemo(memo : MemoEntity) {

        val updateTask = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                db.memoDAO().update(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }
    }

    private fun setRecyclerview(memoList : List<MemoEntity>) {
        val recyclerView = findViewById<RecyclerView>(R.id.memoRecyclerView)
        recyclerView.adapter = MemoAdapter(this, memoList, this)
    }

    override fun onDeleteListener(memo: MemoEntity) {
        deleteMemo(memo)
    }
}