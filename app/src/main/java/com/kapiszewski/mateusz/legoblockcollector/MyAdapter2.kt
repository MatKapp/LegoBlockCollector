package com.kapiszewski.mateusz.legoblockcollector

import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by mateu on 5/31/2018.
 */
class MyAdapter2(private val myDataset: Array<String>) :
        RecyclerView.Adapter<MyAdapter2.ViewHolder>(), Parcelable {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)


    constructor(parcel: Parcel) : this(parcel.createStringArray()) {
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter2.ViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false) as TextView
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.text = myDataset[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringArray(myDataset)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyAdapter2> {
        override fun createFromParcel(parcel: Parcel): MyAdapter2 {
            return MyAdapter2(parcel)
        }

        override fun newArray(size: Int): Array<MyAdapter2?> {
            return arrayOfNulls(size)
        }
    }
}