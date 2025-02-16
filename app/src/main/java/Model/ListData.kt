package Model

public class ListData {
    var name: String
    var kondisi: String

    constructor(name: String, kondisi: String) {
        this.name = name
        this.kondisi = kondisi
    }



    fun SetKondisi(kondisi: String){
        this.kondisi = kondisi
    }
}