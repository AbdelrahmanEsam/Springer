package com.apptikar.springer
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ModelResults")
 data class ModelResults(

    @ColumnInfo (name = "uri")   var uri:String? = null,
     @ColumnInfo (name = "moldorDie")   var moldorDie:String? = null,
    @ColumnInfo (name = " moldorDieName") var  moldorDieName:String? = null,
    @ColumnInfo (name = "image")  var  image:String?  = null,
    @ColumnInfo (name = "history")  var  history:String? = null,
    @ColumnInfo (name = "color")  var  color:String? = null,
    @ColumnInfo (name = "moldorDieCode")  var  moldorDieCode:Int? = null,
    @ColumnInfo (name = "percentprogressfinal")  var percentprogressfinal:Double? = 0.0,
    @ColumnInfo (name = " maxprogressfinal ")  var  maxprogressfinal:Double? = 0.0,
    @ColumnInfo (name = "compressionDistancefinal")  var  compressionDistancefinal:Double? = 0.0,
) {
    @PrimaryKey(autoGenerate = true)  @ColumnInfo (name = "id")    var  id: Int = 0
}


