package Model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.NonNull
import com.example.honda.R

class ListAdapter(@NonNull val context1 : Context, private val DataArrayList :
ArrayList<ListData>) : ArrayAdapter<ListData>(context1, 0, DataArrayList)  {



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listData: ListData? = getItem(position)
        val view =  convertView ?: LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false)

        val NamaGambar: TextView = view.findViewById(R.id.ListName)
        val Kondisi: TextView = view.findViewById(R.id.ListOutputs)

        NamaGambar.setText(listData?.name)
        Kondisi.setText(listData?.kondisi)

        return view
    }

}