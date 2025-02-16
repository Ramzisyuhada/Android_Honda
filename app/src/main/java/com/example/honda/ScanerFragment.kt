package com.example.honda

import Model.ListAdapter
import Model.ListData
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.honda.ml.MobilenetV110224Quantized1Metadata1
import org.tensorflow.lite.InterpreterApi
import org.tensorflow.lite.gpu.GpuDelegate
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.label.Category


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScanerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScanerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit  var button : Button;
    lateinit var button_Scane : Button
    private  var bitmapBuffer: Bitmap?= null;
    private var JumlahPhooto : Int = 0;
     var bitmapdata = mutableListOf<Bitmap>()
     val data_List = mutableListOf<String>()
    var datagambar: ArrayAdapter<String>? = null

    /*
    * Variable Pada List View
    * */

    lateinit var Adapter : ListAdapter;
    lateinit var dataArrayList : ArrayList<ListData>
    var i : Int = 0
    lateinit var List : ListView;

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            i++
            val data = result.data
            var listdata = ListData("Gambar $i","")
            dataArrayList.add(listdata)
            Adapter?.notifyDataSetChanged()

            val bitmap = data?.extras?.get("data") as? Bitmap
            if (bitmap != null) {
                bitmapdata.add(bitmap)
            } else {
                Toast.makeText(requireContext(), "Gagal mengambil gambar", Toast.LENGTH_SHORT).show()
            }

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scaner, container, false);
        dataArrayList = ArrayList()

        List = view.findViewById(R.id.list_item)
        button = view.findViewById(R.id.buttonCamera)
        button_Scane = view.findViewById(R.id.Deteksi)
        Adapter = ListAdapter(requireContext(),dataArrayList)

        button.setOnClickListener  {

            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
                openCamera()

            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }

        button_Scane.setOnClickListener {
            processImage()
            Adapter.notifyDataSetChanged()
        }

        return  view;
    }

    private fun processImage() {
        var index = 0
        for ( data :Bitmap in bitmapdata){
            var model: MobilenetV110224Quantized1Metadata1? = null
            try {
                val gpuDelegate = GpuDelegate()

                val options = InterpreterApi.Options().addDelegate(gpuDelegate)
                model = MobilenetV110224Quantized1Metadata1.newInstance(requireContext())

                val newBitmap = data!!.copy(Bitmap.Config.ARGB_8888, true)
                val tfImage = TensorImage.fromBitmap(newBitmap)

                val outputs = model.process(tfImage).probabilityAsCategoryList
                outputs.sortWith(java.util.Comparator { o1: Category, o2: Category ->
                    java.lang.Float.compare(
                        o2.score,
                        o1.score
                    )
                })

                if (!outputs.isEmpty()) {
                    val highestProbabilityOutput = outputs[0]
                    dataArrayList.get(index).kondisi = highestProbabilityOutput.label
                    index++
                    Log.i("Klassifikasi", "Output: $highestProbabilityOutput")
                }
            } catch (e: Exception) {
                Log.e("Klassifikasi", "Error processing image", e)
            } finally {
                model?.close()
            }
        }


    }

    private fun     openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
        List.setAdapter(Adapter)
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScanerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }




}