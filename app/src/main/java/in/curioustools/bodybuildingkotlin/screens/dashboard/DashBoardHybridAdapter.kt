package `in`.curioustools.bodybuildingkotlin.screens.dashboard

import `in`.curioustools.bodybuildingkotlin.R
import `in`.curioustools.bodybuildingkotlin.databinding.LayoutEachrowDailylogBinding
import `in`.curioustools.bodybuildingkotlin.databinding.LayoutEachrowExerciseBinding
import `in`.curioustools.bodybuildingkotlin.databinding.LayoutEachrowHeaderBinding
import `in`.curioustools.bodybuildingkotlin.modal.DailyLog
import `in`.curioustools.bodybuildingkotlin.modal.Exercise
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

//this is an advance adapter with view binding and mutliple(hybrid) views/viewholders
class DashBoardHybridAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataList: ArrayList<Any> = ArrayList()
        set(value) {
            field = value;notifyDataSetChanged()
        }
    //get()=field

    enum class VIEW_TYPES { HEADER, DAILY_LOG, EXERCISE }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val exerciseBinding = LayoutEachrowExerciseBinding.inflate(inflater)
        val dailyLogBinding = LayoutEachrowDailylogBinding.inflate(inflater)
        val headerBinding = LayoutEachrowHeaderBinding.inflate(inflater)
        return when (viewType) {
           VIEW_TYPES.EXERCISE.ordinal ->{ ExerciseHolder(exerciseBinding) }
           VIEW_TYPES.DAILY_LOG.ordinal->{ DailyLogHolder(dailyLogBinding) }
           else                        ->{ StringHeaderHolder(headerBinding) }
       }

    }

    override fun getItemViewType(position: Int): Int {
        return  when(dataList[position]){
            is Exercise -> VIEW_TYPES.EXERCISE.ordinal
            is DailyLog -> VIEW_TYPES.DAILY_LOG.ordinal
            else -> VIEW_TYPES.HEADER.ordinal
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val  data = dataList[position]){
            is String ->(holder as StringHeaderHolder).bindData(data)
            is DailyLog ->(holder as DailyLogHolder).bindData(data)
            is Exercise ->(holder as ExerciseHolder).bindData(data)
        }
    }

    override fun getItemCount() = dataList.size

    class DailyLogHolder(private val binding: LayoutEachrowDailylogBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(log: DailyLog){
            binding.tvDlDate.text=log.date

            when(log.exerciseCount){
                5 -> {
                    binding.icMus5.setImageResource(R.drawable.ic_muscle_flat_yellow)
                    binding.icStar4.setImageResource(R.drawable.ic_star)
                    binding.icStar3.setImageResource(R.drawable.ic_star)
                    binding.icStar2.setImageResource(R.drawable.ic_star)
                    binding.icStar1.setImageResource(R.drawable.ic_star)
                }
                4 -> {
                    binding.icMus5.setImageResource(R.drawable.ic_muscle_flat_gray)
                    binding.icStar4.setImageResource(R.drawable.ic_star)
                    binding.icStar3.setImageResource(R.drawable.ic_star)
                    binding.icStar2.setImageResource(R.drawable.ic_star)
                    binding.icStar1.setImageResource(R.drawable.ic_star)
                }

                3 -> {
                    binding.icMus5.setImageResource(R.drawable.ic_muscle_flat_gray)
                    binding.icStar4.setImageResource(R.drawable.ic_star_grey)
                    binding.icStar3.setImageResource(R.drawable.ic_star)
                    binding.icStar2.setImageResource(R.drawable.ic_star)
                    binding.icStar1.setImageResource(R.drawable.ic_star)
                }

                2 -> {
                    binding.icMus5.setImageResource(R.drawable.ic_muscle_flat_gray)
                    binding.icStar4.setImageResource(R.drawable.ic_star_grey)
                    binding.icStar3.setImageResource(R.drawable.ic_star_grey)
                    binding.icStar2.setImageResource(R.drawable.ic_star)
                    binding.icStar1.setImageResource(R.drawable.ic_star)
                }

                1 -> {
                    binding.icMus5.setImageResource(R.drawable.ic_muscle_flat_gray)
                    binding.icStar4.setImageResource(R.drawable.ic_star_grey)
                    binding.icStar3.setImageResource(R.drawable.ic_star_grey)
                    binding.icStar2.setImageResource(R.drawable.ic_star_grey)
                    binding.icStar1.setImageResource(R.drawable.ic_star)
                }
                0 -> {
                    binding.icMus5.setImageResource(R.drawable.ic_muscle_flat_gray)
                    binding.icStar4.setImageResource(R.drawable.ic_star_grey)
                    binding.icStar3.setImageResource(R.drawable.ic_star_grey)
                    binding.icStar2.setImageResource(R.drawable.ic_star_grey)
                    binding.icStar1.setImageResource(R.drawable.ic_star_grey)
                }


            }

         }

    }
    class ExerciseHolder(private val binding: LayoutEachrowExerciseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(e: Exercise){
            binding.ivChalInfo.visibility=View.GONE
            binding.tvInfobubTitle.text =e.eName
            binding.tvInfobubDetails.text=e.details
            binding.tvInfobubReptime.text=Exercise.timeToString(e.timeInSeconds)
            binding.ivExerciseRes.setImageResource(e.imageRes)
        }
    }
    class StringHeaderHolder(private val binding: LayoutEachrowHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(data:String) {
            binding.tvHeader.text=data
        }
    }
}


/*

    public static class DBHolderHeader extends RecyclerView.ViewHolder {
        TextView tvHeader;

        public DBHolderHeader(@NonNull View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.tv_header);
        }

        public void bindData(String s) {
            tvHeader.setText(s);

        }


    }


}



* */