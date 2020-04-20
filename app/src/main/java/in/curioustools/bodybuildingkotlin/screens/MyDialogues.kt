package `in`.curioustools.bodybuildingkotlin.screens

import `in`.curioustools.bodybuildingkotlin.databinding.LayoutDialogRepcountBinding
import `in`.curioustools.bodybuildingkotlin.databinding.LayoutEachrowExerciseBinding
import `in`.curioustools.bodybuildingkotlin.modal.Exercise
import `in`.curioustools.bodybuildingkotlin.screens.start_exer.StartExerciseActivity
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View

@SuppressLint("ResourceAsColor")
class MyDialogues {

    companion object {


        fun showRepCountDialogue(ctx: Context, inflater: LayoutInflater, selectedExerciseList:ArrayList<Exercise>) {
            val binding = LayoutDialogRepcountBinding.inflate(inflater)
            val dialog: AlertDialog? = AlertDialog.Builder(ctx).setView(binding.root).create()
            dialog?.window?.setBackgroundDrawable(ColorDrawable(android.R.color.transparent))

            var repCount = 10
            binding.tvDRepcount.text = "$repCount"
            binding.ibtAdd.setOnClickListener {

                repCount = if (repCount >= 30) 30 else repCount + 5
                binding.tvDRepcount.text = "$repCount"

            }
            binding.ibtMinus.setOnClickListener {

                repCount = if (repCount <= 0) 0 else repCount - 5
                binding.tvDRepcount.text = "$repCount"

            }
            binding.tvDStart.setOnClickListener {
                val intent = Intent(ctx,StartExerciseActivity::class.java)
                intent.putParcelableArrayListExtra(StartExerciseActivity.KEY_EXERCISE_LIST,selectedExerciseList)
                intent.putExtra(StartExerciseActivity.KEY_REP_COUNT,repCount)
                ctx.startActivity(intent)
                dialog?.cancel()
            }

            dialog?.show()


        }

        fun showDetailsDialogue(ctx: Context,inflater: LayoutInflater,exercise: Exercise){
            val binding = LayoutEachrowExerciseBinding.inflate(inflater)
            val dialog:AlertDialog? = AlertDialog.Builder(ctx).setView(binding.root).create()
            dialog?.window?.setBackgroundDrawable(ColorDrawable(android.R.color.transparent))
            dialog?.setCancelable(true)

            binding.tvInfobubTitle.text=exercise.eName
            binding.tvInfobubDetails.text=exercise.details
            binding.tvInfobubReptime.text=Exercise.timeToString(exercise.timeInSeconds)
            binding.ivExerciseRes.setImageResource(exercise.imageRes)
            binding.ivChalInfo.visibility =View.VISIBLE
            binding.ivChalInfo.setOnClickListener {
                dialog?.cancel()
            }

            dialog?.show()


        }


    }


    /*


public class MyDialogs {


    private static AlertDialog itemDetailsDialogue = null;
    public static void showItemDetailsDialog(Context ctx, LayoutInflater inflater, Exercise exc) {
        View v = inflater.inflate(R.layout.ll_eachrow_exercise, null, false);
        ImageView ivInfoBubble = v.findViewById(R.id.iv_info_bubble);
        TextView tvtitle = v.findViewById(R.id.tv_infobub_title);
        TextView tvDetails = v.findViewById(R.id.tv_infobub_details);
        TextView tvRepCount = v.findViewById(R.id.tv_infobub_reptime);
        ImageView ivCancel = v.findViewById(R.id.iv_cancel);

        ivInfoBubble.setImageResource(exc.getImageRes());
        tvtitle.setText(exc.geteName());
        tvDetails.setText(exc.getDetails());
        tvRepCount.setText(Exercise.timeToString(exc.getTimeInSeconds()) + " Per Rep");
        ivCancel.setVisibility(View.VISIBLE);
        ivCancel.setOnClickListener(v1 -> {
            if (itemDetailsDialogue == null) {
                return;
            }
            itemDetailsDialogue.cancel();
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(v);
        builder.setCancelable(true);

        itemDetailsDialogue = builder.create();
        itemDetailsDialogue.show();


    }

}


    * */


}