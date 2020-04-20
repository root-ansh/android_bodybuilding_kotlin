package `in`.curioustools.bodybuildingkotlin.screens.challangelist

import `in`.curioustools.bodybuildingkotlin.R
import `in`.curioustools.bodybuildingkotlin.modal.Exercise
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


//this will be a simple adapter without view binding
class ChallengesAdapter : RecyclerView.Adapter<ChallengesAdapter.ChallengesHolder>() {
    var  exerciseList:MutableList<Exercise> = ArrayList()
    set(value) {field=value;notifyDataSetChanged()}

    var  listener:ChallengeClickListener?=null
    set(value) {field=value;notifyDataSetChanged()}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengesHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_eachrow_challange,parent,false)

        return ChallengesHolder(view)

    }

    override fun onBindViewHolder(holder: ChallengesHolder, pos: Int) {
        holder.bindData(exerciseList[pos],listener)
    }

    override fun getItemCount()=exerciseList.size

    class ChallengesHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private var tvTitle = itemView.findViewById(R.id.tv_chal_title) as TextView
        private var  ivExercise =itemView.findViewById(R.id.iv_chal_image) as ImageView
        private var  ivInfo =itemView.findViewById(R.id.iv_chal_info) as ImageView


        fun bindData(exercise: Exercise, listener: ChallengeClickListener?) {
            tvTitle.text=exercise.eName
            ivExercise.setImageResource(exercise.imageRes)
            ivInfo.visibility=View.VISIBLE
            ivInfo.setOnClickListener {listener?.onClick(exercise)}

        }
    }

    interface ChallengeClickListener{
        fun onClick(exercise: Exercise)
    }

}